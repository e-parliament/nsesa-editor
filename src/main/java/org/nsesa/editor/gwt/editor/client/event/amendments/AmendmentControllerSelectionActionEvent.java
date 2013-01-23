package org.nsesa.editor.gwt.editor.client.event.amendments;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

import java.util.List;

/**
 * An event raised when the user select a selection
 * User: groza
 * Date: 29/11/12
 * Time: 10:42
 */
public class AmendmentControllerSelectionActionEvent extends GwtEvent<AmendmentControllerSelectionActionEventHandler> {

    public static interface Action {
        void execute(List<AmendmentController> amendmentControllers);
    }

    public static final Type<AmendmentControllerSelectionActionEventHandler> TYPE = new Type<AmendmentControllerSelectionActionEventHandler>();

    private final Action action;

    public AmendmentControllerSelectionActionEvent(Action action) {
        this.action = action;
    }

    @Override
    public Type<AmendmentControllerSelectionActionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentControllerSelectionActionEventHandler handler) {
        handler.onEvent(this);
    }

    public Action getAction() {
        return action;
    }
}
