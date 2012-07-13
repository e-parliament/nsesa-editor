package org.nsesa.editor.gwt.dialog.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Date: 24/06/12 18:15
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface CloseDialogEventHandler extends EventHandler {
    void onEvent(CloseDialogEvent event);
}
