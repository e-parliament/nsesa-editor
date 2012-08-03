package org.nsesa.editor.gwt.editor.client.event.main;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ShowInfoTabEvent extends GwtEvent<ShowInfoTabEventHandler> {

    public static Type<ShowInfoTabEventHandler> TYPE = new Type<ShowInfoTabEventHandler>();

    @Override
    public Type<ShowInfoTabEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowInfoTabEventHandler handler) {
        handler.onEvent(this);
    }
}
