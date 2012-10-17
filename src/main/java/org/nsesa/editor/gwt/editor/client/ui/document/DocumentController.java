package org.nsesa.editor.gwt.editor.client.ui.document;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.core.client.event.ResizeEvent;
import org.nsesa.editor.gwt.core.client.event.ResizeEventHandler;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEvent;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineController;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetListener;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayStrategy;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.nsesa.editor.gwt.editor.client.event.document.*;
import org.nsesa.editor.gwt.editor.client.ui.actionbar.ActionBarController;
import org.nsesa.editor.gwt.editor.client.ui.document.content.ContentController;
import org.nsesa.editor.gwt.editor.client.ui.document.header.DocumentHeaderController;
import org.nsesa.editor.gwt.editor.client.ui.document.marker.MarkerController;

import java.util.ArrayList;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentController implements AmendableWidgetListener {

    private final DocumentInjector injector = GWT.create(DocumentInjector.class);

    private DocumentView view;
    private DocumentDTO document;
    private String documentID;

    private final ClientFactory clientFactory;
    private final ServiceFactory serviceFactory;
    private final MarkerController markerController;
    private final DocumentHeaderController documentHeaderController;
    private final ContentController contentController;
    private final ActionBarController actionBarController;
    private final DeadlineController deadlineController;
    private final OverlayFactory overlayFactory;
    private final Locator locator;
    private final EventBus documentEventBus;

    private final AmendmentManager amendmentManager;

    private final OverlayStrategy overlayStrategy;

    private ArrayList<AmendableWidget> amendableWidgets;

    @Inject
    public DocumentController(final ClientFactory clientFactory,
                              final ServiceFactory serviceFactory,
                              final OverlayFactory overlayFactory,
                              final OverlayStrategy overlayStrategy,
                              final ActionBarController actionBarController,
                              final Locator locator,
                              final AmendmentManager amendmentManager) {

        this.clientFactory = clientFactory;
        this.serviceFactory = serviceFactory;
        this.actionBarController = actionBarController;

        this.amendmentManager = amendmentManager;
        this.overlayStrategy = overlayStrategy;
        this.locator = locator;
        this.overlayFactory = overlayFactory;

        this.documentEventBus = injector.getEventBus();

        this.view = injector.getDocumentView();
        this.markerController = injector.getMarkerController();
        this.contentController = injector.getContentController();
        this.documentHeaderController = injector.getDocumentHeaderController();
        this.deadlineController = injector.getDeadlineController();

        // set references in the child controllers
        this.markerController.setDocumentController(this);
        this.contentController.setDocumentController(this);
        this.documentHeaderController.setDocumentController(this);
        this.actionBarController.setDocumentController(this);
        this.deadlineController.setDocumentController(this);

        registerListeners();
    }

    private void registerListeners() {
        contentController.getView().getScrollPanel().addScrollHandler(new ScrollHandler() {
            @Override
            public void onScroll(ScrollEvent event) {
                clientFactory.getEventBus().fireEvent(new DocumentScrollEvent(DocumentController.this));
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

        clientFactory.getEventBus().addHandler(DocumentRefreshRequestEvent.TYPE, new DocumentRefreshRequestEventHandler() {
            @Override
            public void onEvent(DocumentRefreshRequestEvent event) {
                event.getDocumentController().documentEventBus.fireEvent(event);
            }
        });

        // forward the resize event
        clientFactory.getEventBus().addHandler(ResizeEvent.TYPE, new ResizeEventHandler() {
            @Override
            public void onEvent(ResizeEvent event) {
                documentEventBus.fireEvent(event);
            }
        });
    }

    public void setDocument(final DocumentDTO document) {
        this.document = document;
        this.documentID = document.getDocumentID();

        // update the header
        this.documentHeaderController.setDocumentName(document.getName());

        // set the deadline
        this.deadlineController.setDeadline(document.getDeadline());

        serviceFactory.getGwtDocumentService().getAvailableTranslations(clientFactory.getClientContext(), document.getDocumentID(), new AsyncCallback<ArrayList<DocumentDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Log.warn("No translations available.", caught);
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

    public void wrapContent() {
        final Element[] contentElements = contentController.getContentElements();
        if (amendableWidgets == null) amendableWidgets = new ArrayList<AmendableWidget>();
        for (final Element element : contentElements) {
            final AmendableWidget rootAmendableWidget = wrap(element, this);
            amendableWidgets.add(rootAmendableWidget);
        }
    }

    public AmendableWidget wrap(final com.google.gwt.dom.client.Element element, final AmendableWidgetListener listener) {
        return wrap(null, element, listener);
    }

    public AmendableWidget wrap(final AmendableWidget parent, final com.google.gwt.dom.client.Element element, final AmendableWidgetListener listener) {
        // Assert that the element is attached.
        // assert Document.get().getBody().isOrHasChild(element) : "element is not attached to the document -- BUG";

        final AmendableWidget amendableWidget = overlayFactory.getAmendableWidget(element);
        if (amendableWidget != null) {
            amendableWidget.setParentAmendableWidget(parent);

            // process all properties
            amendableWidget.setAmendable(overlayStrategy.isAmendable(element));
            amendableWidget.setImmutable(overlayStrategy.isImmutable(element));

            if (amendableWidget.isAmendable()) {
                amendableWidget.setContent(overlayStrategy.getContent(element));
            }

            // attach all children (note, this is a recursive call)
            final Element[] children = overlayStrategy.getChildren(element);
            for (final Element child : children) {
                final AmendableWidget amendableChild = wrap(amendableWidget, child, listener);
                amendableWidget.addAmendableWidget(amendableChild);
            }

            // if the widget is amendable, register a listener for its events
            if (amendableWidget.isAmendable() != null && amendableWidget.isAmendable()) {
                amendableWidget.setListener(listener);
            }
            // post process the widget (eg. hide large tables)
            amendableWidget.postProcess();
        }
        return amendableWidget;
    }

    public DocumentView getView() {
        return view;
    }

    public void setWidth(final String width) {
        view.setWidth(width);
    }

    @Override
    public void onAmend(AmendableWidget sender) {
        throw new RuntimeException("Not yet migrated away ...");
    }

    @Override
    public void onAdd(AmendableWidget sender, AmendableWidget amendableWidget, boolean asChild) {
        throw new RuntimeException("Not yet migrated away ...");
    }

    @Override
    public void onAddWithExternalSource(AmendableWidget sender, AmendableWidget amendableWidget, boolean asChild) {
        throw new RuntimeException("Not yet migrated away ...");
    }

    @Override
    public void onAmendWithChildren(AmendableWidget sender) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onAmendWithFootnotes(AmendableWidget sender) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onDelete(AmendableWidget sender) {
        Log.info("[Event: D] " + sender);
    }

    @Override
    public void onTranslate(AmendableWidget sender, String languageIso) {
        Log.info("[Event: Tl] " + sender);
    }

    @Override
    public void onTransfer(AmendableWidget sender) {
        Log.info("[Event: Tr] " + sender);
    }

    @Override
    public void onClick(AmendableWidget sender) {
        Log.info("[Event: Cl] " + sender);
    }

    @Override
    public void onDblClick(AmendableWidget sender) {
        clientFactory.getEventBus().fireEvent(new AmendmentContainerCreateEvent(sender, AmendmentAction.MODIFICATION));
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

    @Override
    public String toString() {
        return "Document controller " + documentID + " (" + super.toString() + ")";
    }
}
