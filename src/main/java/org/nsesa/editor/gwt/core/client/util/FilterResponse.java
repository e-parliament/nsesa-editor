package org.nsesa.editor.gwt.core.client.util;

import java.util.List;

/**
 * A response occurred after a filter operation
 * User: groza
 * Date: 5/12/12
 * Time: 11:41
 */
public class FilterResponse<T> {
    private final int totalSize;
    private final Filter<T> filter;
    private final List<T> result;

    public FilterResponse(Filter<T> filter, int totalSize, List<T> result) {
        this.totalSize = totalSize;
        this.filter = filter;
        this.result = result;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public Filter<T> getFilter() {
        return filter;
    }

    public List<T> getResult() {
        return result;
    }
}
