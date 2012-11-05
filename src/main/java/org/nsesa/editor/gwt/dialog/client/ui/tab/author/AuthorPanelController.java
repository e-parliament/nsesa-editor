package org.nsesa.editor.gwt.dialog.client.ui.tab.author;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DIALOG;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DIALOG)
public class AuthorPanelController {

    private final AuthorPanelView view;
    private final AuthorPanelViewCss authorPanelViewCss;

    private final ClientFactory clientFactory;

    private DocumentController documentController;

    @Inject
    public AuthorPanelController(final ClientFactory clientFactory, final AuthorPanelView view,
                                 final AuthorPanelViewCss authorPanelViewCss) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.authorPanelViewCss = authorPanelViewCss;
        registerListeners();
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }

    private void registerListeners() {

    }

    public AuthorPanelView getView() {
        return view;
    }
}
