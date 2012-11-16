package org.nsesa.editor.gwt.core.client.event;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.shared.ClientContext;

/**
 * Date: 24/06/12 18:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class BootstrapEvent extends GwtEvent<BootstrapEventHandler> {

    public static final Type<BootstrapEventHandler> TYPE = new Type<BootstrapEventHandler>();

    private final ClientContext clientContext;

    public BootstrapEvent(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @Override
    public Type<BootstrapEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(BootstrapEventHandler handler) {
        handler.onEvent(this);
    }

    public ClientContext getClientContext() {
        return clientContext;
    }
}
