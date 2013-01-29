package org.nsesa.editor.gwt.editor.client.ui.document.header;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class DocumentHeaderController {

    private final DocumentHeaderView view;
    private final DocumentEventBus documentEventBus;
    private DocumentController documentController;

    @Inject
    public DocumentHeaderController(final DocumentEventBus documentEventBus, final DocumentHeaderView view) {
        assert view != null : "View is not set --BUG";

        this.view = view;
        this.documentEventBus = documentEventBus;

        registerListeners();
    }

    private void registerListeners() {

    }

    public DocumentHeaderView getView() {
        return view;
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }
}
