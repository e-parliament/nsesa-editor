package org.nsesa.editor.gwt.editor.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerRemoveEvent extends GwtEvent<AmendmentContainerRemoveEventHandler> {

    public static Type<AmendmentContainerRemoveEventHandler> TYPE = new Type<AmendmentContainerRemoveEventHandler>();

    public AmendmentContainerRemoveEvent() {

    }

    @Override
    public Type<AmendmentContainerRemoveEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerRemoveEventHandler handler) {
        handler.onEvent(this);
    }
}
