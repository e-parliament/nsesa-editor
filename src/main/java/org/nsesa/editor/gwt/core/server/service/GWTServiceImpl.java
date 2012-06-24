package org.nsesa.editor.gwt.core.server.service;

import org.joda.time.DateTime;
import org.nsesa.editor.gwt.core.client.service.GWTService;

/**
 * Date: 24/06/12 19:57
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class GWTServiceImpl extends SpringRemoteServiceServlet implements GWTService {
    @Override
    public String getPrincipal() {
        return "dummy-" + new DateTime().getMillis();
    }
}
