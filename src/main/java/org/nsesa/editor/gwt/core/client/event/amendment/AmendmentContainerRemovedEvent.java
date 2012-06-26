package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerRemovedEvent extends GwtEvent<AmendmentContainerRemovedEventHandler> {

    public static Type<AmendmentContainerRemovedEventHandler> TYPE = new Type<AmendmentContainerRemovedEventHandler>();

    public AmendmentContainerRemovedEvent() {

    }

    @Override
    public Type<AmendmentContainerRemovedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerRemovedEventHandler handler) {
        handler.onEvent(this);
    }
}
