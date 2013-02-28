/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.ui.document;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.core.client.diffing.DiffingManager;
import org.nsesa.editor.gwt.core.client.event.*;
import org.nsesa.editor.gwt.core.client.event.amendment.*;
import org.nsesa.editor.gwt.core.client.event.amendments.*;
import org.nsesa.editor.gwt.core.client.event.document.*;
import org.nsesa.editor.gwt.core.client.event.widget.OverlayWidgetSelectEvent;
import org.nsesa.editor.gwt.core.client.event.widget.OverlayWidgetSelectEventHandler;
import org.nsesa.editor.gwt.core.client.mode.DocumentMode;
import org.nsesa.editor.gwt.core.client.mode.DocumentState;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineController;
import org.nsesa.editor.gwt.core.client.ui.document.amendments.AmendmentsPanelController;
import org.nsesa.editor.gwt.core.client.ui.document.amendments.header.AmendmentsHeaderController;
import org.nsesa.editor.gwt.core.client.ui.document.header.DocumentHeaderController;
import org.nsesa.editor.gwt.core.client.ui.document.info.InfoPanelController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.SourceFileController;
import org.nsesa.editor.gwt.core.client.ui.overlay.Creator;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.DiffMethod;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;
import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Main controller, responsible for loading and rendering, and entry point for executing actions on a document.
 * <p/>
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentController {

    private static final Logger LOG = Logger.getLogger(DocumentController.class.getName());

    /**
     * The main view of this document controller.
     */
    @Scope(DOCUMENT)
    protected final DocumentView view;
    /**
     * Document view CSS resource.
     */
    protected final DocumentViewCss style;

    /**
     * Data Transfer Object (DTO) for a single document translation.
     */
    protected DocumentDTO document;

    /**
     * Primary identifier for a single document translation, used to get the {@link #document}.
     */
    protected String documentID;

    /**
     * Client factory, giving access to the client context, global scheduler and event bus.
     */
    @Scope(EDITOR)
    protected final ClientFactory clientFactory;

    /**
     * Service factory, giving access to the backend RPC services.
     */
    @Scope(EDITOR)
    protected final ServiceFactory serviceFactory;

    /**
     * Overlay factory, responsible for doing the overlaying of the DOM with a higher level tree of
     * {@link OverlayWidget}s.
     */
    @Scope(DOCUMENT)
    protected final OverlayFactory overlayFactory;

    /**
     * The creator, responsible for determining which siblings and children are allowed to be created.
     */
    @Scope(DOCUMENT)
    protected final Creator creator;

    /**
     * The locator, responsible for determining the location or path for {@link OverlayWidget}s in the document.
     */
    @Scope(DOCUMENT)
    protected final Locator locator;

    /**
     * The amendment manager, responsible for storing and filtering the {@link AmendmentController}s.
     */
    @Scope(DOCUMENT)
    protected final AmendmentManager amendmentManager;

    /**
     * The diffing manager, responsible for performing diffs on {@link AmendmentController}s.
     */
    @Scope(DOCUMENT)
    protected final DiffingManager diffingManager;

    /**
     * UI controller for the document header.
     */
    @Scope(DOCUMENT)
    protected final DocumentHeaderController documentHeaderController;

    /**
     * UI controller for the deadline.
     */
    @Scope(DOCUMENT)
    protected final DeadlineController deadlineController;

    /**
     * UI controller for the document tab.
     */
    @Scope(DOCUMENT)
    protected final SourceFileController sourceFileController;

    /**
     * UI controller for the amendments panel tab.
     */
    @Scope(DOCUMENT)
    protected final AmendmentsPanelController amendmentsPanelController;

    /**
     * UI controller for the amendments panel header.
     */
    @Scope(DOCUMENT)
    protected final AmendmentsHeaderController amendmentsHeaderController;

    /**
     * UI controller for the info panel tab.
     */
    @Scope(DOCUMENT)
    protected final InfoPanelController infoPanelController;

    /**
     * Document scoped event bus, used for private communication between components in a document controller.
     */
    @Scope(DOCUMENT)
    protected final DocumentEventBus documentEventBus;

    /**
     * A list of selected document controllers on which actions can be performed (tabling, deleting, ...)
     */
    private List<AmendmentController> selectedAmendmentControllers = new ArrayList<AmendmentController>();

    /**
     * A list of supported {@link DocumentMode}s with their registration key.
     */
    private final Map<String, DocumentMode<? extends DocumentState>> documentModes = new LinkedHashMap<String, DocumentMode<? extends DocumentState>>();

    @Inject
    public DocumentController(final ClientFactory clientFactory,
                              final ServiceFactory serviceFactory,
                              final OverlayFactory overlayFactory,
                              final Locator locator,
                              final Creator creator) {

        this.clientFactory = clientFactory;
        this.serviceFactory = serviceFactory;

        this.creator = creator;
        this.locator = locator;
        this.overlayFactory = overlayFactory;

        // get the document injector to create privately scoped components for this controller
        final DocumentInjector documentInjector = getInjector();
        if (documentInjector == null)
            throw new UnsupportedOperationException("DocumentController.getInjector() returned null. Cannot continue.");

        this.amendmentManager = documentInjector.getAmendmentManager();
        this.documentEventBus = documentInjector.getDocumentEventBus();
        this.view = documentInjector.getDocumentView();
        this.style = documentInjector.getDocumentViewCss();
        this.amendmentsPanelController = documentInjector.getAmendmentsPanelController();

        this.diffingManager = documentInjector.getDiffingManager();
        this.infoPanelController = documentInjector.getInfoPanelController();
        this.sourceFileController = documentInjector.getSourceFileController();
        this.documentHeaderController = documentInjector.getDocumentHeaderController();
        this.deadlineController = documentInjector.getDeadlineController();
        this.amendmentsHeaderController = documentInjector.getAmendmentsHeaderController();

        // set references in the child controllers
        this.diffingManager.setDocumentController(this);
        this.amendmentManager.setDocumentController(this);
        this.infoPanelController.setDocumentController(this);
        this.sourceFileController.setDocumentController(this);
        this.amendmentsPanelController.setDocumentController(this);
        this.documentHeaderController.setDocumentController(this);
        this.deadlineController.setDocumentController(this);
        this.amendmentsHeaderController.setDocumentController(this);

        registerListeners();
        registerModes();
    }

    private void registerModes() {

    }

    // TODO while this approach is more flexible, it might be better to have the document controller act as the
    // event handler.
    private void registerListeners() {

        clientFactory.getEventBus().addHandler(DocumentScrollToEvent.TYPE, new DocumentScrollToEventHandler() {
            @Override
            public void onEvent(DocumentScrollToEvent event) {
                if (event.getDocumentController() == DocumentController.this) {
                    sourceFileController.scrollTo(event.getTarget());
                }
            }
        });

        // forward the resize event
        clientFactory.getEventBus().addHandler(ResizeEvent.TYPE, new ResizeEventHandler() {
            @Override
            public void onEvent(ResizeEvent event) {
                documentEventBus.fireEvent(event);
                view.setDocumentHeight(event.getHeight());
            }
        });

        clientFactory.getEventBus().addHandler(OverlayWidgetSelectEvent.TYPE, new OverlayWidgetSelectEventHandler() {
            @Override
            public void onEvent(OverlayWidgetSelectEvent event) {
                boolean alreadySelected = false;
                if (sourceFileController.getActiveOverlayWidget() == event.getOverlayWidget()) {
                    alreadySelected = true;
                }
                if (sourceFileController.getActiveOverlayWidget() != null) {
                    sourceFileController.getActiveOverlayWidget().asWidget().removeStyleName(style.selected());
                }
                sourceFileController.setActiveOverlayWidget(event.getOverlayWidget());
                sourceFileController.getActiveOverlayWidget().asWidget().addStyleName(style.selected());

                // inline editing is currently disabled
                /*final InlineEditingMode inlineEditingMode = (InlineEditingMode) getMode(InlineEditingMode.KEY);
                if (alreadySelected && inlineEditingMode != null && inlineEditingMode.getState().isActive()) {
                    clientFactory.getEventBus().fireEvent(new AttachInlineEditorEvent(event.getAmendableWidget(), DocumentController.this));
                }*/
            }
        });

        // forward the amendment injected event to the parent event bus
        documentEventBus.addHandler(AmendmentContainerInjectedEvent.TYPE, new AmendmentContainerInjectedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerInjectedEvent event) {
                assert event.getAmendmentController().getDocumentController() != null : "Expected document controller on injected amendment controller.";
                clientFactory.getEventBus().fireEvent(event);
            }
        });

        // forward the edit event
        documentEventBus.addHandler(AmendmentContainerEditEvent.TYPE, new AmendmentContainerEditEventHandler() {
            @Override
            public void onEvent(AmendmentContainerEditEvent event) {
                assert event.getAmendmentController().getDocumentController() != null : "Expected document controller on injected amendment controller.";
                clientFactory.getEventBus().fireEvent(event);
            }
        });

        // forward the critical error event
        documentEventBus.addHandler(CriticalErrorEvent.TYPE, new CriticalErrorEventHandler() {
            @Override
            public void onEvent(CriticalErrorEvent event) {
                clientFactory.getEventBus().fireEvent(event);
            }
        });

        // forward the confirmation event
        documentEventBus.addHandler(ConfirmationEvent.TYPE, new ConfirmationEventHandler() {
            @Override
            public void onEvent(ConfirmationEvent event) {
                clientFactory.getEventBus().fireEvent(event);
            }
        });

        // forward the notification event to the parent event bus
        documentEventBus.addHandler(NotificationEvent.TYPE, new NotificationEventHandler() {
            @Override
            public void onEvent(NotificationEvent event) {
                clientFactory.getEventBus().fireEvent(event);
            }
        });

        // after an amendment has been saved, we have to redo its diffing
        documentEventBus.addHandler(AmendmentContainerSavedEvent.TYPE, new AmendmentContainerSavedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerSavedEvent event) {
                diffingManager.diff(DiffMethod.WORD, event.getAmendmentController());
            }
        });

        // if an amendment has been successfully deleted, we need to update our selection and active widget
        // (since it might have been part of it), and renumber the existing amendments locally
        documentEventBus.addHandler(AmendmentContainerDeletedEvent.TYPE, new AmendmentContainerDeletedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerDeletedEvent event) {
                final AmendmentController amendmentController = event.getAmendmentController();
                // remove from the selection, if it existed
                removeFromSelectedAmendmentControllers(amendmentController);
                documentEventBus.fireEvent(new AmendmentControllerSelectedEvent(selectedAmendmentControllers));

                if (amendmentController.getAmendedOverlayWidget() != null) {
                    if (amendmentController.getAmendedOverlayWidget() == sourceFileController.getActiveOverlayWidget()) {
                        sourceFileController.setActiveOverlayWidget(null);
                    }
                    amendmentController.getAmendedOverlayWidget().removeAmendmentController(amendmentController);
                    sourceFileController.renumberAmendments();
                }
            }
        });

        // applying a new state to the document controller
        documentEventBus.addHandler(DocumentModeChangeEvent.TYPE, new DocumentModeChangeEventHandler() {
            @Override
            public void onEvent(DocumentModeChangeEvent event) {
                LOG.info("Applying state " + event.getState() + " to mode " + event.getDocumentMode());
                event.getDocumentMode().apply(event.getState());
            }
        });

        // simple logging of amendment container status transitions
        documentEventBus.addHandler(AmendmentContainerStatusUpdatedEvent.TYPE, new AmendmentContainerStatusUpdatedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerStatusUpdatedEvent event) {
                LOG.info("Amendment " + event.getAmendmentController().getModel() + " had its status updated from "
                        + event.getOldStatus() + " to " + event.getAmendmentController().getModel().getAmendmentContainerStatus());
            }
        });

        // forward the create event to the parent event bus
        documentEventBus.addHandler(AmendmentContainerCreateEvent.TYPE, new AmendmentContainerCreateEventHandler() {
            @Override
            public void onEvent(AmendmentContainerCreateEvent event) {
                clientFactory.getEventBus().fireEvent(event);
            }
        });

        // forward an injection request to the amendment manager, and update the local numbering
        documentEventBus.addHandler(AmendmentContainerInjectEvent.TYPE, new AmendmentContainerInjectEventHandler() {
            @Override
            public void onEvent(AmendmentContainerInjectEvent event) {
                for (final OverlayWidget overlayWidget : sourceFileController.getOverlayWidgets()) {
                    for (final AmendmentContainerDTO amendmentContainerDTO : event.getAmendments()) {
                        amendmentManager.injectSingleAmendment(amendmentContainerDTO, overlayWidget, DocumentController.this);
                    }
                }
                // renumber amendments
                sourceFileController.renumberAmendments();
            }
        });

        // if an amendment is updated, update the revision and renumber the amendments
        documentEventBus.addHandler(AmendmentContainerUpdatedEvent.TYPE, new AmendmentContainerUpdatedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerUpdatedEvent event) {
                final OverlayWidget overlayWidget = event.getOldRevision().getAmendedOverlayWidget();
                if (overlayWidget != null) {
                    overlayWidget.removeAmendmentController(event.getOldRevision());
                    overlayWidget.addAmendmentController(event.getNewRevision());
                    sourceFileController.renumberAmendments();
                }
            }
        });

        // when the document should be reloaded, clear all amendments (they will be requested later again)
        documentEventBus.addHandler(DocumentRefreshRequestEvent.TYPE, new DocumentRefreshRequestEventHandler() {
            @Override
            public void onEvent(DocumentRefreshRequestEvent event) {
                // clear the previous amendable widgets
                sourceFileController.clearAmendableWidgets();
                // make sure all amendment controllers are 'uninjected'
                for (final AmendmentController ac : amendmentManager.getAmendmentControllers()) {
                    if (ac.getDocumentController() == DocumentController.this) {
                        ac.setDocumentController(null);
                    }
                }
                loadDocumentContent();
            }
        });

        // execute an action on the selected amendments
        documentEventBus.addHandler(AmendmentControllerSelectionActionEvent.TYPE, new AmendmentControllerSelectionActionEventHandler() {
            @Override
            public void onEvent(AmendmentControllerSelectionActionEvent event) {
                event.getAction().execute(selectedAmendmentControllers);
            }
        });

        // apply a selection
        documentEventBus.addHandler(AmendmentControllerSelectionEvent.TYPE, new AmendmentControllerSelectionEventHandler() {
            @Override
            public void onEvent(AmendmentControllerSelectionEvent event) {
                applySelection(event.getSelection());
            }
        });

        // add an amendment to the selection
        documentEventBus.addHandler(AmendmentControllerAddToSelectionEvent.TYPE, new AmendmentControllerAddToSelectionEventHandler() {
            @Override
            public void onEvent(AmendmentControllerAddToSelectionEvent event) {
                addToSelectedAmendmentControllers(event.getSelected().toArray(new AmendmentController[event.getSelected().size()]));
            }
        });

        // remove an amendment from the selection
        documentEventBus.addHandler(AmendmentControllerRemoveFromSelectionEvent.TYPE, new AmendmentControllerRemoveFromSelectionEventHandler() {
            @Override
            public void onEvent(AmendmentControllerRemoveFromSelectionEvent event) {
                removeFromSelectedAmendmentControllers(event.getSelected().toArray(new AmendmentController[event.getSelected().size()]));
            }
        });

        // handle requests to switch the document tabs
        documentEventBus.addHandler(SwitchTabEvent.TYPE, new SwitchTabEventHandler() {
            @Override
            public void onEvent(SwitchTabEvent event) {
                view.switchToTab(event.getTabIndex());
            }
        });
    }

    /**
     * Shows or hides a loading indicator when the document content is retrieved and overlaid.
     *
     * @param show    <tt>true</tt> to show the loading indicator, <tt>false</tt> to hide it
     * @param message the message to show, if any
     */
    protected void showLoadingIndicator(boolean show, String message) {
        view.showLoadingIndicator(show, message);
    }

    /**
     * Apply a selection to a set of amendment controllers, and keep only those that pass the
     * {@link Selection#select(Object)} filter.
     *
     * @param selection the selection to filter
     */
    protected void applySelection(final Selection<AmendmentController> selection) {
        selectedAmendmentControllers.clear();
        List<AmendmentController> toAdd = new ArrayList<AmendmentController>(Collections2.filter(amendmentManager.getAmendmentControllers(), new Predicate<AmendmentController>() {
            @Override
            public boolean apply(AmendmentController input) {
                return selection.select(input);
            }
        }));
        addToSelectedAmendmentControllers(toAdd.toArray(new AmendmentController[toAdd.size()]));
    }

    /**
     * Add a set of amendment controllers to the list of selected amendment controllers for this document.
     *
     * @param amendmentControllers the amendment controllers to add to the selection
     */
    protected void addToSelectedAmendmentControllers(final AmendmentController... amendmentControllers) {
        selectedAmendmentControllers.addAll(Arrays.asList(amendmentControllers));
        documentEventBus.fireEvent(new AmendmentControllerSelectedEvent(selectedAmendmentControllers));
    }

    /**
     * Remove a set of amendment controllers from the list of selected amendment controllers for this document.
     *
     * @param amendmentControllers the amendment controllers to remove from the selection
     */
    protected void removeFromSelectedAmendmentControllers(final AmendmentController... amendmentControllers) {
        selectedAmendmentControllers.removeAll(Arrays.asList(amendmentControllers));
        documentEventBus.fireEvent(new AmendmentControllerSelectedEvent(selectedAmendmentControllers));
    }

    /**
     * Register a new mode to use under a given <tt>key</tt>. We can then check for the existing of this mode
     * via {@link #getMode(String)} with the same key, and request/change its state.
     *
     * @param key  the key to register the mode under - it if already exists, it will be overridden with the new mode
     * @param mode the mode to register
     */
    public void registerMode(final String key, final DocumentMode<? extends DocumentState> mode) {
        if (documentModes.containsKey(key)) {
            LOG.warning("A mode with key '" + key + "' has already been registered. Overriding.");
        } else {
            LOG.info("Installing '" + key + "' mode.");
        }
        documentModes.put(key, mode);
    }

    /**
     * Get back the mode that was registered under the given <tt>key</tt>, or <tt>null</tt> if it does not exist.
     *
     * @param key the key under which the mode was registered
     * @return the mode, or <tt>null</tt> if it cannot be found
     */
    public DocumentMode<? extends DocumentState> getMode(final String key) {
        return documentModes.get(key);
    }

    /**
     * Loads a document from the backend using the {@link ServiceFactory} with the {@link #getDocumentID()}. Note that
     * this is only the {@link DocumentDTO}, not the actual content call (which is loaded via
     * {@link #loadDocumentContent()}.
     * <p/>
     * If this call fails, a {@link CriticalErrorEvent} is fired on the global event bus.
     */
    public void loadDocument() {
        serviceFactory.getGwtDocumentService().getDocument(clientFactory.getClientContext(), documentID, new AsyncCallback<DocumentDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                final String message = clientFactory.getCoreMessages().errorDocumentError(documentID);
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent(message, caught));
            }

            @Override
            public void onSuccess(final DocumentDTO document) {
                onDocumentLoaded(document);
            }
        });
    }

    /**
     * Callback when the document is loaded successfully from the backend after {@link #loadDocument()}.
     * This will set the document using {@link #setDocument(org.nsesa.editor.gwt.core.shared.DocumentDTO)},
     * update the title via a {@link SetWindowTitleEvent}, and request the actual document to be loaded via
     * {@link #loadDocumentContent()}.
     *
     * @param document the received document DTO
     */
    protected void onDocumentLoaded(DocumentDTO document) {
        setDocument(document);
        final String title = clientFactory.getCoreMessages().windowTitleDocument(document.getName());
        clientFactory.getEventBus().fireEvent(new SetWindowTitleEvent(title));
        loadDocumentContent();
    }

    /**
     * Requests the loading of the document content for the {@link #getDocument()} DTO by its {@link #getDocumentID()}
     * via the {@link #getServiceFactory()}.
     * <p/>
     * If this call fails, a {@link CriticalErrorEvent} is fired on the global event bus. If the request was successful,
     * we call {@link #onDocumentContentLoaded(String)} with the received content.
     */
    public void loadDocumentContent() {
        assert documentID != null : "No documentID set.";
        // clean up any previous content - if any
        showLoadingIndicator(true, "Retrieving document.");
        serviceFactory.getGwtDocumentService().getDocumentContent(clientFactory.getClientContext(), documentID, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                showLoadingIndicator(false, "Done retrieving document.");
                final String message = clientFactory.getCoreMessages().errorDocumentError(documentID);
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent(message, caught));
            }

            @Override
            public void onSuccess(final String content) {
                showLoadingIndicator(false, "Done retrieving document.");
                onDocumentContentLoaded(content);
            }
        });
    }

    /**
     * Callback when the document content was successfully received. Will set the received content on the
     * {@link #getSourceFileController()}, and via a deferred
     * command call the amendments to be fetched via {@link #fetchAmendments()}.
     *
     * @param content the received HTML content to be place in the source file controller
     */
    protected void onDocumentContentLoaded(final String content) {
        showLoadingIndicator(true, "Parsing document.");
        sourceFileController.setContent(content);
        showLoadingIndicator(true, "Building document tree.");
        clientFactory.getScheduler().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                fetchAmendments();
            }
        });
    }

    /**
     * Fetch the amendments via the {@link ServiceFactory}'s
     * {@link org.nsesa.editor.gwt.core.client.service.gwt.GWTAmendmentService#getAmendmentContainers(org.nsesa.editor.gwt.core.shared.ClientContext)},
     * which upon successful returning, calls {@link #onAmendmentContainerDTOsLoaded(org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO[])}.
     * <p/>
     * This call is supposed to retrieve all available amendments for this user for the current document translation.
     * <p/>
     * If this call fails, a {@link CriticalErrorEvent} is fired on the global event bus.
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
     * {@link AmendmentManager} to be transformed into {@link AmendmentController}s.
     * <p/>
     * Will subsequently execute a deferred command to do the overlaying of the received content to build up the
     * higher level tree of {@link OverlayWidget}s.
     * <p/>
     * When done, we request an injection of the amendments we have received.
     * <p/>
     * Fires a {@link ResizeEvent} on the global event bus to allow components to resize.
     *
     * @param amendments the received amendment container DTOs.
     */
    protected void onAmendmentContainerDTOsLoaded(AmendmentContainerDTO[] amendments) {
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
     * Sets the document Data Transfer Object (DTO) on this controller, and this marks the beginning of our document
     * loading, meaning that we have to:
     * <ul>
     * <li>Clear any existing amendments</li>
     * <li>Update the window title</li>
     * <li>Set the new deadline for the document, if any</li>
     * <li>Get all available translations via {@link ServiceFactory}'s
     * {@link org.nsesa.editor.gwt.core.client.service.gwt.GWTDocumentService#getAvailableTranslations(org.nsesa.editor.gwt.core.shared.ClientContext, String)}</li>
     * <li>Get all available related documents (annexes, introductions, draft legislative resolutions, ...) via
     * {@link ServiceFactory}'s
     * {@link org.nsesa.editor.gwt.core.client.service.gwt.GWTDocumentService#getRelatedDocuments(org.nsesa.editor.gwt.core.shared.ClientContext, String)}</li>
     * </ul>
     *
     * @param document the document DTO to set
     */
    public void setDocument(final DocumentDTO document) {
        // TODO check if this is the same document (or, perhaps, keep a cache of documents and their amendments)
        this.document = document;
        this.documentID = document.getDocumentID();

        // clean the amendments
        amendmentManager.getAmendmentControllers().clear();

        // update the document title
        this.view.setDocumentTitle(document.getName());

        // set the deadline
        this.deadlineController.setDeadline(document.getDeadline());

        // get the translations, if any
        serviceFactory.getGwtDocumentService().getAvailableTranslations(clientFactory.getClientContext(), document.getDocumentID(), new AsyncCallback<ArrayList<DocumentDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                LOG.log(Level.SEVERE, "No translations available.", caught);
            }

            @Override
            public void onSuccess(ArrayList<DocumentDTO> translations) {
                sourceFileController.getSourceFileHeaderController().setAvailableTranslations(translations);
                // select the correct translation
                sourceFileController.getSourceFileHeaderController().setSelectedTranslation(document);
            }
        });

        // get all related documents
        serviceFactory.getGwtDocumentService().getRelatedDocuments(clientFactory.getClientContext(), document.getDocumentID(), new AsyncCallback<ArrayList<DocumentDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                LOG.log(Level.SEVERE, "No related documents available.", caught);
            }

            @Override
            public void onSuccess(ArrayList<DocumentDTO> translations) {
                sourceFileController.getSourceFileHeaderController().setRelatedDocuments(translations);
                // select the correct translation
                sourceFileController.getSourceFileHeaderController().setSelectedRelatedDocument(document);
            }
        });
    }

    /**
     * Return a reference to the view.
     *
     * @return the view
     */
    public DocumentView getView() {
        return view;
    }

    /**
     * Set the width of the view.
     *
     * @param width the with to set
     */
    public void setWidth(final String width) {
        view.setWidth(width);
    }

    /**
     * Inject the amendment controllers that have been placed in the {@link AmendmentManager},
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
                sourceFileController.renumberAmendments();
            }
        });
    }

    /**
     * Get a {@link DocumentInjector}, responsible for getting the various lower components used in this document
     * controller.
     *
     * @return the document injector
     */
    public DocumentInjector getInjector() {
        return GWT.create(DocumentInjector.class);
    }

    /**
     * Sets the document ID on this controller.
     * @param documentID the document ID
     */
    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    /**
     * Get the document ID.
     * @return the document ID
     */
    public String getDocumentID() {
        return documentID;
    }

    /**
     * Get the document DTO.
     * @return the document DTO.
     */
    public DocumentDTO getDocument() {
        return document;
    }

    /**
     * Get a reference to the amendment manager for this document controller.
     * @return the amendment manager
     */
    public AmendmentManager getAmendmentManager() {
        return amendmentManager;
    }

    /**
     * Get a reference to the creator for this document controller.
     * @return the creator
     */
    public Creator getCreator() {
        return creator;
    }

    /**
     * Get a reference to the locator for this document controller.
     * @return the locator
     */
    public Locator getLocator() {
        return locator;
    }

    /**
     * Get a reference to the client factory for this document controller.
     * @return the client factory
     */
    public ClientFactory getClientFactory() {
        return clientFactory;
    }

    /**
     * Get a reference to the service factory for this document controller.
     * @return the service factory
     */
    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    /**
     * Get a reference to the private event bus for this document controller.
     * @return the private event bus
     */
    public DocumentEventBus getDocumentEventBus() {
        return documentEventBus;
    }

    /**
     * Get a reference to the overlay factory for this document controller.
     * @return the overlay factory
     */
    public OverlayFactory getOverlayFactory() {
        return overlayFactory;
    }

    /**
     * Get the locally selected amendment controllers for this document controller.
     * @return the selected amendent controllers
     */
    public List<AmendmentController> getSelectedAmendmentControllers() {
        return selectedAmendmentControllers;
    }

    /**
     * Get a reference to the diffing manager for this document controller.
     * @return the diffing manager
     */
    public DiffingManager getDiffingManager() {
        return diffingManager;
    }

    /**
     * Get a reference to the underlaying source file content controller for this document controller.
     * @return
     */
    public SourceFileController getSourceFileController() {
        return sourceFileController;
    }

    @Override
    public String toString() {
        return "Document controller " + documentID + " (" + super.toString() + ")";
    }
}
