package org.nsesa.editor.gwt.core.client;

import com.google.inject.Provider;
import org.nsesa.editor.gwt.core.client.util.UUID;
import org.nsesa.editor.gwt.core.shared.ClientContext;

/**
 * Date: 24/06/12 19:08
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultClientContextProvider implements Provider<ClientContext> {
    /**
     * Client context for this runtime.
     */
    private ClientContext clientContext;

    /**
     * Returns the client context - guaranteed to be not null and with a session id.
     *
     * @return the client context
     */
    @Override
    public ClientContext get() {
        if (clientContext == null) {
            clientContext = new ClientContext();
            // generate a new session id for this client context
            clientContext.setSessionID(UUID.uuid(16));
        }
        return clientContext;
    }
}
