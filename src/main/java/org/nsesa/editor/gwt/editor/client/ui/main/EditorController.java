package org.nsesa.editor.gwt.editor.client.ui.main;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.BootstrapEvent;
import org.nsesa.editor.gwt.core.client.event.BootstrapEventHandler;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.ResizeEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentRefreshRequestEventHandler;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.nsesa.editor.gwt.editor.client.Injector;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentView;

import java.util.ArrayList;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EditorController extends Composite implements BootstrapEventHandler, DocumentRefreshRequestEventHandler {

    private final EditorView view;
    private final ClientFactory clientFactory;
    private final ServiceFactory serviceFactory;
    private final Injector injector;

    private final ArrayList<DocumentController> documentControllers = new ArrayList<DocumentController>();

    @Inject
    public EditorController(final EditorView view, final ClientFactory clientFactory, final ServiceFactory serviceFactory, final Injector injector) {
        assert view != null : "View is not set --BUG";

        this.view = view;
        this.clientFactory = clientFactory;
        this.serviceFactory = serviceFactory;
        this.injector = injector;

        registerListeners();
    }

    private void registerListeners() {
        clientFactory.getEventBus().addHandler(BootstrapEvent.TYPE, this);
        clientFactory.getEventBus().addHandler(DocumentRefreshRequestEvent.TYPE, this);
    }

    @Override
    public void onEvent(BootstrapEvent event) {

        Log.info("Received bootstrap event.");

        final EventBus eventBus = clientFactory.getEventBus();

        final String[] documentIDs = event.getClientContext().getDocumentIDs();

        if (documentIDs == null) {
            // no document ids provided in the client context?
            final String message = clientFactory.getCoreMessages().errorDocumentIdMissing();
            eventBus.fireEvent(new CriticalErrorEvent(message));

        } else {
            for (final String documentID : documentIDs) {
                // request the amendment in the backend
                fetchDocument(documentID, true);
            }
        }
    }

    @Override
    public void onEvent(DocumentRefreshRequestEvent event) {
        // find the origin of this event
        DocumentController documentController = getDocumentController(event.getDocumentID());
        if (documentController != null) {
            fetchContent(documentController);
        }
    }

    protected DocumentController addDocument(final DocumentDTO documentDTO) {
        final DocumentController documentController = injector.getDocumentController();
        documentController.setDocument(documentDTO);
        return documentController;
    }

    public boolean addDocumentController(final DocumentController documentController, final boolean autoCreateView) {
        boolean added = documentControllers.add(documentController);
        if (added && autoCreateView) {
            final DocumentView documentControllerView = documentController.getView();
            view.getDocumentsPanel().add(documentControllerView);
            doLayout();
        }
        return added;
    }

    public boolean removeDocumentController(final DocumentController documentController, final boolean autoRemoveView) {
        final boolean removed = documentControllers.remove(documentController);
        if (removed && autoRemoveView) {
            if (view.getDocumentsPanel().remove(documentController.getView())) {
                doLayout();
            }
        }
        return removed;
    }

    private void fetchDocument(final String documentID, final boolean autoCreateView) {
        serviceFactory.getGwtDocumentService().getDocument(clientFactory.getClientContext(), documentID, new AsyncCallback<DocumentDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                final String message = clientFactory.getCoreMessages().errorDocumentError(documentID);
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent(message));
            }

            @Override
            public void onSuccess(DocumentDTO document) {
                final DocumentController documentController = addDocument(document);
                addDocumentController(documentController, autoCreateView);
                fetchContent(documentController);
            }
        });
    }

    private void fetchContent(final DocumentController documentController) {
        serviceFactory.getGwtDocumentService().getDocumentContent(clientFactory.getClientContext(), documentController.getDocumentID(), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                final String message = clientFactory.getCoreMessages().errorDocumentError(documentController.getDocumentID());
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent(message));
            }

            @Override
            public void onSuccess(String content) {
                documentController.setContent(content);
                documentController.wrapContent();
            }
        });
    }

    private DocumentController getDocumentController(String documentID) {
        for (final DocumentController documentController : documentControllers) {
            if (documentID.equals(documentController.getDocumentID())) {
                return documentController;
            }
        }
        return null;
    }

    private void doLayout() {
        clientFactory.getEventBus().fireEvent(new ResizeEvent(Window.getClientHeight(), Window.getClientWidth()));

        // There seems to be no other way to dynamically set the width of the children
        // for an evenly distributed width
        for (final DocumentController d : documentControllers) {
            final String width = (100 / documentControllers.size()) + "%";
            final CellPanel documentsPanel = view.getDocumentsPanel();
            documentsPanel.setCellWidth(d.getView(), width);
        }
    }

    public EditorView getView() {
        return view;
    }
}
