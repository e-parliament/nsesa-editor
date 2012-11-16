package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerSkippedEvent extends GwtEvent<AmendmentContainerSkippedEventHandler> {

    public static final Type<AmendmentContainerSkippedEventHandler> TYPE = new Type<AmendmentContainerSkippedEventHandler>();

    public AmendmentContainerSkippedEvent() {

    }

    @Override
    public Type<AmendmentContainerSkippedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerSkippedEventHandler handler) {
        handler.onEvent(this);
    }
}
