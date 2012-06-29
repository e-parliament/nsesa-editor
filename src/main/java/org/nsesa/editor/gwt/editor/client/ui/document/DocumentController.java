package org.nsesa.editor.gwt.editor.client.ui.document;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
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
public class DocumentController extends Composite {

    private DocumentView view;
    private DocumentDTO document;

    private final ClientFactory clientFactory;
    private final ServiceFactory serviceFactory;
    private final MarkerController markerController;
    private final DocumentHeaderController documentHeaderController;
    private final ContentController contentController;

    @Inject
    public DocumentController(final ClientFactory clientFactory, final ServiceFactory serviceFactory,
                              final DocumentView view,
                              final MarkerController markerController,
                              final ContentController contentController,
                              final DocumentHeaderController documentHeaderController) {
        assert view != null : "View is not set --BUG";

        this.view = view;
        this.clientFactory = clientFactory;
        this.serviceFactory = serviceFactory;

        this.markerController = markerController;
        this.contentController = contentController;
        this.documentHeaderController = documentHeaderController;

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
        contentController.overlay();
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
}
