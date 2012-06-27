package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.document.ContentRequestEvent;
import org.nsesa.editor.gwt.core.client.event.document.ContentRequestEventHandler;
import org.nsesa.editor.gwt.core.client.event.document.ContentResponseEvent;
import org.nsesa.editor.gwt.core.client.event.document.ContentResponseEventHandler;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.nsesa.editor.gwt.editor.client.ui.document.content.ContentController;
import org.nsesa.editor.gwt.editor.client.ui.document.marker.MarkerController;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentController extends Composite implements ContentResponseEventHandler, ContentRequestEventHandler {

    private DocumentView view;
    private DocumentDTO document;

    private final ClientFactory clientFactory;
    private final ServiceFactory serviceFactory;
    private final MarkerController markerController;
    private final ContentController contentController;

    @Inject
    public DocumentController(final ClientFactory clientFactory, final ServiceFactory serviceFactory,
                              final DocumentView view, final MarkerController markerController,
                              final ContentController contentController) {
        assert view != null : "View is not set --BUG";

        this.view = view;
        this.clientFactory = clientFactory;
        this.serviceFactory = serviceFactory;
        this.markerController = markerController;
        this.contentController = contentController;

        registerListeners();

        view.getContentPanel().add(contentController.getView());
        view.getMarkerPanel().add(markerController.getView());
    }

    private void registerListeners() {
        clientFactory.getEventBus().addHandler(ContentRequestEvent.TYPE, this);
        clientFactory.getEventBus().addHandler(ContentResponseEvent.TYPE, this);
    }

    @Override
    public void onEvent(ContentRequestEvent event) {
        if (document.equals(event.getDocument())) {
            serviceFactory.getGwtDocumentService().getDocumentContent(clientFactory.getClientContext(), document.getDocumentID(), new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    final String message = clientFactory.getCoreMessages().errorDocumentError(document.getDocumentID());
                    clientFactory.getEventBus().fireEvent(new CriticalErrorEvent(message));
                }

                @Override
                public void onSuccess(String content) {
                    clientFactory.getEventBus().fireEvent(new ContentResponseEvent(document, content));
                }
            });
        } else {
            // ignore, this not our request
        }
    }

    @Override
    public void onEvent(ContentResponseEvent event) {
        if (document.equals(event.getDocument())) {
            contentController.setContent(event.getDocumentContent());
        }
    }

    public void setDocument(DocumentDTO document) {
        this.document = document;
    }

    public DocumentView getView() {
        return view;
    }
}
