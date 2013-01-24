package org.nsesa.editor.gwt.editor.client.event.amendments;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

import java.util.Arrays;
import java.util.List;

/**
 * An event raised when a selection gets applied.
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentControllerRemoveFromSelectionEvent extends GwtEvent<AmendmentControllerRemoveFromSelectionEventHandler> {
    public static final Type<AmendmentControllerRemoveFromSelectionEventHandler> TYPE = new Type<AmendmentControllerRemoveFromSelectionEventHandler>();

    private final List<AmendmentController> selected;

    public AmendmentControllerRemoveFromSelectionEvent(final AmendmentController selected) {
        this.selected = Arrays.asList(selected);
    }

    public AmendmentControllerRemoveFromSelectionEvent(final List<AmendmentController> selected) {
        this.selected = selected;
    }

    @Override
    public Type<AmendmentControllerRemoveFromSelectionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentControllerRemoveFromSelectionEventHandler handler) {
        handler.onEvent(this);
    }

    public List<AmendmentController> getSelected() {
        return selected;
    }
}
