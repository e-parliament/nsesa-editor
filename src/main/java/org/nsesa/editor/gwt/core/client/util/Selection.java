package org.nsesa.editor.gwt.core.client.util;

/**
 * Created with IntelliJ IDEA.
 * User: groza
 * Date: 28/11/12
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public interface Selection<T> {
    boolean apply(T t);
    String getName();
}
