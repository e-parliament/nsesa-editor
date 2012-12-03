package org.nsesa.editor.gwt.editor.client.event.filter;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.util.Filter;

/**
 * An event initiated as a response to a filter request
 * User: groza
 * Date: 3/12/12
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public class FilterResponseEvent extends GwtEvent<FilterResponseEventHandler> {
    public static final GwtEvent.Type<FilterResponseEventHandler> TYPE = new GwtEvent.Type<FilterResponseEventHandler>();
    private int totalSize;
    private Filter filter;

    public FilterResponseEvent(int totalSize, Filter filter) {
        this.totalSize = totalSize;
        this.filter = filter;
    }

    @Override
    public GwtEvent.Type<FilterResponseEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FilterResponseEventHandler handler) {
        handler.onEvent(this);
    }

    public int getTotalSize() {
        return totalSize;
    }

    public Filter getFilter() {
        return filter;
    }
}
