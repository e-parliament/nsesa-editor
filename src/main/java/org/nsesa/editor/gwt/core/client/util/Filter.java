package org.nsesa.editor.gwt.core.client.util;

import java.util.Comparator;

/**
 * A class to keep the filter parameters
 * User: groza
 * Date: 3/12/12
 * Time: 13:49
 */
public class Filter<T> {

    private int start;
    private int size;
    private Comparator<T> comparator;
    private Selection<T> selection;

    public Filter(int start, int size, Comparator<T> comparator, Selection<T> selection) {
        this.start = start;
        this.size = size;
        this.selection = selection;
        this.comparator = comparator;

    }


    public int getStart() {
        return start;
    }

    public int getSize() {
        return size;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    public Selection<T> getSelection() {
        return selection;
    }
}
