package org.nsesa.editor.gwt.editor.client.event.pagination;

import com.google.gwt.event.shared.GwtEvent;

/**
 * An event triggered by the pagination component
 * User: groza
 * Date: 29/11/12
 * Time: 10:42
 */
public class PaginationEvent extends GwtEvent<PaginationEventHandler> {
    public static final Type<PaginationEventHandler> TYPE = new Type<PaginationEventHandler>();

    private int page;
    public PaginationEvent(int page) {
        this.page = page;
    }

    @Override
    public Type<PaginationEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PaginationEventHandler handler) {
        handler.onEvent(this);
    }

    public int getPage() {
        return page;
    }
}
