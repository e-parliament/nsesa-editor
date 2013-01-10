package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerStatusUpdatedEvent extends GwtEvent<AmendmentContainerStatusUpdatedEventHandler> {

    public static final Type<AmendmentContainerStatusUpdatedEventHandler> TYPE = new Type<AmendmentContainerStatusUpdatedEventHandler>();

    private final AmendmentController amendmentController;
    private final String oldStatus;

    public AmendmentContainerStatusUpdatedEvent(AmendmentController amendmentController, String oldStatus) {
        this.amendmentController = amendmentController;
        this.oldStatus = oldStatus;
    }

    @Override
    public Type<AmendmentContainerStatusUpdatedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerStatusUpdatedEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentController getAmendmentController() {
        return amendmentController;
    }

    public String getOldStatus() {
        return oldStatus;
    }
}
