package org.nsesa.editor.gwt.core.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import org.nsesa.editor.gwt.core.client.ui.actionbar.ActionBarModule;
import org.nsesa.editor.gwt.core.client.ui.error.ErrorModule;
import org.nsesa.editor.gwt.core.shared.ClientContext;

/**
 * Date: 24/06/12 15:10
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class CoreModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        binder.install(new ErrorModule());
        binder.install(new ActionBarModule());

        binder.bind(ClientFactory.class).to(ClientFactoryImpl.class).in(Singleton.class);
        binder.bind(ServiceFactory.class).to(ServiceFactoryImpl.class).in(Singleton.class);

        binder.bind(ClientContext.class).toProvider(DefaultClientContextProvider.class).in(Singleton.class);
    }

    @Provides
    EventBus getEventBus() {
        return new SimpleEventBus();
    }

    @Provides
    Scheduler getScheduler() {
        return Scheduler.get();
    }

    @Provides
    @Inject
    PlaceController getPlaceController(final EventBus eventBus) {
        return new PlaceController(eventBus);
    }

}
