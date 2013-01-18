package org.nsesa.editor.gwt.core.client.event.drafting;

import com.google.gwt.event.shared.GwtEvent;

/**
 * An event occurred on drafting an amendment by changing the cursor location or selecting a text
 * inside of an amendment
 * User: groza
 * Date: 17/01/13
 * Time: 9:59
 */
public class SelectionChangedEvent extends GwtEvent<SelectionChangedEventHandler> {
    public static final Type<SelectionChangedEventHandler> TYPE = new Type<SelectionChangedEventHandler>();

    private String parentTagType;
    private boolean moreTagsSelected;
    private String nameSpace;
    private String selectedText;

    public SelectionChangedEvent(String parentTagType, String nameSpace, boolean moreTagsSelected, String selectedText) {
        this.parentTagType = parentTagType;
        this.moreTagsSelected = moreTagsSelected;
        this.nameSpace = nameSpace;
        this.selectedText = selectedText;
    }

    @Override
    public Type<SelectionChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SelectionChangedEventHandler handler) {
        handler.onEvent(this);
    }

    public String getParentTagType() {
        return parentTagType;
    }

    public boolean isMoreTagsSelected() {
        return moreTagsSelected;
    }

    public String getSelectedText() {
        return selectedText;
    }
}
