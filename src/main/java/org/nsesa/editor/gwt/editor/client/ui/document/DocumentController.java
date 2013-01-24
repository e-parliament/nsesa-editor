package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.amendment.AmendableWidgetWalker;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.core.client.diffing.DiffingManager;
import org.nsesa.editor.gwt.core.client.event.*;
import org.nsesa.editor.gwt.core.client.event.amendment.*;
import org.nsesa.editor.gwt.core.client.event.widget.AmendableWidgetSelectEvent;
import org.nsesa.editor.gwt.core.client.event.widget.AmendableWidgetSelectEventHandler;
import org.nsesa.editor.gwt.core.client.mode.*;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineController;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.Creator;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetUIListener;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.util.Counter;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.DiffMethod;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.nsesa.editor.gwt.editor.client.event.amendments.*;
import org.nsesa.editor.gwt.editor.client.event.document.*;
import org.nsesa.editor.gwt.editor.client.ui.actionbar.ActionBarController;
import org.nsesa.editor.gwt.editor.client.ui.amendments.AmendmentsPanelController;
import org.nsesa.editor.gwt.editor.client.ui.amendments.header.AmendmentsHeaderController;
import org.nsesa.editor.gwt.editor.client.ui.document.content.ContentController;
import org.nsesa.editor.gwt.editor.client.ui.document.header.DocumentHeaderController;
import org.nsesa.editor.gwt.editor.client.ui.document.marker.MarkerController;
import org.nsesa.editor.gwt.editor.client.ui.info.InfoPanelController;
import org.nsesa.editor.gwt.inline.client.event.AttachInlineEditorEvent;
import org.nsesa.editor.gwt.inline.client.ui.inline.InlineEditorController;

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
public class DocumentController implements AmendableWidgetUIListener, AmendableWidgetWalker {

    private static final Logger LOG = Logger.getLogger(DocumentController.class.getName());

    private final DocumentInjector injector = getInjector();

    @Scope(DOCUMENT)
    private final DocumentView view;
    private final DocumentViewCss style;
    private DocumentDTO document;
    private String documentID;

    private final ClientFactory clientFactory;
    private final ServiceFactory serviceFactory;

    private final OverlayFactory overlayFactory;
    private final Creator creator;
    private final Locator locator;

    private final InlineEditorController inlineEditorController;

    @Scope(DOCUMENT)
    private final AmendmentManager amendmentManager;
    @Scope(DOCUMENT)
    private final DiffingManager diffingManager;
    @Scope(DOCUMENT)
    private final MarkerController markerController;
    @Scope(DOCUMENT)
    private final DocumentHeaderController documentHeaderController;
    @Scope(DOCUMENT)
    private final ContentController contentController;
    @Scope(DOCUMENT)
    private final DeadlineController deadlineController;
    @Scope(DOCUMENT)
    private final DocumentEventBus documentEventBus;
    @Scope(DOCUMENT)
    private final ActionBarController actionBarController;
    @Scope(DOCUMENT)
    private final AmendmentsPanelController amendmentsPanelController;
    @Scope(DOCUMENT)
    private AmendmentsHeaderController amendmentsHeaderController;
    @Scope(DOCUMENT)
    private final InfoPanelController infoPanelController;

    private List<AmendableWidget> amendableWidgets;

    private AmendableWidget activeAmendableWidget;

    private List<AmendmentController> selectedAmendmentControllers = new ArrayList<AmendmentController>();

    private final Map<String, DocumentMode<? extends DocumentState>> documentModes = new LinkedHashMap<String, DocumentMode<? extends DocumentState>>();

    @Inject
    public DocumentController(final ClientFactory clientFactory,
                              final ServiceFactory serviceFactory,
                              final OverlayFactory overlayFactory,
                              final DiffingManager diffingManager,
                              final Locator locator,
                              final Creator creator,
                              final InlineEditorController inlineEditorController) {

        this.clientFactory = clientFactory;
        this.serviceFactory = serviceFactory;


        this.creator = creator;
        this.locator = locator;
        this.overlayFactory = overlayFactory;
        this.diffingManager = diffingManager;
        this.amendmentManager = injector.getAmendmentManager();
        this.inlineEditorController = inlineEditorController;

        // document scoped singletons
        this.amendmentManager.setDocumentController(this);

        this.documentEventBus = injector.getDocumentEventBus();
        this.view = injector.getDocumentView();
        this.style = injector.getDocumentViewCss();
        //this.amendmentsHeaderController = injector.getAmendmentsHeaderController();
        this.amendmentsPanelController = injector.getAmendmentsPanelController();

        this.infoPanelController = injector.getInfoPanelController();
        this.markerController = injector.getMarkerController();
        this.contentController = injector.getContentController();
        this.documentHeaderController = injector.getDocumentHeaderController();
        this.deadlineController = injector.getDeadlineController();
        this.actionBarController = injector.getActionBarController();

        // set references in the child controllers
        this.infoPanelController.setDocumentController(this);
        this.amendmentsPanelController.setDocumentController(this);
        this.markerController.setDocumentController(this);
        this.contentController.setDocumentController(this);
        this.documentHeaderController.setDocumentController(this);
        this.deadlineController.setDocumentController(this);
        this.actionBarController.setDocumentController(this);

        registerListeners();
        registerModes();
    }

    protected void registerModes() {
        registerMode(InlineEditingMode.KEY, new InlineEditingMode(this, clientFactory));
        registerMode(DiffMode.KEY, new DiffMode(this, clientFactory, serviceFactory));
        registerMode(ConsolidationMode.KEY, new ConsolidationMode(this, clientFactory));
    }

    protected void registerListeners() {
        contentController.getView().getScrollPanel().addScrollHandler(new ScrollHandler() {
            @Override
            public void onScroll(ScrollEvent event) {
                documentEventBus.fireEvent(new DocumentScrollEvent(DocumentController.this));
            }
        });


        clientFactory.getEventBus().addHandler(DocumentScrollToEvent.TYPE, new DocumentScrollToEventHandler() {
            @Override
            public void onEvent(DocumentScrollToEvent event) {
                if (event.getDocumentController() == DocumentController.this) {
                    scrollTo(event.getTarget());
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
                if (activeAmendableWidget == event.getAmendableWidget()) {
                    alreadySelected = true;
                }
                if (activeAmendableWidget != null) {
                    activeAmendableWidget.asWidget().removeStyleName(style.selected());
                }
                activeAmendableWidget = event.getAmendableWidget();
                activeAmendableWidget.asWidget().addStyleName(style.selected());
                final InlineEditingMode inlineEditingMode = (InlineEditingMode) getMode(InlineEditingMode.KEY);
                if (alreadySelected && inlineEditingMode != null && inlineEditingMode.getState().isActive()) {
                    clientFactory.getEventBus().fireEvent(new AttachInlineEditorEvent(event.getAmendableWidget(), DocumentController.this));
                }
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

                if (amendmentController.getAmendedAmendableWidget() != null) {
                    if (amendmentController.getAmendedAmendableWidget() == activeAmendableWidget) {
                        activeAmendableWidget = null;
                    }
                    amendmentController.getAmendedAmendableWidget().removeAmendmentController(amendmentController);
                    renumberAmendments();
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
                for (final AmendableWidget amendableWidget : amendableWidgets) {
                    for (final AmendmentContainerDTO amendmentContainerDTO : event.getAmendments()) {
                        amendmentManager.injectSingleAmendment(amendmentContainerDTO, amendableWidget, DocumentController.this);
                    }
                }
                // renumber amendments
                renumberAmendments();
            }
        });

        documentEventBus.addHandler(AmendmentContainerUpdatedEvent.TYPE, new AmendmentContainerUpdatedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerUpdatedEvent event) {
                final AmendableWidget amendableWidget = event.getOldRevision().getAmendedAmendableWidget();
                if (amendableWidget != null) {
                    amendableWidget.removeAmendmentController(event.getOldRevision());
                    amendableWidget.addAmendmentController(event.getNewRevision());
                    renumberAmendments();
                }
            }
        });

        documentEventBus.addHandler(DocumentRefreshRequestEvent.TYPE, new DocumentRefreshRequestEventHandler() {
            @Override
            public void onEvent(DocumentRefreshRequestEvent event) {

                // clear the previous amendable widgets
                amendableWidgets = new ArrayList<AmendableWidget>();
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

    private boolean addToSelectedAmendmentControllers(final AmendmentController... amendmentControllers) {
        final boolean added = selectedAmendmentControllers.addAll(Arrays.asList(amendmentControllers));
        if (added) documentEventBus.fireEvent(new AmendmentControllerSelectedEvent(selectedAmendmentControllers));
        return added;
    }

    private boolean removeFromSelectedAmendmentControllers(final AmendmentController... amendmentControllers) {
        final boolean removed = selectedAmendmentControllers.removeAll(Arrays.asList(amendmentControllers));
        if (removed) documentEventBus.fireEvent(new AmendmentControllerSelectedEvent(selectedAmendmentControllers));
        return removed;
    }

    public AmendableWidget getTopVisibleAmenableWidget() {
        return contentController.getCurrentVisibleAmendableWidget();
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
            public void onSuccess(DocumentDTO document) {
                setDocument(document);
                final String title = clientFactory.getCoreMessages().windowTitleDocument(document.getName());
                clientFactory.getEventBus().fireEvent(new SetWindowTitleEvent(title));
                loadDocumentContent();
            }
        });
    }

    public void loadDocumentContent() {
        assert documentID != null : "No documentID set.";
        // clean up any previous content - if any
        if (amendableWidgets != null && !amendableWidgets.isEmpty()) {
            for (final AmendableWidget amendableWidget : amendableWidgets) {
                amendableWidget.onDetach();
            }
        }
        serviceFactory.getGwtDocumentService().getDocumentContent(clientFactory.getClientContext(), documentID, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                final String message = clientFactory.getCoreMessages().errorDocumentError(documentID);
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent(message, caught));
            }

            @Override
            public void onSuccess(final String content) {
                setContent(content);
                clientFactory.getScheduler().scheduleDeferred(new Command() {
                    @Override
                    public void execute() {
                        overlay();
                        clientFactory.getEventBus().fireEvent(new ResizeEvent(Window.getClientHeight(), Window.getClientWidth()));
                        injectAmendments();
                    }
                });
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
            public void onSuccess(AmendmentContainerDTO[] result) {
                LOG.info("Received " + result.length + " amendments.");
                amendmentManager.setAmendmentContainerDTOs(result);
                loadDocument();
            }
        });
    }

    public void setDocument(final DocumentDTO document) {
        this.document = document;
        this.documentID = document.getDocumentID();

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
                documentHeaderController.setAvailableTranslations(translations);
                // select the correct translation
                documentHeaderController.setSelectedTranslation(document);
            }
        });

        serviceFactory.getGwtDocumentService().getRelatedDocuments(clientFactory.getClientContext(), document.getDocumentID(), new AsyncCallback<ArrayList<DocumentDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                LOG.log(Level.SEVERE, "No related documents available.", caught);
            }

            @Override
            public void onSuccess(ArrayList<DocumentDTO> translations) {
                documentHeaderController.setRelatedDocuments(translations);
                // select the correct translation
                documentHeaderController.setSelectedRelatedDocument(document);
            }
        });
    }

    public void renumberAmendments() {
        final Counter counter = new Counter();
        walk(new AmendableVisitor() {
            @Override
            public boolean visit(AmendableWidget visited) {
                if (visited.isAmended()) {
                    for (final AmendmentController amendmentController : visited.getAmendmentControllers()) {
                        amendmentController.setOrder(counter.incrementAndGet());
                    }
                }
                return true;
            }
        });
    }


    public void setContent(String documentContent) {
        contentController.setContent(documentContent);
    }

    public void scrollTo(Widget widget) {
        contentController.scrollTo(widget);
    }

    public void overlay() {
        long start = System.currentTimeMillis();
        final Element[] contentElements = contentController.getContentElements();
        if (amendableWidgets == null) amendableWidgets = new ArrayList<AmendableWidget>();
        for (final Element element : contentElements) {
            try {
                final AmendableWidget rootAmendableWidget = overlay(element, this);
                amendableWidgets.add(rootAmendableWidget);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Exception while overlaying.", e);
                documentEventBus.fireEvent(new CriticalErrorEvent("Exception while overlaying.", e));
            }
        }
        LOG.info("Overlaying took " + (System.currentTimeMillis() - start) + "ms.");
    }

    public AmendableWidget overlay(final com.google.gwt.dom.client.Element element, final AmendableWidgetUIListener UIListener) {
        // Assert that the element is attached.
        // assert Document.get().getBody().isOrHasChild(element) : "element is not attached to the document -- BUG";

        final AmendableWidget root = overlayFactory.getAmendableWidget(element);
        if (root != null) {
            walk(root, new AmendableWidgetWalker.AmendableVisitor() {
                @Override
                public boolean visit(AmendableWidget visited) {
                    // if the widget is amendable, register a listener for its events
                    if (visited != null && visited.isAmendable() != null && visited.isAmendable()) {
                        visited.setUIListener(UIListener);
                    }
                    return true;
                }
            });
        }
        return root;
    }

    @Override
    public void walk(AmendableVisitor visitor) {
        for (final AmendableWidget root : amendableWidgets) {
            root.walk(visitor);
        }
    }

    public void walk(final AmendableWidget toVisit, final AmendableVisitor visitor) {
        toVisit.walk(visitor);
    }

    public DocumentView getView() {
        return view;
    }

    public void setWidth(final String width) {
        view.setWidth(width);
    }

    @Override
    public void onClick(AmendableWidget sender) {
//        printDetails(sender);
        clientFactory.getEventBus().fireEvent(new AmendableWidgetSelectEvent(sender, this));
    }

    private void printDetails(final AmendableWidget amendableWidget) {
        final AmendableWidget previousNonIntroducedAmendableWidget = amendableWidget.getPreviousNonIntroducedAmendableWidget(false);
        System.out.println(">>>> " + (previousNonIntroducedAmendableWidget != null ? locator.getLocation(previousNonIntroducedAmendableWidget, "EN", false) : null));
        final AmendableWidget previousNonIntroducedAmendableWidget1 = amendableWidget.getPreviousNonIntroducedAmendableWidget(true);
        System.out.println(">>>> " + (previousNonIntroducedAmendableWidget1 != null ? locator.getLocation(previousNonIntroducedAmendableWidget1, "EN", false) : null));
        final AmendableWidget nextNonIntroducedAmendableWidget = amendableWidget.getNextNonIntroducedAmendableWidget(false);
        System.out.println(">>>> " + (nextNonIntroducedAmendableWidget != null ? locator.getLocation(nextNonIntroducedAmendableWidget, "EN", false) : null));
        final AmendableWidget nextNonIntroducedAmendableWidget1 = amendableWidget.getNextNonIntroducedAmendableWidget(true);
        System.out.println(">>>> " + (nextNonIntroducedAmendableWidget1 != null ? locator.getLocation(nextNonIntroducedAmendableWidget1, "EN", false) : null));
    }

    @Override
    public void onDblClick(AmendableWidget sender) {
        if (documentModes.containsKey(InlineEditingMode.KEY)) {
            final InlineEditingMode inlineEditingMode = (InlineEditingMode) documentModes.get(InlineEditingMode.KEY);
            if (!inlineEditingMode.getState().isActive()) {
                clientFactory.getEventBus().fireEvent(new AmendmentContainerCreateEvent(sender, null, 0, AmendmentAction.MODIFICATION, this));
            }
        } else {
            clientFactory.getEventBus().fireEvent(new AmendmentContainerCreateEvent(sender, null, 0, AmendmentAction.MODIFICATION, this));
        }
    }

    @Override
    public void onMouseOver(AmendableWidget sender) {
        actionBarController.attach(sender);
        actionBarController.setLocation(locator.getLocation(sender, document.getLanguageIso(), false));
    }

    @Override
    public void onMouseOut(AmendableWidget sender) {
        // ignore
    }

    public void injectAmendments() {
        for (final AmendableWidget root : amendableWidgets) {
            amendmentManager.inject(root, this);
        }
        // after the injection, renumber all the amendments.
        renumberAmendments();
    }

    public DocumentInjector getInjector() {
        return GWT.create(DocumentInjector.class);
    }

    public MarkerController getMarkerController() {
        return markerController;
    }

    public DocumentHeaderController getDocumentHeaderController() {
        return documentHeaderController;
    }

    public ContentController getContentController() {
        return contentController;
    }

    public ActionBarController getActionBarController() {
        return actionBarController;
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

    public DocumentEventBus getDocumentEventBus() {
        return documentEventBus;
    }

    public OverlayFactory getOverlayFactory() {
        return overlayFactory;
    }

    public List<AmendmentController> getSelectedAmendmentControllers() {
        return selectedAmendmentControllers;
    }

    @Override
    public String toString() {
        return "Document controller " + documentID + " (" + super.toString() + ")";
    }
}
