package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerDeletedEvent extends GwtEvent<AmendmentContainerDeletedEventHandler> {

    public static final Type<AmendmentContainerDeletedEventHandler> TYPE = new Type<AmendmentContainerDeletedEventHandler>();

    private final AmendmentController amendmentController;

    public AmendmentContainerDeletedEvent(AmendmentController amendmentController) {
        this.amendmentController = amendmentController;
    }

    @Override
    public Type<AmendmentContainerDeletedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerDeletedEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentController getAmendmentController() {
        return amendmentController;
    }
}
