package org.nsesa.editor.gwt.core.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ResizeEvent extends GwtEvent<ResizeEventHandler> {

    public static final Type<ResizeEventHandler> TYPE = new Type<ResizeEventHandler>();

    final int height;
    final int width;

    public ResizeEvent(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public Type<ResizeEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ResizeEventHandler handler) {
        handler.onEvent(this);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
