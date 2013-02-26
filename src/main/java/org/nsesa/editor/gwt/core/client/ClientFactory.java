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
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.ImplementedBy;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ui.i18n.CoreMessages;
import org.nsesa.editor.gwt.core.shared.ClientContext;

/**
 * A client factory to give access to local core dependencies.
 * Date: 25/06/12 21:54
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ClientFactoryImpl.class)
public interface ClientFactory {

    /**
     * Returns the global event bus.
     * @return the global event bus.
     */
    EventBus getEventBus();

    /**
     * The (unused) place controller to control activities and history state management.
     * @return the place controller.
     */
    PlaceController getPlaceController();

    /**
     * The default scheduler.
     * @return the default scheduler.
     */
    Scheduler getScheduler();

    /**
     * The client context. Gives access to details about the current client and the current environment.
     * @return the client context.
     */
    ClientContext getClientContext();

    /**
     * Sets the client context on the client factory as soon as it has been authenticated.
     * @param clientContext the (authenticated) client context.
     */
    void setClientContext(ClientContext clientContext);

    /**
     * Returns the core messages.
     * @return the core messages.
     */
    CoreMessages getCoreMessages();
}
