package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerUpdatedEvent extends GwtEvent<AmendmentContainerUpdatedEventHandler> {

    public static final Type<AmendmentContainerUpdatedEventHandler> TYPE = new Type<AmendmentContainerUpdatedEventHandler>();

    private final AmendmentController oldRevision;
    private final AmendmentController newRevision;

    public AmendmentContainerUpdatedEvent(AmendmentController oldRevision, AmendmentController newRevision) {
        this.oldRevision = oldRevision;
        this.newRevision = newRevision;
    }

    @Override
    public Type<AmendmentContainerUpdatedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerUpdatedEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentController getNewRevision() {
        return newRevision;
    }

    public AmendmentController getOldRevision() {
        return oldRevision;
    }
}
