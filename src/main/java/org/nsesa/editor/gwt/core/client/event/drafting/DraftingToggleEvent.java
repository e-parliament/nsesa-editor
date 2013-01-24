package org.nsesa.editor.gwt.core.client.event.drafting;

import com.google.gwt.event.shared.GwtEvent;

/**
 * An event used to show/hide the drafting tool
 * User: groza
 * Date: 22/01/13
 * Time: 13:14
 */
public class DraftingToggleEvent extends GwtEvent<DraftingToggleEventHandler> {

    public static final Type<DraftingToggleEventHandler> TYPE = new Type<DraftingToggleEventHandler>();
    private boolean shown;

    public DraftingToggleEvent(boolean shown) {
        this.shown = shown;
    }

    @Override
    public Type<DraftingToggleEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DraftingToggleEventHandler handler) {
        handler.onEvent(this);
    }

    public boolean isShown() {
        return shown;
    }
}
