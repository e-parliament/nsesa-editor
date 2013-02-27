package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTAmendmentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTDiffServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTDocumentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTServiceAsync;

/**
 * Date: 26/02/13 13:31
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ServiceFactoryMock implements ServiceFactory {
    @Override
    public GWTServiceAsync getGwtService() {
        return null;
    }

    @Override
    public GWTDiffServiceAsync getGwtDiffService() {
        return null;
    }

    @Override
    public GWTAmendmentServiceAsync getGwtAmendmentService() {
        return null;
    }

    @Override
    public GWTDocumentServiceAsync getGwtDocumentService() {
        return null;
    }
}
