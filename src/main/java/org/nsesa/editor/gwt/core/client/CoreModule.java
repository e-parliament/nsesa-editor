/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import org.nsesa.editor.gwt.core.client.ui.confirmation.ConfirmationModule;
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
        install(new ConfirmationModule());
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
