package org.nsesa.editor.gwt.dialog.client.ui.handler.modify.author;

import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.dialog.client.ui.handler.modify.AmendmentModifyAwareController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DIALOG;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(DIALOG)
public class AuthorPanelController implements AmendmentModifyAwareController {

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

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void setAmendmentAndAmendableWidget(AmendmentContainerDTO amendment, AmendableWidget amendableWidget) {
        // set up the views
    }

    private void registerListeners() {
        // nothing yet
    }

    public AuthorPanelView getView() {
        return view;
    }

    @Override
    public String getTitle() {
        return "Author";
    }
}
