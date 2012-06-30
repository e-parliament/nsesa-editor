package org.nsesa.editor.gwt.editor.client.ui.document;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.ui.actionbar.ActionBarController;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendableWidgetListener;
import org.nsesa.editor.gwt.core.client.util.DOMHelper;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
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
public class DocumentController extends Composite implements AmendableWidgetListener {

    private DocumentView view;
    private DocumentDTO document;

    private final ClientFactory clientFactory;
    private final ServiceFactory serviceFactory;
    private final MarkerController markerController;
    private final DocumentHeaderController documentHeaderController;
    private final ContentController contentController;
    private final ActionBarController actionBarController;

    private ArrayList<AmendableWidget> amendableWidgets;

    @Inject
    public DocumentController(final ClientFactory clientFactory, final ServiceFactory serviceFactory,
                              final DocumentView view,
                              final MarkerController markerController,
                              final ContentController contentController,
                              final DocumentHeaderController documentHeaderController,
                              final ActionBarController actionBarController) {
        assert view != null : "View is not set --BUG";

        this.view = view;
        this.clientFactory = clientFactory;
        this.serviceFactory = serviceFactory;

        this.markerController = markerController;
        this.contentController = contentController;
        this.documentHeaderController = documentHeaderController;
        this.actionBarController = actionBarController;

        // set references in the child controllers
        this.markerController.setDocumentController(this);
        this.contentController.setDocumentController(this);
        this.documentHeaderController.setDocumentController(this);

        registerListeners();

        doLayout();
    }

    private void doLayout() {
        view.getContentPanel().add(contentController.getView());
        view.getMarkerPanel().add(markerController.getView());
        view.getDocumentHeaderPanel().add(documentHeaderController.getView());
    }

    private void registerListeners() {
    }

    public void setDocument(final DocumentDTO document) {
        this.document = document;

        // update the header
        this.documentHeaderController.setDocumentName(document.getName());

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

    public void wrapContent() {
        final Element[] contentElements = contentController.getContentElements();
        if (amendableWidgets == null) amendableWidgets = new ArrayList<AmendableWidget>();
        for (Element element : contentElements) {
            amendableWidgets.add(DOMHelper.wrap(element, this));
        }
    }

    public String getDocumentID() {
        return document.getDocumentID();
    }

    public DocumentView getView() {
        return view;
    }

    @Override
    public void setWidth(final String width) {
        view.setWidth(width);
    }

    @Override
    public void onAmend(AmendableWidget sender) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onAdd(AmendableWidget sender, AmendableWidget amendableWidget, boolean asChild) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onAddWithExternalSource(AmendableWidget sender, AmendableWidget amendableWidget, boolean asChild) {
        //To change body of implemented methods use File | Settings | File Templates.
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
        Log.info("[Event: DC] " + sender);
    }

    @Override
    public void onMouseOver(AmendableWidget sender) {
//        Log.info("[Event: OMOv] " + sender);
        actionBarController.attach(sender);
    }

    @Override
    public void onMouseOut(AmendableWidget sender) {
//        Log.info("[Event: OMOu] " + sender);

    }
}
