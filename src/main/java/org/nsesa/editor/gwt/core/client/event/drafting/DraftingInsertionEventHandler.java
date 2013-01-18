package org.nsesa.editor.gwt.core.client.event.drafting;

import com.google.gwt.event.shared.EventHandler;

/**
 * A handler invoked when a drafting insertion event occur
 * User: groza
 * Date: 17/01/13
 * Time: 15:25
 */
public interface DraftingInsertionEventHandler extends EventHandler {
    void onEvent(DraftingInsertionEvent event);
}
