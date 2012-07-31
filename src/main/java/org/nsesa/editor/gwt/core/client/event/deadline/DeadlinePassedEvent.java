package org.nsesa.editor.gwt.core.client.event.deadline;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DeadlinePassedEvent extends GwtEvent<DeadlinePassedEventHandler> {

    public static Type<DeadlinePassedEventHandler> TYPE = new Type<DeadlinePassedEventHandler>();

    @Override
    public Type<DeadlinePassedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DeadlinePassedEventHandler handler) {
        handler.onEvent(this);
    }
}
