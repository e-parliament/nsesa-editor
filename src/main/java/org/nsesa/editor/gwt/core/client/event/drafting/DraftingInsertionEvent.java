package org.nsesa.editor.gwt.core.client.event.drafting;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * An event occured when the user wants to draft an amendment
 * User: groza
 * Date: 17/01/13
 * Time: 15:24
 */
public class DraftingInsertionEvent extends GwtEvent<DraftingInsertionEventHandler>{

    public static final Type<DraftingInsertionEventHandler> TYPE = new Type<DraftingInsertionEventHandler>();

    private AmendableWidget amendableWidget;

    public DraftingInsertionEvent(AmendableWidget amendableWidget) {
        this.amendableWidget = amendableWidget;
    }

    @Override
    public Type<DraftingInsertionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DraftingInsertionEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendableWidget getAmendableWidget() {
        return amendableWidget;
    }
}
