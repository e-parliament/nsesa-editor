package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerInjectedEvent extends GwtEvent<AmendmentContainerInjectedEventHandler> {

    public static Type<AmendmentContainerInjectedEventHandler> TYPE = new Type<AmendmentContainerInjectedEventHandler>();

    public AmendmentContainerInjectedEvent() {

    }

    @Override
    public Type<AmendmentContainerInjectedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerInjectedEventHandler handler) {
        handler.onEvent(this);
    }
}
