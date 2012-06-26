package org.nsesa.editor.gwt.core.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ui.i18n.CoreMessages;
import org.nsesa.editor.gwt.core.shared.ClientContext;

/**
 * Date: 25/06/12 21:54
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface ClientFactory {

    EventBus getEventBus();

    PlaceController getPlaceController();

    Scheduler getScheduler();

    ClientContext getClientContext();

    void setClientContext(ClientContext clientContext);

    CoreMessages getCoreMessages();
}
