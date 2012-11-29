package org.nsesa.editor.gwt.editor.client.event.amendments;

import com.google.gwt.event.shared.EventHandler;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentModeChangeEvent;

/**
 * Created with IntelliJ IDEA.
 * User: groza
 * Date: 29/11/12
 * Time: 10:43
 * To change this template use File | Settings | File Templates.
 */
public interface AmendmentsSelectionEventHandler extends EventHandler {
    void onEvent(AmendmentsSelectionEvent event);
}
