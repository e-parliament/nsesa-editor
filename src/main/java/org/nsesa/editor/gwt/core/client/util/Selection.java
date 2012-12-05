package org.nsesa.editor.gwt.core.client.util;

import java.util.Collections;

/**
 * Applies selection over given object
 * User: groza
 * Date: 28/11/12
 * Time: 10:28
 */
public interface Selection<T> {
    boolean select(T t);
    String getName();
    Selection NONE = new NoneSelection();
    Selection ALL = new AllSelection();

    static class NoneSelection implements Selection {
        @Override
        public boolean select(Object o) {
            return false;
        }

        @Override
        public String getName() {
            return "None";
        }
    }
    static class AllSelection implements Selection {
        @Override
        public boolean select(Object o) {
            return true;
        }

        @Override
        public String getName() {
            return "All";
        }
    }

}
