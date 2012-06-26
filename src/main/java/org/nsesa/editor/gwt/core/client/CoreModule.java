package org.nsesa.editor.gwt.core.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
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
        binder.bind(ClientFactory.class).to(ClientFactoryImpl.class).in(Singleton.class);
        binder.bind(ClientContext.class).toProvider(DefaultClientContextProvider.class).in(Singleton.class);
        binder.bind(Scheduler.class).toProvider(DefaultSchedulerProvider.class).in(Singleton.class);
        binder.bind(EventBus.class).toProvider(DefaultEventBusProvider.class).in(Singleton.class);
        binder.bind(PlaceController.class).toProvider(DefaultPlaceControllerProvider.class).in(Singleton.class);
    }
}
