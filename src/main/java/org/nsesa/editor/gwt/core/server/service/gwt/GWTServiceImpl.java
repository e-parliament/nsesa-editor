package org.nsesa.editor.gwt.core.server.service.gwt;

import org.joda.time.DateTime;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTService;
import org.nsesa.editor.gwt.core.shared.ClientContext;

/**
 * Date: 24/06/12 19:57
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class GWTServiceImpl extends SpringRemoteServiceServlet implements GWTService {
    @Override
    public ClientContext authenticate(final ClientContext clientContext) {
        clientContext.setPrincipal("dummy-" + new DateTime().getMillis());
        clientContext.setRoles(new String[]{"USER", "ADMIN"});
        clientContext.setDocumentIDs(clientContext.getParameter(ClientContext.DOCUMENT_ID));
        return clientContext;
    }
}
