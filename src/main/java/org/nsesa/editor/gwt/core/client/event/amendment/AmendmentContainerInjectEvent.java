package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerInjectEvent extends GwtEvent<AmendmentContainerInjectEventHandler> {

    public static Type<AmendmentContainerInjectEventHandler> TYPE = new Type<AmendmentContainerInjectEventHandler>();

    private final AmendmentContainerDTO[] amendments;

    public AmendmentContainerInjectEvent(AmendmentContainerDTO amendment) {
        this.amendments = new AmendmentContainerDTO[]{amendment};
    }

    public AmendmentContainerInjectEvent(AmendmentContainerDTO[] amendments) {
        this.amendments = amendments;
    }

    @Override
    public Type<AmendmentContainerInjectEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerInjectEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentContainerDTO[] getAmendments() {
        return amendments;
    }
}
