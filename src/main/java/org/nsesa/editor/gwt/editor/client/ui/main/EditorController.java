package org.nsesa.editor.gwt.editor.client.ui.main;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.BootstrapEvent;
import org.nsesa.editor.gwt.core.client.event.BootstrapEventHandler;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.ResizeEvent;
import org.nsesa.editor.gwt.core.client.event.document.ContentRequestEvent;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.nsesa.editor.gwt.editor.client.Injector;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.ArrayList;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EditorController extends Composite implements BootstrapEventHandler {

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
                serviceFactory.getGwtDocumentService().getDocument(clientFactory.getClientContext(), documentID, new AsyncCallback<DocumentDTO>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        // TODO check the type of caught exception to differentiate the action
                        final String message = clientFactory.getCoreMessages().errorDocumentError(documentID);
                        eventBus.fireEvent(new CriticalErrorEvent(message));
                    }

                    @Override
                    public void onSuccess(DocumentDTO document) {
                        addDocument(document);
                        doLayout();
                        eventBus.fireEvent(new ContentRequestEvent(document));
                    }
                });
            }
        }
    }

    public void addDocument(DocumentDTO documentDTO) {
        final DocumentController documentController = injector.getDocumentController();
        documentController.setDocument(documentDTO);
        addDocumentController(documentController);
    }

    public void addDocumentController(DocumentController documentController) {
        documentControllers.add(documentController);
        view.getDocumentsPanel().add(documentController.getView());
    }

    private void doLayout() {
        clientFactory.getEventBus().fireEvent(new ResizeEvent(Window.getClientHeight(), Window.getClientWidth()));
    }

    public EditorView getView() {
        return view;
    }
}
