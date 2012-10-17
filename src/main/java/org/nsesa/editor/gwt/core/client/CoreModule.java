package org.nsesa.editor.gwt.core.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import org.nsesa.editor.gwt.core.client.ui.error.ErrorModule;

/**
 * Date: 24/06/12 15:10
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class CoreModule extends AbstractGinModule {
    @Override
    public void configure() {
        install(new ErrorModule());
    }

    @Singleton
    @Provides
    EventBus getEventBus() {
        return new SimpleEventBus();
    }

    @Singleton
    @Provides
    Scheduler getScheduler() {
        return Scheduler.get();
    }

    @Singleton
    @Provides
    @Inject
    PlaceController getPlaceController(final EventBus eventBus) {
        return new PlaceController(eventBus);
    }

}
