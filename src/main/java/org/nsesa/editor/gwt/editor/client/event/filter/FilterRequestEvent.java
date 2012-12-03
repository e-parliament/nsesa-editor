package org.nsesa.editor.gwt.editor.client.event.filter;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.util.Filter;

/**
 * An event triggered by the filter component
 * User: groza
 * Date: 29/11/12
 * Time: 10:42
 */
public class FilterRequestEvent extends GwtEvent<FilterRequestEventHandler> {
    public static final Type<FilterRequestEventHandler> TYPE = new Type<FilterRequestEventHandler>();
    private Filter filter;

    public FilterRequestEvent(Filter filter) {
        this.filter = filter;
    }

    @Override
    public Type<FilterRequestEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FilterRequestEventHandler handler) {
        handler.onEvent(this);
    }

    public Filter getFilter() {
        return filter;
    }

}
