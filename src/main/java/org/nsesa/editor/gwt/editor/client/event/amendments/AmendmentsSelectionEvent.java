package org.nsesa.editor.gwt.editor.client.event.amendments;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.util.Selection;

/**
 * An event raised when the user select a selection
 * User: groza
 * Date: 29/11/12
 * Time: 10:42
 */
public class AmendmentsSelectionEvent extends GwtEvent<AmendmentsSelectionEventHandler> {
    public static final Type<AmendmentsSelectionEventHandler> TYPE = new Type<AmendmentsSelectionEventHandler>();

    private Selection<AmendmentController> selection;

    public AmendmentsSelectionEvent(Selection<AmendmentController> selection) {
        this.selection = selection;
    }

    @Override
    public Type<AmendmentsSelectionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentsSelectionEventHandler handler) {
        handler.onEvent(this);
    }

    public Selection<AmendmentController> getSelection() {
        return selection;
    }
}
