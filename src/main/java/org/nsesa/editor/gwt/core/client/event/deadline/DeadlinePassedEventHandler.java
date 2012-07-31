package org.nsesa.editor.gwt.core.client.event.deadline;

import com.google.gwt.event.shared.EventHandler;

/**
 * Date: 24/06/12 18:15
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface DeadlinePassedEventHandler extends EventHandler {
    void onEvent(DeadlinePassedEvent event);
}
