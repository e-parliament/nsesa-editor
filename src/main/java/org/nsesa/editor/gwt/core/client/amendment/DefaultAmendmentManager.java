package org.nsesa.editor.gwt.core.client.amendment;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.NotificationEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.*;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.Transformer;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.Filter;
import org.nsesa.editor.gwt.core.client.util.FilterResponse;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 08/07/12 13:56
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class DefaultAmendmentManager implements AmendmentManager {

    private static final Logger LOG = Logger.getLogger(DefaultAmendmentManager.class.getName());

    private final ServiceFactory serviceFactory;

    private final ClientFactory clientFactory;

    private final Transformer transformer;

    private final DocumentEventBus documentEventBus;

    // parent document controller
    private DocumentController documentController;

    private final List<AmendmentController> amendmentControllers = new ArrayList<AmendmentController>();

    private final AmendmentInjectionPointFinder injectionPointFinder;

    private final AmendmentInjectionPointProvider injectionPointProvider;

    @Inject
    public DefaultAmendmentManager(final ClientFactory clientFactory,
                                   final ServiceFactory serviceFactory,
                                   final Transformer transformer,
                                   final DocumentEventBus documentEventBus,
                                   final AmendmentInjectionPointFinder injectionPointFinder,
                                   final AmendmentInjectionPointProvider injectionPointProvider) {
        this.clientFactory = clientFactory;
        this.serviceFactory = serviceFactory;
        this.transformer = transformer;
        this.documentEventBus = documentEventBus;
        this.injectionPointFinder = injectionPointFinder;
        this.injectionPointProvider = injectionPointProvider;
        registerListeners();
    }

    private void registerListeners() {
        documentEventBus.addHandler(AmendmentContainerSaveEvent.TYPE, new AmendmentContainerSaveEventHandler() {
            @Override
            public void onEvent(final AmendmentContainerSaveEvent event) {

                // serialize amendable widget into XML content
                for (final AmendmentContainerDTO amendment : event.getAmendments()) {
                    amendment.setBody(transformer.transform(amendment.getRoot()));
                    // do some checks to make sure all fields are set
                    if (amendment.getRevisionID() == null)
                        throw new NullPointerException("No revision id set before sending to the backend. This will cause problems.");
                }

                serviceFactory.getGwtAmendmentService().saveAmendmentContainers(clientFactory.getClientContext(), new ArrayList<AmendmentContainerDTO>(Arrays.asList(event.getAmendments())), new AsyncCallback<AmendmentContainerDTO[]>() {
                    @Override
                    public void onFailure(final Throwable caught) {
                        clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Woops, could not save the amendment(s).", caught));
                    }

                    @Override
                    public void onSuccess(final AmendmentContainerDTO[] result) {
                        for (final AmendmentContainerDTO amendmentContainerDTO : result) {
                            final AmendmentController amendmentController = documentController.getInjector().getAmendmentController();
                            amendmentController.setAmendment(amendmentContainerDTO);
                            amendmentController.setDocumentController(documentController);

                            // check if we already have an amendment with a similar revisionID
                            int indexOfOlderRevision = -1;
                            int counter = 0;
                            for (final AmendmentController ac : amendmentControllers) {

                                if (amendmentController.getModel().getRevisionID().equals(ac.getModel().getRevisionID())) {
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
                                documentEventBus.fireEvent(new AmendmentContainerInjectEvent(result));
                            }
                            // inform the document the save has happened
                            documentEventBus.fireEvent(new AmendmentContainerSavedEvent(amendmentController));
                        }
                        // show notification about our successful save
                        documentEventBus.fireEvent(new NotificationEvent(clientFactory.getCoreMessages().amendmentActionSaveSuccessful(result.length)));
                    }
                });
            }
        });

        documentEventBus.addHandler(AmendmentContainerDeleteEvent.TYPE, new AmendmentContainerDeleteEventHandler() {
            @Override
            public void onEvent(final AmendmentContainerDeleteEvent event) {
                final ArrayList<AmendmentContainerDTO> amendmentContainerDTOs = new ArrayList<AmendmentContainerDTO>(Collections2.transform(Arrays.asList(event.getAmendmentControllers()), new Function<AmendmentController, AmendmentContainerDTO>() {
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
                        amendmentControllers.removeAll(Arrays.asList(event.getAmendmentControllers()));
                        // successfully deleted on the server, so inform our document controller to remove the amendment
                        for (int i = 0; i < result.length; i++) {
                            AmendmentController amendmentController = event.getAmendmentControllers()[i];
                            documentEventBus.fireEvent(new AmendmentContainerDeletedEvent(amendmentController));
                        }
                        // show notification about successful delete
                        documentEventBus.fireEvent(new NotificationEvent(clientFactory.getCoreMessages().amendmentActionDeleteSuccessful(result.length)));
                    }
                });
            }
        });
    }

    @Override
    public void injectSingleAmendment(final AmendmentContainerDTO amendment, final AmendableWidget root, final DocumentController documentController) {
        final AmendmentController amendmentController = getAmendmentController(amendment);
        if (amendmentController == null) {
            throw new NullPointerException("AmendmentContainer DTO was not yet registered with a controller?");
        }
        injectInternal(amendmentController, root, documentController);
    }

    @Override
    public void inject(final AmendableWidget root, final DocumentController documentController) {
        // TODO if we're going to do multiple injections, it's faster to create a temporary lookup cache with all IDs
        for (final AmendmentController amendmentController : amendmentControllers) {
            injectInternal(amendmentController, root, documentController);
        }
    }


    protected void injectInternal(final AmendmentController amendmentController, final AmendableWidget root, final DocumentController documentController) {
        // find the correct amendable widget(s) to which this amendment applies
        final List<AmendableWidget> injectionPoints = injectionPointFinder.findInjectionPoints(amendmentController, root, documentController);
        if (injectionPoints != null) {
            if (injectionPoints.size() > 1) {
                // TODO: multiple injection points might mean that a single amendment controller gets added to multiple amendable widgets - and that will currently cause issues with the view
            }
            for (final AmendableWidget injectionPoint : injectionPoints) {
                final AmendableWidget target = injectionPointProvider.provideInjectionPoint(amendmentController, injectionPoint, documentController);
                if (target != null) {
                    target.addAmendmentController(amendmentController);
                    amendmentController.setDocumentController(documentController);
                    documentEventBus.fireEvent(new AmendmentContainerInjectedEvent(amendmentController));
                }
            }
        }

    }

    private AmendmentController getAmendmentController(final AmendmentContainerDTO amendment) {
        for (final AmendmentController amendmentController : amendmentControllers) {
            if (amendmentController.getModel().equals(amendment)) {
                return amendmentController;
            }
        }
        return null;
    }

    private AmendmentController createAmendmentController(final AmendmentContainerDTO amendment) {
        final AmendmentController amendmentController = documentController.getInjector().getAmendmentController();
        amendmentController.setAmendment(amendment);
        return amendmentController;
    }


    @Override
    public void setAmendmentContainerDTOs(final AmendmentContainerDTO[] amendmentContainerDTOs) {
        for (final AmendmentContainerDTO amendmentContainerDTO : amendmentContainerDTOs) {
            amendmentControllers.add(createAmendmentController(amendmentContainerDTO));
        }
    }

    @Override
    public List<AmendmentController> getAmendmentControllers() {
        return amendmentControllers;
    }

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

    @Override
    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }
}
