package org.nsesa.editor.gwt.core.server.service;

import org.nsesa.editor.gwt.core.client.service.GWTDocumentService;
import org.nsesa.editor.gwt.core.shared.ClientContext;

import java.util.HashMap;

/**
 * Date: 24/06/12 19:57
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class GWTDocumentServiceImpl extends SpringRemoteServiceServlet implements GWTDocumentService {
    @Override
    public HashMap<String, String> getMetaInformation(final ClientContext clientContext) {
        return null;
    }

    @Override
    public String getDocument(final ClientContext clientContext) {
        return null;
    }

    @Override
    public String getDocumentFragment(final ClientContext clientContext, final String elementID) {
        return null;
    }

    @Override
    public String[] getAvailableTranslations(final ClientContext clientContext) {
        return null;
    }
}
