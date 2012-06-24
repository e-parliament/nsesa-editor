package org.nsesa.editor.gwt.core.client;

import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 * Date: 24/06/12 17:57
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultEventBusProvider implements Provider<EventBus> {

    @Override
    public EventBus get() {
        return new SimpleEventBus();
    }
}
