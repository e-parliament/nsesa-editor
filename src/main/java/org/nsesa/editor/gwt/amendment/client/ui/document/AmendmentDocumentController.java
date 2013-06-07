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
package org.nsesa.editor.gwt.amendment.client.ui.document;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.internal.util.$Nullable;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.amendment.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.amendment.client.event.amendment.*;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.action.AmendmentActionPanelController;
import org.nsesa.editor.gwt.amendment.client.ui.document.amendments.AmendmentsPanelController;
import org.nsesa.editor.gwt.amendment.client.ui.document.amendments.header.AmendmentsHeaderController;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.diffing.DiffingManager;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.ResizeEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentRefreshRequestEventHandler;
import org.nsesa.editor.gwt.core.client.event.document.DocumentScrollEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentScrollEventHandler;
import org.nsesa.editor.gwt.core.client.event.filter.FilterRequestEvent;
import org.nsesa.editor.gwt.core.client.event.widget.*;
import org.nsesa.editor.gwt.core.client.ui.document.DefaultDocumentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentInjector;
import org.nsesa.editor.gwt.core.client.ui.overlay.Creator;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.Mover;
import org.nsesa.editor.gwt.core.client.ui.overlay.Selector;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentAction;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.OverlayWidgetOrigin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Main controller, responsible for loading and rendering, and entry point for executing actions on a document.
 * <p/>
 * Date: 24/06/12 18:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDocumentController extends DefaultDocumentController {

    private static final Logger LOG = Logger.getLogger(DefaultDocumentController.class.getName());

    /**
     * The amendment manager, responsible for storing and filtering the {@link org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController}s.
     */
    @Scope(DOCUMENT)
    protected AmendmentManager amendmentManager;

    /**
     * UI controller for the amendments panel tab.
     */
    @Scope(DOCUMENT)
    protected AmendmentsPanelController amendmentsPanelController;

    /**
     * UI controller for the amendments panel header.
     */
    @Scope(DOCUMENT)
    protected AmendmentsHeaderController amendmentsHeaderController;

    /**
     * UI controller for the amendments panel header.
     */
    @Scope(DOCUMENT)
    protected AmendmentActionPanelController amendmentActionPanelController;

    @Scope(DOCUMENT)
    protected Selector<AmendmentController> selector;

    protected DiffingManager<AmendmentController> diffingManager;


    // ------------- event handler registration -----------
    private HandlerRegistration documentRefreshRequestEventHandlerRegistration;
    private HandlerRegistration amendmentContainerUpdatedEventHandlerRegistration;
    private HandlerRegistration amendmentContainerInjectEventHandlerRegistration;
    private HandlerRegistration amendmentContainerCreateEventHandlerRegistration;
    private HandlerRegistration amendmentContainerStatusUpdatedEventHandlerRegistration;
    private HandlerRegistration amendmentContainerDeletedEventHandlerRegistration;
    private HandlerRegistration amendmentContainerSavedEventHandlerRegistration;
    private HandlerRegistration amendmentContainerEditEventHandlerRegistration;
    private HandlerRegistration documentScrollEventHandlerRegistration;
    private HandlerRegistration amendmentContainerInjectedEventHandlerRegistration;

    @Inject
    public AmendmentDocumentController(final ClientFactory clientFactory,
                                       final ServiceFactory serviceFactory,
                                       final OverlayFactory overlayFactory,
                                       final Locator locator,
                                       final Creator creator,
                                       final Mover mover) {

        super(clientFactory, serviceFactory, overlayFactory, locator, creator, mover);
    }

    public void setInjector(DocumentInjector documentInjector) {
        super.setInjector(documentInjector);
        if (documentInjector instanceof AmendmentDocumentInjector) {
            final AmendmentDocumentInjector amendmentDocumentInjector = (AmendmentDocumentInjector) documentInjector;
            this.amendmentManager = amendmentDocumentInjector.getAmendmentManager();
            this.amendmentsPanelController = amendmentDocumentInjector.getAmendmentsPanelController();
            this.amendmentsPanelController.setDocumentController(this);
            this.amendmentActionPanelController = amendmentDocumentInjector.getAmendmentActionPanelController();
            this.amendmentsHeaderController = amendmentDocumentInjector.getAmendmentsHeaderController();
            this.amendmentsHeaderController.setDocumentController(this);
            this.selector = amendmentDocumentInjector.getSelector();
            this.diffingManager = amendmentDocumentInjector.getAmendmentDiffingManager();
            this.diffingManager.setDocumentController(this);
            // install collection filter for the selector
            this.selector.setCollectionFilter(new Selector.CollectionFilter<AmendmentController>() {
                @Override
                public List<AmendmentController> getCollection() {
                    return amendmentManager.getAmendmentControllers();
                }
            });
        }

        // set references in the child controllers
        this.amendmentManager.setDocumentController(this);
        this.amendmentManager.registerListeners();
    }

    // TODO while this approach is more flexible, it might be better to have the document controller act as the
    // event handler.
    public void registerListeners() {
        super.registerListeners();

        // forward the amendment injected event to the parent event bus
        amendmentContainerInjectedEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerInjectedEvent.TYPE, new AmendmentContainerInjectedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerInjectedEvent event) {
                assert event.getAmendmentController().getDocumentController() != null : "Expected document controller on injected amendment controller.";
                if (isDiffModeActive()) {
                    diffingManager.diff(event.getAmendmentController());
                } else {
                    LOG.info("Diff not active, skipping diff on amendment " + event.getAmendmentController().getModel().getId());
                }
                clientFactory.getEventBus().fireEvent(event);
            }
        });

        // when we detect a scrolling event, hide the amendment action panel
        documentScrollEventHandlerRegistration = documentEventBus.addHandler(DocumentScrollEvent.TYPE, new DocumentScrollEventHandler() {
            @Override
            public void onEvent(DocumentScrollEvent event) {
                amendmentActionPanelController.hide();
            }
        });

        documentEventBus.addHandler(OverlayWidgetModifyEvent.TYPE, new OverlayWidgetModifyEventHandler() {
            @Override
            public void onEvent(OverlayWidgetModifyEvent event) {
                // translate to an amendment
                documentEventBus.fireEvent(new AmendmentContainerCreateEvent(event.getOverlayWidget().getParentOverlayWidget(), event.getOverlayWidget(), event.getOverlayWidget(), AmendmentAction.MODIFICATION, AmendmentDocumentController.this));
            }
        });

        documentEventBus.addHandler(OverlayWidgetDeleteEvent.TYPE, new OverlayWidgetDeleteEventHandler() {
            @Override
            public void onEvent(OverlayWidgetDeleteEvent event) {
                // translate to an amendment
                documentEventBus.fireEvent(new AmendmentContainerCreateEvent(event.getOverlayWidget().getParentOverlayWidget(), event.getOverlayWidget(), event.getOverlayWidget(), AmendmentAction.DELETION, AmendmentDocumentController.this));
            }
        });

        documentEventBus.addHandler(OverlayWidgetNewEvent.TYPE, new OverlayWidgetNewEventHandler() {
            @Override
            public void onEvent(OverlayWidgetNewEvent event) {
                // set the origin to come from the amendment
                event.getChild().setOrigin(OverlayWidgetOrigin.AMENDMENT);
                documentEventBus.fireEvent(new AmendmentContainerCreateEvent(event.getParentOverlayWidget(), event.getReference(), event.getChild(),
                        AmendmentAction.CREATION, sourceFileController.getDocumentController()));
            }
        });

        // forward the edit event
        amendmentContainerEditEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerEditEvent.TYPE, new AmendmentContainerEditEventHandler() {
            @Override
            public void onEvent(AmendmentContainerEditEvent event) {
                assert event.getAmendmentController().getDocumentController() != null : "Expected document controller on injected amendment controller.";
                clientFactory.getEventBus().fireEvent(event);
            }
        });

        // after an amendment has been saved, we have to redo its diffing
        amendmentContainerSavedEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerSavedEvent.TYPE, new AmendmentContainerSavedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerSavedEvent event) {
                if (isDiffModeActive()) {
                    diffingManager.diff(event.getAmendmentController());
                } else {
                    LOG.info("Diff not active, skipping diff on amendment " + event.getAmendmentController().getModel().getId());
                }
            }
        });

        // if an amendment has been successfully deleted, we need to update our selection and active widget
        // (since it might have been part of it), and renumber the existing amendments locally
        amendmentContainerDeletedEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerDeletedEvent.TYPE, new AmendmentContainerDeletedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerDeletedEvent event) {
                final AmendmentController amendmentController = event.getAmendmentController();
                // remove from the selection, if it existed
                selector.removeFromSelectedAmendmentControllers(Arrays.asList(amendmentController));

                OverlayWidget toRemove = amendmentController.getOverlayWidget();
                if (toRemove != null) {
                    final int widgetIndex = toRemove.getParentOverlayWidget().getChildOverlayWidgets().indexOf(toRemove);
                    final OverlayWidget parent = toRemove.getParentOverlayWidget();

                    if (toRemove == sourceFileController.getActiveOverlayWidget()) {
                        sourceFileController.setActiveOverlayWidget(null);
                    }
                    toRemove.removeAmendmentController(amendmentController);
                    if (toRemove.isIntroducedByAnAmendment() &&
                            toRemove.getOverlayWidgetAwareList().isEmpty()) {
                        toRemove.getParentOverlayWidget().removeOverlayWidget(toRemove);
                    }
                    // raise a structural change event now
                    final Collection<OverlayWidget> affectedWidgets =
                            Collections2.filter(parent.getChildOverlayWidgets(),
                                    new Predicate<OverlayWidget>() {
                                        @Override
                                        public boolean apply(@$Nullable OverlayWidget input) {
                                            return input.isIntroducedByAnAmendment() &&
                                                    parent.getChildOverlayWidgets().indexOf(input) >= widgetIndex;
                                        }
                                    });
                    if (!affectedWidgets.isEmpty()) {
                        documentEventBus.fireEvent(new OverlayWidgetStructureChangeEvent(new ArrayList<OverlayWidget>(affectedWidgets)));
                    }

                }
                sourceFileController.renumberOverlayWidgetsAware();
            }
        });

        // simple logging of amendment container status transitions
        amendmentContainerStatusUpdatedEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerStatusUpdatedEvent.TYPE, new AmendmentContainerStatusUpdatedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerStatusUpdatedEvent event) {
                LOG.info("Amendment " + event.getAmendmentController().getModel() + " had its status updated from "
                        + event.getOldStatus() + " to " + event.getAmendmentController().getModel().getAmendmentContainerStatus());
            }
        });

        // forward the create event to the parent event bus
        amendmentContainerCreateEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerCreateEvent.TYPE, new AmendmentContainerCreateEventHandler() {
            @Override
            public void onEvent(AmendmentContainerCreateEvent event) {
                clientFactory.getEventBus().fireEvent(event);
            }
        });

        // forward an injection request to the amendment manager, and update the local numbering
        amendmentContainerInjectEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerInjectEvent.TYPE, new AmendmentContainerInjectEventHandler() {
            @Override
            public void onEvent(AmendmentContainerInjectEvent event) {
                for (final OverlayWidget overlayWidget : sourceFileController.getOverlayWidgets()) {
                    for (final AmendmentContainerDTO amendmentContainerDTO : event.getAmendments()) {
                        amendmentManager.injectSingleAmendment(amendmentContainerDTO, overlayWidget, AmendmentDocumentController.this);
                    }
                }
                // renumber amendments
                sourceFileController.renumberOverlayWidgetsAware();
            }
        });

        // if an amendment is updated, update the revision and renumber the amendments
        amendmentContainerUpdatedEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerUpdatedEvent.TYPE, new AmendmentContainerUpdatedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerUpdatedEvent event) {
                final OverlayWidget overlayWidget = event.getOldRevision().getOverlayWidget();
                if (overlayWidget != null) {
                    overlayWidget.removeAmendmentController(event.getOldRevision());
                    overlayWidget.addOverlayWidgetAware(event.getNewRevision());
                    sourceFileController.renumberOverlayWidgetsAware();
                }
            }
        });

        // when the document should be reloaded, clear all amendments (they will be requested later again)
        documentRefreshRequestEventHandlerRegistration = documentEventBus.addHandler(DocumentRefreshRequestEvent.TYPE, new DocumentRefreshRequestEventHandler() {
            @Override
            public void onEvent(DocumentRefreshRequestEvent event) {
                // make sure all amendment controllers are 'uninjected'
                for (final AmendmentController ac : amendmentManager.getAmendmentControllers()) {
                    if (ac.getDocumentController() == AmendmentDocumentController.this) {
                        ac.setDocumentController(null);
                    }
                }
            }
        });

    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        super.removeListeners();
        documentRefreshRequestEventHandlerRegistration.removeHandler();
        amendmentContainerUpdatedEventHandlerRegistration.removeHandler();
        amendmentContainerInjectEventHandlerRegistration.removeHandler();
        amendmentContainerCreateEventHandlerRegistration.removeHandler();
        amendmentContainerStatusUpdatedEventHandlerRegistration.removeHandler();
        amendmentContainerDeletedEventHandlerRegistration.removeHandler();
        amendmentContainerSavedEventHandlerRegistration.removeHandler();
        amendmentContainerEditEventHandlerRegistration.removeHandler();
        documentScrollEventHandlerRegistration.removeHandler();
        amendmentContainerInjectedEventHandlerRegistration.removeHandler();
    }

    /**
     * Callback when the document content was successfully received. Will set the received content on the
     * {@link #getSourceFileController()}, and via a deferred
     * command call the amendments to be fetched via {@link #fetchAmendments()}.
     *
     * @param content the received HTML content to be place in the source file controller
     */
    public void onDocumentContentLoaded(final String content) {
        super.onDocumentContentLoaded(content);
        clientFactory.getScheduler().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                fetchAmendments();
            }
        });
    }

    /**
     * Fetch the amendments via the {@link org.nsesa.editor.gwt.core.client.ServiceFactory}'s
     * {@link org.nsesa.editor.gwt.core.client.service.gwt.GWTAmendmentService#getAmendmentContainers(org.nsesa.editor.gwt.core.shared.ClientContext)},
     * which upon successful returning, calls {@link #onAmendmentContainerDTOsLoaded(org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO[])}.
     * <p/>
     * This call is supposed to retrieve all available amendments for this user for the current document translation.
     * <p/>
     * If this call fails, a {@link org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent} is fired on the global event bus.
     */
    public void fetchAmendments() {
        serviceFactory.getGwtAmendmentService().getAmendmentContainers(clientFactory.getClientContext(), new AsyncCallback<AmendmentContainerDTO[]>() {
            @Override
            public void onFailure(Throwable caught) {
                final String message = clientFactory.getCoreMessages().errorAmendmentsError();
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent(message, caught));
            }

            @Override
            public void onSuccess(AmendmentContainerDTO[] amendments) {
                onAmendmentContainerDTOsLoaded(amendments);
            }
        });
    }

    /**
     * Callback when the amendments have been received. Will subsequently place the received amendment DTOs on the
     * {@link org.nsesa.editor.gwt.amendment.client.amendment.AmendmentManager} to be transformed into {@link org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController}s.
     * <p/>
     * Will subsequently execute a deferred command to do the overlaying of the received content to build up the
     * higher level tree of {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget}s.
     * <p/>
     * When done, we request an injection of the amendments we have received.
     * <p/>
     * Fires a {@link org.nsesa.editor.gwt.core.client.event.ResizeEvent} on the global event bus to allow components to resize.
     *
     * @param amendments the received amendment container DTOs.
     */
    public void onAmendmentContainerDTOsLoaded(AmendmentContainerDTO[] amendments) {
        LOG.info("Received " + amendments.length + " amendments.");
        amendmentManager.setAmendmentContainerDTOs(amendments);

        clientFactory.getScheduler().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                sourceFileController.overlay();
                showLoadingIndicator(true, "Done overlaying document.");
                clientFactory.getEventBus().fireEvent(new ResizeEvent(Window.getClientHeight(), Window.getClientWidth()));
                showLoadingIndicator(false, "Done retrieving document.");

                clientFactory.getScheduler().scheduleDeferred(new Command() {
                    @Override
                    public void execute() {
                        injectAmendments();
                    }
                });
            }
        });
    }

    /**
     * Inject the amendment controllers that have been placed in the {@link org.nsesa.editor.gwt.amendment.client.amendment.AmendmentManager},
     * and, after injection, do a renumbering to set their local number
     */
    public void injectAmendments() {
        for (final OverlayWidget root : sourceFileController.getOverlayWidgets()) {
            amendmentManager.inject(root, this);
        }
        clientFactory.getScheduler().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                // after the injection, renumber all the amendments.
                sourceFileController.renumberOverlayWidgetsAware();
                //raise a filter request event after reordering
                documentEventBus.fireEvent(new FilterRequestEvent(null));
            }
        });

    }

    /**
     * Get a reference to the amendment manager for this document controller.
     *
     * @return the amendment manager
     */
    public AmendmentManager getAmendmentManager() {
        return amendmentManager;
    }

    @Override
    public DiffingManager getDiffingManager() {
        // override to ensure we're using the amendment diffing manager instead of a default one.
        return diffingManager;
    }

    /**
     * Return a reference to the document-wide singleton for the amendment action panel.
     *
     * @return the amendment action panel
     */
    public AmendmentActionPanelController getAmendmentActionPanelController() {
        return amendmentActionPanelController;
    }

    public Selector<AmendmentController> getSelector() {
        return selector;
    }

    @Override
    public AmendmentDocumentInjector getInjector() {
        final DocumentInjector injector = super.getInjector();
        if (injector instanceof AmendmentDocumentInjector) {
            return (AmendmentDocumentInjector) injector;
        } else {
            throw new RuntimeException("Ack, the injector is not an AmendmentDocumentInjector? -- BUG");
        }
    }

    @Override
    public String toString() {
        return "Amendment Document controller " + documentID + " (" + super.toString() + ")";
    }
}