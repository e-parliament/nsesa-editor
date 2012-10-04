package org.nsesa.editor.gwt.core.client.service.gwt;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.nsesa.editor.gwt.core.shared.ClientContext;

/**
 * Date: 24/06/12 19:58
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@RemoteServiceRelativePath("gwtService")
public interface GWTService extends RemoteService {

    /**
     * Authenticate the client context (set the principal and roles).
     */
    ClientContext authenticate(ClientContext clientContext);
}
