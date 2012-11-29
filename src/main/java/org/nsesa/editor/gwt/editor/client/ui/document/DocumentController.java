package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.amendment.AmendableWidgetWalker;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.ResizeEvent;
import org.nsesa.editor.gwt.core.client.event.ResizeEventHandler;
import org.nsesa.editor.gwt.core.client.event.SetWindowTitleEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.*;
import org.nsesa.editor.gwt.core.client.event.widget.AmendableWidgetSelectEvent;
import org.nsesa.editor.gwt.core.client.mode.DiffMode;
import org.nsesa.editor.gwt.core.client.mode.DisplayMode;
import org.nsesa.editor.gwt.core.client.mode.DocumentMode;
import org.nsesa.editor.gwt.core.client.mode.DocumentState;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineController;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.Creator;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetUIListener;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.nsesa.editor.gwt.editor.client.event.document.*;
import org.nsesa.editor.gwt.editor.client.ui.actionbar.ActionBarController;
import org.nsesa.editor.gwt.editor.client.ui.amendments.AmendmentsPanelController;
import org.nsesa.editor.gwt.editor.client.ui.amendments.header.AmendmentsHeaderController;
import org.nsesa.editor.gwt.editor.client.ui.document.content.ContentController;
import org.nsesa.editor.gwt.editor.client.ui.document.header.DocumentHeaderController;
import org.nsesa.editor.gwt.editor.client.ui.document.marker.MarkerController;
import org.nsesa.editor.gwt.editor.client.ui.info.InfoPanelController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    private final DocumentInjector injector = GWT.create(DocumentInjector.class);

    @Scope(DOCUMENT)
    private DocumentView view;
    private DocumentDTO document;
    private String documentID;

    private final ClientFactory clientFactory;
    private final ServiceFactory serviceFactory;

    private final OverlayFactory overlayFactory;
    private final Creator creator;
    private final Locator locator;

    @Scope(DOCUMENT)
    private final AmendmentManager amendmentManager;
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

        // document scoped singletons
        this.amendmentManager = injector.getAmendmentManager();
        this.amendmentManager.setInjector(injector);

        this.documentEventBus = injector.getDocumentEventBus();
        this.view = injector.getDocumentView();
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
        registerMode(DiffMode.KEY, new DiffMode(this));
        registerMode(DisplayMode.KEY, new DisplayMode(this));
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

        // forward the amendment injected event to the parent event bus
        documentEventBus.addHandler(AmendmentContainerInjectedEvent.TYPE, new AmendmentContainerInjectedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerInjectedEvent event) {
                assert event.getAmendmentController().getDocumentController() != null : "Expected document controller on injected amendment controller.";
                clientFactory.getEventBus().fireEvent(event);
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
            }
        });

        documentEventBus.addHandler(DocumentRefreshRequestEvent.TYPE, new DocumentRefreshRequestEventHandler() {
            @Override
            public void onEvent(DocumentRefreshRequestEvent event) {
                loadDocumentContent();
            }
        });
    }

    public void registerMode(final String key, final DocumentMode<? extends DocumentState> mode) {
        if (documentModes.containsKey(key)) {
            LOG.warning("A mode with key '" + key + "' has already been registered. Overriding.");
        } else {
            LOG.info("Installing '" + key + "' mode.");
        }
        documentModes.put(key, mode);
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
        serviceFactory.getGwtDocumentService().getDocumentContent(clientFactory.getClientContext(), documentID, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                final String message = clientFactory.getCoreMessages().errorDocumentError(documentID);
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent(message, caught));
            }

            @Override
            public void onSuccess(final String content) {
                setContent(content);
                overlay();
                clientFactory.getEventBus().fireEvent(new ResizeEvent(Window.getClientHeight(), Window.getClientWidth()));
                injectAmendments();
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
    }


    public void setContent(String documentContent) {
        contentController.setContent(documentContent);
    }

    public void scrollTo(Widget widget) {
        contentController.scrollTo(widget);
    }

    public void overlay() {
        final Element[] contentElements = contentController.getContentElements();
        if (amendableWidgets == null) amendableWidgets = new ArrayList<AmendableWidget>();
        for (final Element element : contentElements) {
            try {
                final AmendableWidget rootAmendableWidget = overlay(element, this);
                amendableWidgets.add(rootAmendableWidget);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Exception during the overlaying.", e);
            }
        }
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
            walk(root, visitor);
        }
    }

    @Override
    public void walk(final AmendableWidget toVisit, final AmendableVisitor visitor) {
        if (visitor.visit(toVisit)) {
            if (toVisit != null) {
                for (final AmendableWidget child : toVisit.getChildAmendableWidgets()) {
                    walk(child, visitor);
                }
            }
        }
    }

    public DocumentView getView() {
        return view;
    }

    public void setWidth(final String width) {
        view.setWidth(width);
    }

    @Override
    public void onClick(AmendableWidget sender) {
        clientFactory.getEventBus().fireEvent(new AmendableWidgetSelectEvent(sender));
    }

    @Override
    public void onDblClick(AmendableWidget sender) {
        clientFactory.getEventBus().fireEvent(new AmendmentContainerCreateEvent(sender, AmendmentAction.MODIFICATION, this));
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

    public AmendmentManager getAmendmentManager() {
        return amendmentManager;
    }

    public Creator getCreator() {
        return creator;
    }

    public DocumentEventBus getDocumentEventBus() {
        return documentEventBus;
    }

    @Override
    public String toString() {
        return "Document controller " + documentID + " (" + super.toString() + ")";
    }
}
