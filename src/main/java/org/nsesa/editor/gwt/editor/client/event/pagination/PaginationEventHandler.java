package org.nsesa.editor.gwt.editor.client.event.pagination;

import com.google.gwt.event.shared.EventHandler;

/**
 * A handler for pagination implemented by all the components aware about pagination.
 * User: groza
 * Date: 29/11/12
 * Time: 10:43
 */
public interface PaginationEventHandler extends EventHandler {
    void onEvent(PaginationEvent event);
}
