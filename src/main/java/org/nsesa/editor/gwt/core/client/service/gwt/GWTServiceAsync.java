package org.nsesa.editor.gwt.core.client.service.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.nsesa.editor.gwt.core.shared.ClientContext;

public interface GWTServiceAsync {

    /**
     * Authenticate the client context (set the principal and roles).
     */
    void authenticate(ClientContext clientContext, AsyncCallback<ClientContext> async);
}
