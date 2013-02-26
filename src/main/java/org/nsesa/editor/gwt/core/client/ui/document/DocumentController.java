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
import org.nsesa.editor.gwt.core.client.event.widget.AmendableWidgetSelectEvent;
import org.nsesa.editor.gwt.core.client.event.widget.AmendableWidgetSelectEventHandler;
import org.nsesa.editor.gwt.core.client.mode.DocumentMode;
import org.nsesa.editor.gwt.core.client.mode.DocumentState;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineController;
import org.nsesa.editor.gwt.core.client.ui.document.amendments.header.AmendmentsHeaderController;
import org.nsesa.editor.gwt.core.client.ui.overlay.Creator;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.DiffMethod;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.nsesa.editor.gwt.core.client.event.amendments.*;
import org.nsesa.editor.gwt.core.client.event.document.*;
import org.nsesa.editor.gwt.core.client.ui.document.amendments.AmendmentsPanelController;
import org.nsesa.editor.gwt.core.client.ui.document.header.DocumentHeaderController;
import org.nsesa.editor.gwt.core.client.ui.document.info.InfoPanelController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.SourceFileController;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentController {

    private static final Logger LOG = Logger.getLogger(DocumentController.class.getName());

    @Scope(DOCUMENT)
    protected final DocumentView view;
    protected final DocumentViewCss style;
    protected DocumentDTO document;
    protected String documentID;

    protected final ClientFactory clientFactory;
    protected final ServiceFactory serviceFactory;

    protected final OverlayFactory overlayFactory;
    protected final Creator creator;
    protected final Locator locator;

    @Scope(DOCUMENT)
    protected final AmendmentManager amendmentManager;
    @Scope(DOCUMENT)
    protected final DiffingManager diffingManager;
    @Scope(DOCUMENT)
    protected final DocumentHeaderController documentHeaderController;
    @Scope(DOCUMENT)
    protected final DeadlineController deadlineController;
    @Scope(DOCUMENT)
    protected final SourceFileController sourceFileController;

    @Scope(DOCUMENT)
    protected final AmendmentsHeaderController amendmentsHeaderController;

    @Scope(DOCUMENT)
    protected final DocumentEventBus documentEventBus;

    @Scope(DOCUMENT)
    protected final AmendmentsPanelController amendmentsPanelController;
    @Scope(DOCUMENT)
    protected final InfoPanelController infoPanelController;

    private List<AmendmentController> selectedAmendmentControllers = new ArrayList<AmendmentController>();

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
        final DocumentInjector documentInjector = getInjector();
        if (documentInjector == null)
            throw new UnsupportedOperationException("getInjector() returned null. Cannot continue.");

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

    protected void registerModes() {

    }

    protected void registerListeners() {

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

        clientFactory.getEventBus().addHandler(AmendableWidgetSelectEvent.TYPE, new AmendableWidgetSelectEventHandler() {
            @Override
            public void onEvent(AmendableWidgetSelectEvent event) {
                boolean alreadySelected = false;
                if (sourceFileController.getActiveOverlayWidget() == event.getOverlayWidget()) {
                    alreadySelected = true;
                }
                if (sourceFileController.getActiveOverlayWidget() != null) {
                    sourceFileController.getActiveOverlayWidget().asWidget().removeStyleName(style.selected());
                }
                sourceFileController.setActiveOverlayWidget(event.getOverlayWidget());
                sourceFileController.getActiveOverlayWidget().asWidget().addStyleName(style.selected());
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

        documentEventBus.addHandler(AmendmentContainerSavedEvent.TYPE, new AmendmentContainerSavedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerSavedEvent event) {
                diffingManager.diff(DiffMethod.WORD, event.getAmendmentController());
            }
        });

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

        documentEventBus.addHandler(DocumentModeChangeEvent.TYPE, new DocumentModeChangeEventHandler() {
            @Override
            public void onEvent(DocumentModeChangeEvent event) {
                LOG.info("Applying state " + event.getState() + " to mode " + event.getDocumentMode());
                event.getDocumentMode().apply(event.getState());
            }
        });

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

        documentEventBus.addHandler(AmendmentControllerSelectionActionEvent.TYPE, new AmendmentControllerSelectionActionEventHandler() {
            @Override
            public void onEvent(AmendmentControllerSelectionActionEvent event) {
                event.getAction().execute(selectedAmendmentControllers);
            }
        });

        documentEventBus.addHandler(AmendmentControllerSelectionEvent.TYPE, new AmendmentControllerSelectionEventHandler() {
            @Override
            public void onEvent(AmendmentControllerSelectionEvent event) {
                applySelection(event.getSelection());
            }
        });

        documentEventBus.addHandler(AmendmentControllerAddToSelectionEvent.TYPE, new AmendmentControllerAddToSelectionEventHandler() {
            @Override
            public void onEvent(AmendmentControllerAddToSelectionEvent event) {
                addToSelectedAmendmentControllers(event.getSelected().toArray(new AmendmentController[event.getSelected().size()]));
            }
        });

        documentEventBus.addHandler(AmendmentControllerRemoveFromSelectionEvent.TYPE, new AmendmentControllerRemoveFromSelectionEventHandler() {
            @Override
            public void onEvent(AmendmentControllerRemoveFromSelectionEvent event) {
                removeFromSelectedAmendmentControllers(event.getSelected().toArray(new AmendmentController[event.getSelected().size()]));
            }
        });
        documentEventBus.addHandler(SwitchTabEvent.TYPE, new SwitchTabEventHandler() {
            @Override
            public void onEvent(SwitchTabEvent event) {
                view.switchToTab(event.getTabIndex());
            }
        });
    }

    private void showLoadingIndicator(boolean show, String message) {
        view.showLoadingIndicator(show, message);
    }

    private void applySelection(final Selection<AmendmentController> selection) {
        selectedAmendmentControllers.clear();
        List<AmendmentController> toAdd = new ArrayList<AmendmentController>(Collections2.filter(amendmentManager.getAmendmentControllers(), new Predicate<AmendmentController>() {
            @Override
            public boolean apply(AmendmentController input) {
                return selection.select(input);
            }
        }));
        addToSelectedAmendmentControllers(toAdd.toArray(new AmendmentController[toAdd.size()]));
    }

    private void addToSelectedAmendmentControllers(final AmendmentController... amendmentControllers) {
        selectedAmendmentControllers.addAll(Arrays.asList(amendmentControllers));
        documentEventBus.fireEvent(new AmendmentControllerSelectedEvent(selectedAmendmentControllers));
    }

    private void removeFromSelectedAmendmentControllers(final AmendmentController... amendmentControllers) {
        selectedAmendmentControllers.removeAll(Arrays.asList(amendmentControllers));
        documentEventBus.fireEvent(new AmendmentControllerSelectedEvent(selectedAmendmentControllers));
    }

    public void registerMode(final String key, final DocumentMode<? extends DocumentState> mode) {
        if (documentModes.containsKey(key)) {
            LOG.warning("A mode with key '" + key + "' has already been registered. Overriding.");
        } else {
            LOG.info("Installing '" + key + "' mode.");
        }
        documentModes.put(key, mode);
    }

    public DocumentMode<? extends DocumentState> getMode(final String key) {
        return documentModes.get(key);
    }

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

    protected void onDocumentLoaded(DocumentDTO document) {
        setDocument(document);
        final String title = clientFactory.getCoreMessages().windowTitleDocument(document.getName());
        clientFactory.getEventBus().fireEvent(new SetWindowTitleEvent(title));
        loadDocumentContent();
    }

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

    protected void onDocumentContentLoaded(final String content) {
        showLoadingIndicator(true, "Parsing document.");
        sourceFileController.setContent(content);
        showLoadingIndicator(true, "Building document tree.");
        clientFactory.getScheduler().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                fetchAmendments();
                sourceFileController.overlay();
                showLoadingIndicator(true, "Done overlaying document.");
                clientFactory.getEventBus().fireEvent(new ResizeEvent(Window.getClientHeight(), Window.getClientWidth()));
                showLoadingIndicator(false, "Done retrieving document.");
            }
        });
    }

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

    protected void onAmendmentContainerDTOsLoaded(AmendmentContainerDTO[] amendments) {
        LOG.info("Received " + amendments.length + " amendments.");
        amendmentManager.setAmendmentContainerDTOs(amendments);
        injectAmendments();
    }

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


    public DocumentView getView() {
        return view;
    }

    public void setWidth(final String width) {
        view.setWidth(width);
    }

    public void injectAmendments() {
        for (final OverlayWidget root : sourceFileController.getOverlayWidgets()) {
            amendmentManager.inject(root, this);
        }
        // after the injection, renumber all the amendments.
        sourceFileController.renumberAmendments();
    }

    public DocumentInjector getInjector() {
        return GWT.create(DocumentInjector.class);
    }


    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getDocumentID() {
        return documentID;
    }

    public DocumentDTO getDocument() {
        return document;
    }

    public AmendmentManager getAmendmentManager() {
        return amendmentManager;
    }

    public Creator getCreator() {
        return creator;
    }

    public Locator getLocator() {
        return locator;
    }

    public ClientFactory getClientFactory() {
        return clientFactory;
    }

    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public DocumentEventBus getDocumentEventBus() {
        return documentEventBus;
    }

    public OverlayFactory getOverlayFactory() {
        return overlayFactory;
    }

    public List<AmendmentController> getSelectedAmendmentControllers() {
        return selectedAmendmentControllers;
    }

    public DiffingManager getDiffingManager() {
        return diffingManager;
    }

    public SourceFileController getSourceFileController() {
        return sourceFileController;
    }

    @Override
    public String toString() {
        return "Document controller " + documentID + " (" + super.toString() + ")";
    }
}