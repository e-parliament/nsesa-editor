package org.nsesa.editor.gwt.dialog.client.ui.handler.common.meta;

import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.AmendmentDialogAwareController;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DIALOG;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(DIALOG)
public class MetaPanelController implements AmendmentDialogAwareController {

    private final MetaPanelView view;
    private final MetaPanelViewCss metaPanelViewCss;

    private final ClientFactory clientFactory;

    private DialogContext dialogContext;

    @Inject
    public MetaPanelController(final ClientFactory clientFactory, final MetaPanelView view,
                               final MetaPanelViewCss metaPanelViewCss) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.metaPanelViewCss = metaPanelViewCss;
        registerListeners();
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void setContext(final DialogContext dialogContext) {
        this.dialogContext = dialogContext;
    }

    private void registerListeners() {
        // nothing yet
    }

    public MetaPanelView getView() {
        return view;
    }

    @Override
    public String getTitle() {
        return "Meta";
    }
}
