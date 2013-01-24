package org.nsesa.editor.gwt.core.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class SwitchTabEvent extends GwtEvent<SwitchTabEventHandler> {

    public static final Type<SwitchTabEventHandler> TYPE = new Type<SwitchTabEventHandler>();

    private final int tabIndex;

    public SwitchTabEvent(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    @Override
    public Type<SwitchTabEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SwitchTabEventHandler handler) {
        handler.onEvent(this);
    }

    public int getTabIndex() {
        return tabIndex;
    }
}
