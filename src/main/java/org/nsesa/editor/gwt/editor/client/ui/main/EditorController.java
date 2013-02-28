/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
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
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.BootstrapEvent;
import org.nsesa.editor.gwt.core.client.event.BootstrapEventHandler;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.Injector;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(EDITOR)
public class EditorController implements BootstrapEventHandler {

    private static final Logger LOG = Logger.getLogger(EditorController.class.getName());

    private final EditorView view;
    private final ClientFactory clientFactory;
    private final ServiceFactory serviceFactory;

    private Injector injector;

    private final List<DocumentController> documentControllers = new ArrayList<DocumentController>();

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
        clientFactory.getEventBus().addHandler(BootstrapEvent.TYPE, this);
    }

    @Override
    public void onEvent(BootstrapEvent event) {
        LOG.info("Received bootstrap event.");
        final String[] documentIDs = event.getClientContext().getDocumentIDs();

        if (documentIDs == null) {
            // no document ids provided in the client context?
            final String message = clientFactory.getCoreMessages().errorDocumentIdMissing();
            clientFactory.getEventBus().fireEvent(new CriticalErrorEvent(message));
        } else {
            // retrieve the documents
            for (final String documentID : clientFactory.getClientContext().getDocumentIDs()) {
                final DocumentController documentController = injector.getDocumentController();
                documentController.setDocumentID(documentID);
                addDocumentController(documentController);
                // request the amendments in the backend, this will fire off the document loading afterwards
                documentController.loadDocument();
            }
        }
    }

    public boolean addDocumentController(final DocumentController documentController) {
        boolean added = documentControllers.add(documentController);
        if (added) {
            view.getDocumentsPanel().add(documentController.getView());
            doLayout();
        } else {
            throw new RuntimeException("Probably a bug.");
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
            view.getDocumentsPanel().setCellWidth(d.getView(), width);
        }
    }

    public EditorView getView() {
        return view;
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }
}
