package org.nsesa.editor.gwt.core.client.util;

/**
 * Applies selection over given object
 * User: groza
 * Date: 28/11/12
 * Time: 10:28
 */
public interface Selection<T> {
    boolean select(T t);
    String getName();
}
