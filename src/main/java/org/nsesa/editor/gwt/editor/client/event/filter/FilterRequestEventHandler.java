package org.nsesa.editor.gwt.editor.client.event.filter;

import com.google.gwt.event.shared.EventHandler;

/**
 * A handler for filter implemented by all the components aware about filter.
 * User: groza
 * Date: 29/11/12
 * Time: 10:43
 */
public interface FilterRequestEventHandler extends EventHandler {
    void onEvent(FilterRequestEvent event);
}
