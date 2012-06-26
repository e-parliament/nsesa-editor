package org.nsesa.editor.gwt.core.client.event;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.shared.ClientContext;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AuthenticatedEvent extends GwtEvent<AuthenticatedEventHandler> {

    public static Type<AuthenticatedEventHandler> TYPE = new Type<AuthenticatedEventHandler>();

    private final ClientContext clientContext;

    public AuthenticatedEvent(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @Override
    public Type<AuthenticatedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AuthenticatedEventHandler handler) {
        handler.onEvent(this);
    }

    public ClientContext getClientContext() {
        return clientContext;
    }
}
