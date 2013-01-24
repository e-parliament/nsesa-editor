package org.nsesa.editor.gwt.editor.client.event.amendments;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

import java.util.List;

/**
 * An event raised when a selection gets applied.
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentControllerSelectedEvent extends GwtEvent<AmendmentControllerSelectedEventHandler> {
    public static final Type<AmendmentControllerSelectedEventHandler> TYPE = new Type<AmendmentControllerSelectedEventHandler>();

    private final List<AmendmentController> selected;

    public AmendmentControllerSelectedEvent(List<AmendmentController> selected) {
        this.selected = selected;
    }

    @Override
    public Type<AmendmentControllerSelectedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentControllerSelectedEventHandler handler) {
        handler.onEvent(this);
    }

    public List<AmendmentController> getSelected() {
        return selected;
    }
}
