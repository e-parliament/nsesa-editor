/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.amendment.client.amendment;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.amendment.client.event.amendment.*;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.amendment.client.ui.document.AmendmentDocumentController;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.NotificationEvent;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.overlay.Transformer;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.util.Counter;
import org.nsesa.editor.gwt.core.client.util.Filter;
import org.nsesa.editor.gwt.core.client.util.FilterResponse;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.exception.ValidationException;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * The default implementation of an {@link AmendmentManager}.
 * <p/>
 * Date: 08/07/12 13:56
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class DefaultAmendmentManager implements AmendmentManager {

    private static final Logger LOG = Logger.getLogger(DefaultAmendmentManager.class.getName());

    /**
     * The transformer for serializing the payload of the body of an amendment container DTO.
     */
    private final Transformer transformer;

    /**
     * An event bus that is private to the current document controller.
     */
    private final DocumentEventBus documentEventBus;

    /**
     * The parent document controller this amendment manager belongs to.
     */
    private AmendmentDocumentController documentController;

    /**
     * The list of amendment controllers for this amendment manager to keep track of.
     */
    private final List<AmendmentController> amendmentControllers = new ArrayList<AmendmentController>();

    /**
     * An injection point finder to find the overlay widgets that are affected by amendments
     */
    private final AmendmentInjectionPointFinder injectionPointFinder;

    /**
     * The provider for the injection points (since amendments can introduce new elements)
     */
    private final AmendmentInjectionPointProvider injectionPointProvider;
    private HandlerRegistration amendmentContainerSaveEventHandlerRegistration;
    private HandlerRegistration amendmentContainerDeleteEventHandlerRegistration;

    @Inject
    public DefaultAmendmentManager(final Transformer transformer,
                                   final DocumentEventBus documentEventBus,
                                   final AmendmentInjectionPointFinder injectionPointFinder,
                                   final AmendmentInjectionPointProvider injectionPointProvider) {
        this.transformer = transformer;
        this.documentEventBus = documentEventBus;
        this.injectionPointFinder = injectionPointFinder;
        this.injectionPointProvider = injectionPointProvider;
    }

    /**
     * Registers the listeners as soon as the parent document controller is injected (manually, due to (lack of)
     * scoping in Gin).
     */
    public void registerListeners() {
        amendmentContainerSaveEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerSaveEvent.TYPE, new AmendmentContainerSaveEventHandler() {
            @Override
            public void onEvent(final AmendmentContainerSaveEvent event) {
                saveAmendmentContainers(event.getAmendments());
            }
        });

        amendmentContainerDeleteEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerDeleteEvent.TYPE, new AmendmentContainerDeleteEventHandler() {
            @Override
            public void onEvent(final AmendmentContainerDeleteEvent event) {
                deleteAmendmentContainers(event.getAmendmentControllers());
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        amendmentContainerSaveEventHandlerRegistration.removeHandler();
        amendmentContainerDeleteEventHandlerRegistration.removeHandler();
    }

    /**
     * Delete a given list of amendment controllers.
     *
     * @param toDelete the amendment controllers to delete.
     */
    public void deleteAmendmentContainers(final AmendmentController... toDelete) {

        final ServiceFactory serviceFactory = documentController.getServiceFactory();
        final ClientFactory clientFactory = documentController.getClientFactory();

        final ArrayList<AmendmentContainerDTO> amendmentContainerDTOs = new ArrayList<AmendmentContainerDTO>(Collections2.transform(Arrays.asList(toDelete), new Function<AmendmentController, AmendmentContainerDTO>() {
            @Override
            public AmendmentContainerDTO apply(AmendmentController input) {
                return input.getModel();
            }
        }));

        serviceFactory.getGwtAmendmentService().deleteAmendmentContainers(clientFactory.getClientContext(), amendmentContainerDTOs, new AsyncCallback<AmendmentContainerDTO[]>() {
            @Override
            public void onFailure(Throwable caught) {
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Woops, could not delete the amendment(s).", caught));
            }

            @Override
            public void onSuccess(AmendmentContainerDTO[] result) {
                // delete from the locally downloaded amendments
                amendmentControllers.removeAll(Arrays.asList(toDelete));
                // successfully deleted on the server, so inform our document controller to remove the amendment
                for (int i = 0; i < result.length; i++) {
                    AmendmentController amendmentController = toDelete[i];
                    // clean up all listeners
                    amendmentController.removeListeners();
                    documentEventBus.fireEvent(new AmendmentContainerDeletedEvent(amendmentController));
                }
                // show notification about successful delete
                documentEventBus.fireEvent(new NotificationEvent(clientFactory.getCoreMessages().amendmentActionDeleteSuccessful(result.length)));
            }
        });
    }

    public void tableAmendmentContainers(final AmendmentController... toTable) {
        final ServiceFactory serviceFactory = documentController.getServiceFactory();
        final ClientFactory clientFactory = documentController.getClientFactory();

        final Collection<AmendmentContainerDTO> amendmentContainerDTOs = Collections2.transform(Arrays.asList(toTable), new Function<AmendmentController, AmendmentContainerDTO>() {
            @Override
            public AmendmentContainerDTO apply(AmendmentController input) {
                return input.getModel();
            }
        });

        final Collection<String> oldStatuses = Collections2.transform(Arrays.asList(toTable), new Function<AmendmentController, String>() {
            @Override
            public String apply(AmendmentController input) {
                return input.getModel().getAmendmentContainerStatus();
            }
        });

        final Counter index = new Counter();
        serviceFactory.getGwtAmendmentService().tableAmendmentContainers(clientFactory.getClientContext(),
                new ArrayList<AmendmentContainerDTO>(amendmentContainerDTOs),
                new AsyncCallback<AmendmentContainerDTO[]>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not table amendment.", caught));
                    }

                    @Override
                    public void onSuccess(AmendmentContainerDTO[] result) {
                        // TODO: incorrect, we should not update the view
                        final AmendmentController amendmentController = toTable[index.get()];
                        amendmentController.setModel(result[index.get()]);
                        final List<String> asList = new ArrayList<String>(oldStatuses);
                        final AmendmentContainerStatusUpdatedEvent updatedEvent = new AmendmentContainerStatusUpdatedEvent(amendmentController, asList.get(index.get()));
                        index.increment();

                        final DocumentEventBus documentEventBus = amendmentController.getDocumentController().getDocumentEventBus();
                        documentEventBus.fireEvent(updatedEvent);
                        documentEventBus.fireEvent(new NotificationEvent(clientFactory.getCoreMessages().amendmentActionTableSuccessful(result.length)));
                    }
                });
    }

    public void withdrawAmendmentContainers(final AmendmentController... toWithdraw) {
        final ServiceFactory serviceFactory = documentController.getServiceFactory();
        final ClientFactory clientFactory = documentController.getClientFactory();

        final Collection<AmendmentContainerDTO> amendmentContainerDTOs = Collections2.transform(Arrays.asList(toWithdraw), new Function<AmendmentController, AmendmentContainerDTO>() {
            @Override
            public AmendmentContainerDTO apply(AmendmentController input) {
                return input.getModel();
            }
        });

        final Collection<String> oldStatuses = Collections2.transform(Arrays.asList(toWithdraw), new Function<AmendmentController, String>() {
            @Override
            public String apply(AmendmentController input) {
                return input.getModel().getAmendmentContainerStatus();
            }
        });

        final Counter index = new Counter();
        serviceFactory.getGwtAmendmentService().withdrawAmendmentContainers(clientFactory.getClientContext(),
                new ArrayList<AmendmentContainerDTO>(amendmentContainerDTOs),
                new AsyncCallback<AmendmentContainerDTO[]>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not withdraw amendment.", caught));
                    }

                    @Override
                    public void onSuccess(AmendmentContainerDTO[] result) {
                        // TODO: incorrect, we should not update the view
                        final AmendmentController amendmentController = toWithdraw[index.get()];
                        amendmentController.setModel(result[index.get()]);
                        final List<String> asList = new ArrayList<String>(oldStatuses);
                        final AmendmentContainerStatusUpdatedEvent updatedEvent = new AmendmentContainerStatusUpdatedEvent(amendmentController, asList.get(index.get()));
                        index.increment();

                        final DocumentEventBus documentEventBus = amendmentController.getDocumentController().getDocumentEventBus();
                        documentEventBus.fireEvent(updatedEvent);
                        documentEventBus.fireEvent(new NotificationEvent(clientFactory.getCoreMessages().amendmentActionWithdrawSuccessful(result.length)));
                    }
                });
    }

    /**
     * Save a list of amendment containers to the backend.
     *
     * @param toSave the amendment container DTOs to save
     */
    protected void saveAmendmentContainers(final AmendmentContainerDTO... toSave) {

        final ServiceFactory serviceFactory = documentController.getServiceFactory();
        final ClientFactory clientFactory = documentController.getClientFactory();

        // serialize amendable widget into XML content
        for (final AmendmentContainerDTO amendment : toSave) {
            amendment.setBody(transformer.transform(amendment.getRoot()));
            amendment.setDocumentID(documentController.getDocument().getDocumentID());
            // do some checks to make sure all fields are set
            if (amendment.getId() == null)
                throw new NullPointerException("No id set before sending to the backend. This will cause problems.");
            if (amendment.getRevisionID() == null)
                throw new NullPointerException("No revision id set before sending to the backend. This will cause problems.");
        }

        serviceFactory.getGwtAmendmentService().saveAmendmentContainers(clientFactory.getClientContext(), new ArrayList<AmendmentContainerDTO>(Arrays.asList(toSave)), new AsyncCallback<AmendmentContainerDTO[]>() {
            @Override
            public void onFailure(final Throwable caught) {
                documentEventBus.fireEvent(new CriticalErrorEvent("Could not save amendment: " + caught.getMessage(), caught));
                if (caught instanceof ValidationException) {
                    LOG.log(Level.SEVERE, "Could not save amendment: " + caught.getMessage(), caught);
                }
            }

            @Override
            public void onSuccess(final AmendmentContainerDTO[] result) {
                mergeAmendmentContainerDTOs(result);
            }
        });
    }

    /**
     * Merge an amendment container DTO from the backend into the current list of amendment controllers.
     *
     * @param toMerge the amendment container DTOs to merge
     */
    protected void mergeAmendmentContainerDTOs(AmendmentContainerDTO... toMerge) {

        if (toMerge != null) {
            final ClientFactory clientFactory = documentController.getClientFactory();

            for (final AmendmentContainerDTO amendmentContainerDTO : toMerge) {
                final AmendmentController amendmentController = documentController.getInjector().getAmendmentController();
                amendmentController.setModel(amendmentContainerDTO);
                amendmentController.setDocumentController(documentController);

                // check if we already have an amendment with a similar id
                int indexOfOlderRevision = -1;
                int counter = 0;
                for (final AmendmentController ac : amendmentControllers) {

                    if (amendmentController.getModel().getId().equals(ac.getModel().getId())) {
                        // aha, we found a controller for an older model
                        indexOfOlderRevision = counter;
                        break;
                    }
                    counter++;
                }
                if (indexOfOlderRevision != -1) {
                    final AmendmentController removed = amendmentControllers.remove(indexOfOlderRevision);
                    amendmentControllers.add(indexOfOlderRevision, amendmentController);
                    if (!removed.getDocumentController().equals(amendmentController.getDocumentController())) {
                        throw new RuntimeException("Newer revision does not match the document controller?");
                    }
                    LOG.info("Replacing amendment controller " + removed + " with " + amendmentController);
                    documentEventBus.fireEvent(new AmendmentContainerUpdatedEvent(removed, amendmentController));
                } else {
                    // new amendment
                    amendmentControllers.add(amendmentController);
                    LOG.info("Adding new amendment controller " + amendmentController);
                    documentEventBus.fireEvent(new AmendmentContainerInjectEvent(toMerge));
                }
                // inform the document the save has happened
                documentEventBus.fireEvent(new AmendmentContainerSavedEvent(amendmentController));
            }
            // show notification about our successful save
            documentEventBus.fireEvent(new NotificationEvent(clientFactory.getCoreMessages().amendmentActionSaveSuccessful(toMerge.length)));
        }

    }

    /**
     * Injects a single amendment into the <tt>root</tt> overlay widget node.
     *
     * @param amendment          the amendment to inject
     * @param root               the root overlay widget node
     * @param documentController the containing document controller
     */
    @Override
    public void injectSingleAmendment(final AmendmentContainerDTO amendment, final OverlayWidget root, final DocumentController documentController) {
        final AmendmentController amendmentController = getAmendmentController(amendment);
        if (amendmentController == null) {
            throw new NullPointerException("AmendmentContainer DTO was not yet registered with a controller?");
        }
        injectInternal(amendmentController, root, documentController);
    }

    /**
     * Injects all existing {#amendmentControllers} into the given <tt>root</tt> overlay widget node.
     *
     * @param root               the root overlay widget node
     * @param documentController the containing document controller
     */
    @Override
    public void inject(final OverlayWidget root, final DocumentController documentController) {
        // TODO if we're going to do multiple injections, it's faster to create a temporary lookup cache with all IDs
        for (final AmendmentController amendmentController : amendmentControllers) {
            injectInternal(amendmentController, root, documentController);
        }
    }


    /**
     * Actual internal injection of the given amendment controller.
     *
     * @param amendmentController the amendment controller to inject
     * @param root                the root node of the overlay widget tree
     * @param documentController  the parent document controller
     */
    protected void injectInternal(final AmendmentController amendmentController, final OverlayWidget root, final DocumentController documentController) {
        // find the correct amendable widget(s) to which this amendment applies
        final List<OverlayWidget> injectionPoints = injectionPointFinder.findInjectionPoints(amendmentController.getModel().getSourceReference().getPath(), root, documentController);
        boolean isInjected = false;
        if (injectionPoints != null) {
            if (injectionPoints.size() > 1) {
                // TODO: multiple injection points might mean that a single amendment controller gets added to multiple amendable widgets - and that will currently cause issues with the view
            }
            for (final OverlayWidget injectionPoint : injectionPoints) {
                final OverlayWidget target = injectionPointProvider.provideInjectionPoint(amendmentController, injectionPoint, documentController);
                if (target != null) {
                    target.addOverlayWidgetAware(amendmentController);
                    amendmentController.setDocumentController(documentController);
                    documentEventBus.fireEvent(new AmendmentContainerInjectedEvent(amendmentController));
                    isInjected = true;
                }
            }
        }
        if (!isInjected) {
            // this can happen if the node is not found, or a different language is used ...
            documentEventBus.fireEvent(new AmendmentContainerSkippedEvent(amendmentController));
        }
    }

    /**
     * Get the controller associated with the given <tt>amendmentContainerDTO</tt>
     *
     * @param amendmentContainerDTO the amendment container DTO to find the controller for
     * @return the controller, or <tt>null</tt> if it cannot be found
     */
    private AmendmentController getAmendmentController(final AmendmentContainerDTO amendmentContainerDTO) {
        for (final AmendmentController amendmentController : amendmentControllers) {
            if (amendmentController.getModel().equals(amendmentContainerDTO)) {
                return amendmentController;
            }
        }
        return null;
    }

    /**
     * Creates a simple {@link AmendmentController} associated with a given <tt>amendmentContainerDTO</tt>
     *
     * @param amendmentContainerDTO the amendment container DTO to create a controller for
     * @return the controller
     */
    private AmendmentController createAmendmentController(final AmendmentContainerDTO amendmentContainerDTO) {
        final AmendmentController amendmentController = documentController.getInjector().getAmendmentController();
        amendmentController.setModel(amendmentContainerDTO);
        amendmentController.setDocumentController(documentController);
        return amendmentController;
    }

    /**
     * Sets a list of amendment container DTOs on this manager. They will be transformed into {@link AmendmentController}s.
     *
     * @param amendmentContainerDTOs the amendment container DTOs.
     */
    @Override
    public void setAmendmentContainerDTOs(final AmendmentContainerDTO[] amendmentContainerDTOs) {
        for (final AmendmentContainerDTO amendmentContainerDTO : amendmentContainerDTOs) {
            amendmentControllers.add(createAmendmentController(amendmentContainerDTO));
        }
    }

    /**
     * Returns all the amendment controllers associated with this manager.
     *
     * @return all amendment controllers
     */
    @Override
    public List<AmendmentController> getAmendmentControllers() {
        return amendmentControllers;
    }

    /**
     * Get a filtered (sub)set of the amendment controllers associated with this manager.
     *
     * @param filter the filter with the specifications
     * @return the filter response, with matching amendment controllers.
     */
    @Override
    public FilterResponse<AmendmentController> getAmendmentControllers(Filter<AmendmentController> filter) {
        final List<AmendmentController> tmpList = new ArrayList<AmendmentController>();
        // apply comparator
        Collections.sort(amendmentControllers, filter.getComparator());
        // apply selection
        for (final AmendmentController amendmentController : amendmentControllers) {
            if (filter.getSelection().select(amendmentController)) {
                tmpList.add(amendmentController);
            }
        }
        final List<AmendmentController> list = new ArrayList<AmendmentController>();

        final int end = Math.min(tmpList.size(), filter.getStart() + filter.getSize());
        for (int i = filter.getStart(); i < end; i++) {
            list.add(tmpList.get(i));
        }
        return new FilterResponse<AmendmentController>(filter, tmpList.size(), list);
    }

    /**
     * Sets the parent document controller and registers the listeners for various events.
     *
     * @param documentController the parent document controller
     */
    @Override
    public void setDocumentController(AmendmentDocumentController documentController) {
        this.documentController = documentController;
    }
}
