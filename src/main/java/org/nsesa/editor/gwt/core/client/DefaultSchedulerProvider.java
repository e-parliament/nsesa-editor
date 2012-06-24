package org.nsesa.editor.gwt.core.client;

import com.google.gwt.core.client.Scheduler;
import com.google.inject.Provider;

/**
 * Date: 24/06/12 16:36
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultSchedulerProvider implements Provider<Scheduler> {
    @Override
    public Scheduler get() {
        return Scheduler.get();
    }
}
