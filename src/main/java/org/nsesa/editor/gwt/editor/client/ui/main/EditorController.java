package org.nsesa.editor.gwt.editor.client.ui.main;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.core.client.event.*;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.AmendmentDialogController;
import org.nsesa.editor.gwt.editor.client.Injector;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentRefreshRequestEventHandler;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentView;

import java.util.ArrayList;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EditorController implements BootstrapEventHandler, DocumentRefreshRequestEventHandler {

    private final EditorView view;
    private final ClientFactory clientFactory;
    private final ServiceFactory serviceFactory;
    private final Injector injector;
    private final AmendmentManager amendmentManager;
    private final AmendmentDialogController amendmentDialogController;

    private final ArrayList<DocumentController> documentControllers = new ArrayList<DocumentController>();

    @Inject
    public EditorController(final EditorView view, final ClientFactory clientFactory,
                            final ServiceFactory serviceFactory, final Injector injector,
                            final AmendmentManager amendmentManager,
                            final AmendmentDialogController amendmentDialogController) {
        assert view != null : "View is not set --BUG";

        this.view = view;
        this.clientFactory = clientFactory;
        this.serviceFactory = serviceFactory;
        this.injector = injector;
        this.amendmentManager = amendmentManager;
        this.amendmentDialogController = amendmentDialogController;

        registerListeners();
    }

    private void registerListeners() {
        clientFactory.getEventBus().addHandler(BootstrapEvent.TYPE, this);
        clientFactory.getEventBus().addHandler(DocumentRefreshRequestEvent.TYPE, this);
    }

    @Override
    public void onEvent(BootstrapEvent event) {
        Log.info("Received bootstrap event.");
        final String[] documentIDs = event.getClientContext().getDocumentIDs();

        if (documentIDs == null) {
            // no document ids provided in the client context?
            final String message = clientFactory.getCoreMessages().errorDocumentIdMissing();
            clientFactory.getEventBus().fireEvent(new CriticalErrorEvent(message));
        } else {
            // get the amendments belonging to this document
            fetchAmendments();
        }
    }

    @Override
    public void onEvent(DocumentRefreshRequestEvent event) {
        fetchContent(event.getDocumentController());
    }

    public boolean addDocumentController(final DocumentController documentController) {
        boolean added = documentControllers.add(documentController);
        if (added) {
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

    private void fetchAmendments() {
        serviceFactory.getGwtAmendmentService().getAmendmentContainers(clientFactory.getClientContext(), new AsyncCallback<AmendmentContainerDTO[]>() {
            @Override
            public void onFailure(Throwable caught) {
                final String message = clientFactory.getCoreMessages().errorAmendmentsError();
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent(message, caught));
            }

            @Override
            public void onSuccess(AmendmentContainerDTO[] result) {
                Log.info("Received " + result.length + " amendments.");
                amendmentManager.setAmendmentContainerDTOs(result);
                // after the amendments, retrieve the documents
                for (final String documentID : clientFactory.getClientContext().getDocumentIDs()) {

                    final DocumentController documentController = injector.getDocumentController();
                    documentController.setDocumentID(documentID);
                    addDocumentController(documentController);
                    // request the amendment in the backend
                    fetchDocument(documentController);
                }
            }
        });
    }

    private void fetchDocument(final DocumentController documentController) {
        serviceFactory.getGwtDocumentService().getDocument(clientFactory.getClientContext(), documentController.getDocumentID(), new AsyncCallback<DocumentDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                final String message = clientFactory.getCoreMessages().errorDocumentError(documentController.getDocumentID());
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent(message, caught));
            }

            @Override
            public void onSuccess(DocumentDTO document) {
                documentController.setDocument(document);
                final String title = clientFactory.getCoreMessages().windowTitleDocument(document.getName());
                clientFactory.getEventBus().fireEvent(new SetWindowTitleEvent(title));
                fetchContent(documentController);
            }
        });
    }

    private void fetchContent(final DocumentController documentController) {
        serviceFactory.getGwtDocumentService().getDocumentContent(clientFactory.getClientContext(), documentController.getDocumentID(), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                final String message = clientFactory.getCoreMessages().errorDocumentError(documentController.getDocumentID());
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent(message, caught));
            }

            @Override
            public void onSuccess(final String content) {
                documentController.setContent(content);
                documentController.wrapContent();
                clientFactory.getEventBus().fireEvent(new ResizeEvent(Window.getClientHeight(), Window.getClientWidth()));
                documentController.injectAmendments();
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

    protected void doLayout() {
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
