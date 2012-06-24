package org.nsesa.editor.gwt.core.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Date: 24/06/12 19:58
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@RemoteServiceRelativePath("gwtService")
public interface GWTService extends RemoteService {

    /**
     * Retrieves the principal of the client.
     *
     * @return the username of the principal.
     */
    String getPrincipal();
}
