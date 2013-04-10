/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.editor.client.ui.main;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.BootstrapEvent;
import org.nsesa.editor.gwt.core.client.event.BootstrapEventHandler;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.Injector;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Main editor controller. This controller acts as a singleton and is responsible for the instantiating of
 * one or more {@link DocumentController}s, which in turn will load documents and amendments.
 * <p/>
 * Date: 24/06/12 18:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(EDITOR)
public class EditorController implements BootstrapEventHandler {

    private static final Logger LOG = Logger.getLogger(EditorController.class.getName());

    /**
     * The associated view.
     */
    protected final EditorView view;

    /**
     * The client factory, giving access to local dependencies such as the global event bus, scheduler, and client
     * context information about the currently logged in user.
     */
    protected final ClientFactory clientFactory;

    /**
     * The service factory, giving access to asynchronous RPC services.
     */
    protected final ServiceFactory serviceFactory;

    /**
     * The injector to instantiate {@link DocumentController}s.
     */
    protected Injector injector;

    /**
     * The list of available {@link DocumentController}s.
     */
    protected final List<DocumentController> documentControllers = new ArrayList<DocumentController>();
    private HandlerRegistration bootstrapEventHandlerRegistration;

    @Inject
    public EditorController(final EditorView view,
                            final ClientFactory clientFactory,
                            final ServiceFactory serviceFactory) {
        assert view != null : "View is not set --BUG";

        this.view = view;
        this.clientFactory = clientFactory;
        this.serviceFactory = serviceFactory;

        registerListeners();
    }

    private void registerListeners() {
        bootstrapEventHandlerRegistration = clientFactory.getEventBus().addHandler(BootstrapEvent.TYPE, this);
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        bootstrapEventHandlerRegistration.removeHandler();
    }

    /**
     * Callback on the {@link BootstrapEvent}, to indicate the user context has been set up, and all local
     * dependencies are available.
     *
     * @param event
     */
    @Override
    public void onEvent(BootstrapEvent event) {
        LOG.info("Received bootstrap event.");
        final String[] documentIDs = event.getClientContext().getDocumentIDs();

        if (documentIDs == null) {
            // no document ids provided in the client context?
            // TODO handle gracefully, perhaps a list of recent documents?
            final String message = clientFactory.getCoreMessages().errorDocumentIdMissing();
            clientFactory.getEventBus().fireEvent(new CriticalErrorEvent(message));
        } else {
            // retrieve the documents
            for (final String documentID : clientFactory.getClientContext().getDocumentIDs()) {
                final DocumentController documentController = injector.getDocumentController();
                documentController.setInjector(documentController.getInjector());
                documentController.registerListeners();
                documentController.registerModes();
                documentController.registerKeyCombos();
                documentController.setDocumentID(documentID);
                addDocumentController(documentController);
                // request the amendments in the backend, this will fire off the document loading afterwards
                documentController.loadDocument();
            }
        }
    }

    /**
     * Add a {@link DocumentController} to the list of current domain controllers, and update the layout if necessary.
     *
     * @param documentController the document controller to add
     * @return <tt>true</tt> if the document controller was added
     */
    public boolean addDocumentController(final DocumentController documentController) {
        boolean added = documentControllers.add(documentController);
        if (added) {
            view.getDocumentsPanel().add(documentController.getView());
            doLayout();
        } else {
            throw new RuntimeException("Document controller not added - was it already added?");
        }
        return added;
    }

    /**
     * Remove a {@link DocumentController} from the current document controllers.
     *
     * @param documentController the document controller to remove
     * @return <tt>true</tt> if the document controller was removed
     */
    public boolean removeDocumentController(final DocumentController documentController) {
        final boolean removed = documentControllers.remove(documentController);
        if (removed) {
            if (view.getDocumentsPanel().remove(documentController.getView())) {
                doLayout();
            }
        }
        return removed;
    }

    /**
     * Get a given document controller by the given <tt>documentID</tt>. Note that this will check the document
     * controller's current document, so if another document was loaded into the document controller, then its
     * documentID might have changed.
     *
     * @param documentID the document ID
     * @return the {@link DocumentController} with the given <tt>documentID</tt>, or <tt>null</tt> if it does not exist.
     */
    public DocumentController getDocumentController(final String documentID) {
        for (final DocumentController documentController : documentControllers) {
            if (documentID.equals(documentController.getDocumentID())) {
                return documentController;
            }
        }
        return null;
    }

    /**
     * Perform a layout adaptation to handle the number of document controllers horizontally by dividing the available
     * screen estate.
     */
    protected void doLayout() {
        // There seems to be no other way to dynamically set the width of the children
        // for an evenly distributed width
        for (final DocumentController d : documentControllers) {
            final String width = (100 / documentControllers.size()) + "%";
            view.getDocumentsPanel().setCellWidth(d.getView(), width);
        }
    }

    /**
     * Return the associated view.
     *
     * @return the view
     */
    public EditorView getView() {
        return view;
    }

    /**
     * Set the injector to use to instantiate the {@link DocumentController}.
     *
     * @param injector the injector to use
     */
    public void setInjector(Injector injector) {
        this.injector = injector;
    }
}
