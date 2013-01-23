package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerDeleteEvent extends GwtEvent<AmendmentContainerDeleteEventHandler> {

    public static final Type<AmendmentContainerDeleteEventHandler> TYPE = new Type<AmendmentContainerDeleteEventHandler>();

    private final AmendmentController[] amendmentControllers;

    public AmendmentContainerDeleteEvent(AmendmentController... amendmentControllers) {
        this.amendmentControllers = amendmentControllers;
    }

    @Override
    public Type<AmendmentContainerDeleteEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerDeleteEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentController[] getAmendmentControllers() {
        return amendmentControllers;
    }
}
