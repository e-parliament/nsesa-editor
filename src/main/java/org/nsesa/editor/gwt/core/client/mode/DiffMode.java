package org.nsesa.editor.gwt.core.client.mode;

import org.nsesa.editor.gwt.core.client.amendment.AmendableWidgetWalker;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 26/11/12 14:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DiffMode implements DocumentMode<ActiveState> {

    public static final String KEY = "diff";

    private final DocumentController documentController;

    private ActiveState activeState = new ActiveState(false);

    public DiffMode(DocumentController documentController) {
        this.documentController = documentController;
    }

    @Override
    public boolean apply(ActiveState state) {
        // TODO: actually change the diffing for the amendments ...
        if (state.isActive()) {
            documentController.walk(new AmendableWidgetWalker.AmendableVisitor() {
                @Override
                public boolean visit(AmendableWidget visited) {
                    if (visited.isAmended()) {
                        for (final AmendmentController amendmentController : visited.getAmendmentControllers()) {

                        }
                    }
                    return true;
                }
            });
        } else {
            documentController.walk(new AmendableWidgetWalker.AmendableVisitor() {
                @Override
                public boolean visit(AmendableWidget visited) {
                    if (visited.isAmended()) {
                        for (final AmendmentController amendmentController : visited.getAmendmentControllers()) {

                        }
                    }
                    return true;
                }
            });
        }
        this.activeState = state;
        return true;
    }

    @Override
    public ActiveState getState() {
        return activeState;
    }
}
