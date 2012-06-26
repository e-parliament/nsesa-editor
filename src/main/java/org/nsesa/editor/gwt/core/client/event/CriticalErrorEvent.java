package org.nsesa.editor.gwt.core.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class CriticalErrorEvent extends GwtEvent<CriticalErrorEventHandler> {

    public static Type<CriticalErrorEventHandler> TYPE = new Type<CriticalErrorEventHandler>();

    private final String message;
    private Throwable throwable;

    public CriticalErrorEvent(String message) {
        this.message = message;
    }

    public CriticalErrorEvent(String message, Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
    }

    @Override
    public Type<CriticalErrorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CriticalErrorEventHandler handler) {
        handler.onEvent(this);
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
