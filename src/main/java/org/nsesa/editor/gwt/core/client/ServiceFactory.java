package org.nsesa.editor.gwt.core.client;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTAmendmentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTDocumentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTServiceAsync;

/**
 * Date: 25/06/12 21:54
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ServiceFactoryImpl.class)
public interface ServiceFactory {

    GWTServiceAsync getGwtService();

    GWTAmendmentServiceAsync getGwtAmendmentService();

    GWTDocumentServiceAsync getGwtDocumentService();
}
