package org.nsesa.editor.gwt.core.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ui.i18n.CoreMessages;
import org.nsesa.editor.gwt.core.shared.ClientContext;

/**
 * Date: 25/06/12 21:59
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class ClientFactoryImpl implements ClientFactory {

    private final EventBus eventBus;
    private final PlaceController placeController;
    private final Scheduler scheduler;
    private final CoreMessages coreMessages;

    private ClientContext clientContext;

    @Inject
    public ClientFactoryImpl(EventBus eventBus, PlaceController placeController, Scheduler scheduler, ClientContext clientContext, CoreMessages coreMessages) {
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
}
