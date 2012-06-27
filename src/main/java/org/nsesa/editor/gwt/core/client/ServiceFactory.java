package org.nsesa.editor.gwt.core.client;

import org.nsesa.editor.gwt.core.client.service.GWTAmendmentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.GWTDocumentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.GWTServiceAsync;

/**
 * Date: 25/06/12 21:54
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface ServiceFactory {

    GWTServiceAsync getGwtService();

    GWTAmendmentServiceAsync getGwtAmendmentService();

    GWTDocumentServiceAsync getGwtDocumentService();
}
