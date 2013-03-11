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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ui.i18n.CoreMessages;
import org.nsesa.editor.gwt.core.shared.ClientContext;

/**
 * Default singleton implementation of the {@link ClientFactory}.
 * Date: 25/06/12 21:59
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class ClientFactoryImpl implements ClientFactory {

    private final EventBus eventBus;
    private final PlaceController placeController;
    private final Scheduler scheduler;
    private final CoreMessages coreMessages;
    private JavaScriptObject configuration;

    private ClientContext clientContext;

    @Inject
    public ClientFactoryImpl(final EventBus eventBus,
                             final PlaceController placeController,
                             final Scheduler scheduler,
                             final ClientContext clientContext,
                             final CoreMessages coreMessages) {
        this.eventBus = eventBus;
        this.placeController = placeController;
        this.scheduler = scheduler;
        this.clientContext = clientContext;
        this.coreMessages = coreMessages;
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }

    @Override
    public ClientContext getClientContext() {
        return clientContext;
    }

    @Override
    public void setClientContext(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @Override
    public CoreMessages getCoreMessages() {
        return coreMessages;
    }

    @Override
    public JavaScriptObject getConfiguration() {
        return configuration;
    }

    @Override
    public void setConfiguration(JavaScriptObject configuration) {
        this.configuration = configuration;
    }
}
