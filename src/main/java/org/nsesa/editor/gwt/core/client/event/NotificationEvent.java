package org.nsesa.editor.gwt.core.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class NotificationEvent extends GwtEvent<NotificationEventHandler> {

    public static final Type<NotificationEventHandler> TYPE = new Type<NotificationEventHandler>();

    private final int duration;
    private final String message;

    public NotificationEvent(String message) {
        this.message = message;
        this.duration = 5;
    }

    public NotificationEvent(int duration, String message) {
        this.duration = duration;
        this.message = message;
    }

    @Override
    public Type<NotificationEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(NotificationEventHandler handler) {
        handler.onEvent(this);
    }

    public int getDuration() {
        return duration;
    }

    public String getMessage() {
        return message;
    }
}
