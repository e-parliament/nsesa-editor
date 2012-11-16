package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerStatusUpdatedEvent extends GwtEvent<AmendmentContainerStatusUpdatedEventHandler> {

    public static final Type<AmendmentContainerStatusUpdatedEventHandler> TYPE = new Type<AmendmentContainerStatusUpdatedEventHandler>();

    private final AmendmentContainerDTO amendment;
    private final String oldStatus;

    public AmendmentContainerStatusUpdatedEvent(final AmendmentContainerDTO amendment, final String oldStatus) {
        this.amendment = amendment;
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

    public AmendmentContainerDTO getAmendment() {
        return amendment;
    }

    public String getOldStatus() {
        return oldStatus;
    }
}
