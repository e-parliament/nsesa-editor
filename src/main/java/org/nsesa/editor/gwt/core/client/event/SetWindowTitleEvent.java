package org.nsesa.editor.gwt.core.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class SetWindowTitleEvent extends GwtEvent<SetWindowTitleEventHandler> {

    public static Type<SetWindowTitleEventHandler> TYPE = new Type<SetWindowTitleEventHandler>();

    private final String title;

    public SetWindowTitleEvent(String title) {
        this.title = title;
    }

    @Override
    public Type<SetWindowTitleEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SetWindowTitleEventHandler handler) {
        handler.onEvent(this);
    }

    public String getTitle() {
        return title;
    }
}
