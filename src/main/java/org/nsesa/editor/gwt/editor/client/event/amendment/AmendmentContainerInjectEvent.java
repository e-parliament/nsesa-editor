package org.nsesa.editor.gwt.editor.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerInjectEvent extends GwtEvent<AmendmentContainerInjectEventHandler> {

    public static Type<AmendmentContainerInjectEventHandler> TYPE = new Type<AmendmentContainerInjectEventHandler>();

    public AmendmentContainerInjectEvent() {

    }

    @Override
    public Type<AmendmentContainerInjectEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerInjectEventHandler handler) {
        handler.onEvent(this);
    }
}
