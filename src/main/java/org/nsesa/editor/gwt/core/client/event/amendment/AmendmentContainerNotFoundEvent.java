package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerNotFoundEvent extends GwtEvent<AmendmentContainerNotFoundEventHandler> {

    public static Type<AmendmentContainerNotFoundEventHandler> TYPE = new Type<AmendmentContainerNotFoundEventHandler>();

    public AmendmentContainerNotFoundEvent() {

    }

    @Override
    public Type<AmendmentContainerNotFoundEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerNotFoundEventHandler handler) {
        handler.onEvent(this);
    }
}
