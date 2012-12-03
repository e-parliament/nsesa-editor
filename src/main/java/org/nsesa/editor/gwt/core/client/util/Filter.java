package org.nsesa.editor.gwt.core.client.util;

/**
 * A class to keep the filter parameters
 * User: groza
 * Date: 3/12/12
 * Time: 13:49
 * To change this template use File | Settings | File Templates.
 */
public class Filter {

    private int start;
    private int size;
    private String orderBy;

    public Filter(int start, int size, String orderBy) {
        this.start = start;
        this.size = size;
        this.orderBy = orderBy;
    }


    public int getStart() {
        return start;
    }

    public int getSize() {
        return size;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setStart(int start) {
        this.start = start;
    }

}
