package org.nsesa.editor.gwt.core.client.event.drafting;

import com.google.gwt.event.shared.EventHandler;

/**
 * A handler for <code>SelectionChangedEvent</code> GWT event
 * User: groza
 * Date: 17/01/13
 * Time: 10:00
 */
public interface SelectionChangedEventHandler extends EventHandler {
    void onEvent(SelectionChangedEvent event);
}
