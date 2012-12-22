package org.nsesa.editor.gwt.dialog.client.ui.handler.modify.content;

import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext;
import org.nsesa.editor.gwt.dialog.client.ui.handler.modify.AmendmentModifyAwareController;

/**
 * Main amendment dialog. Allows for the creation and editing of amendments. Typically consists of a two
 * column layout (with the original proposed text on the left, and a rich text editor on the right).
 * <p/>
 * Requires an {@link org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO} and {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget} to be set before it can be displayed.
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ContentPanelController implements AmendmentModifyAwareController {

    private final ClientFactory clientFactory;

    private final ContentControllerView view;

    private DialogContext dialogContext;

    @Inject
    public ContentPanelController(final ClientFactory clientFactory, final ContentControllerView view) {
        this.clientFactory = clientFactory;
        this.view = view;
        registerListeners();
    }

    private void registerListeners() {
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void setContext(final DialogContext dialogContext) {
        this.dialogContext = dialogContext;
        if (dialogContext.getAmendmentController() != null) {
            // we're editing
            view.setOriginalText(dialogContext.getAmendmentController().getOriginalContent());
        } else {
            view.setOriginalText(dialogContext.getAmendableWidget().getInnerHTML());
        }
    }

    @Override
    public ContentControllerView getView() {
        return view;
    }

    @Override
    public String getTitle() {
        return "Original";
    }
}
