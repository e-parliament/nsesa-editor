package org.nsesa.editor.gwt.core.client.event.deadline;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class Deadline24HourEvent extends GwtEvent<Deadline24HourEventHandler> {

    public static Type<Deadline24HourEventHandler> TYPE = new Type<Deadline24HourEventHandler>();

    @Override
    public Type<Deadline24HourEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Deadline24HourEventHandler handler) {
        handler.onEvent(this);
    }
}
