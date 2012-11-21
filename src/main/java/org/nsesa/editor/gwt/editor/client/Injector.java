package org.nsesa.editor.gwt.editor.client;

import com.google.gwt.activity.shared.ActivityMapper;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.error.ErrorController;
import org.nsesa.editor.gwt.editor.client.activity.EditorPlaceFactory;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.main.EditorController;

/**
 * Date: 24/06/12 15:56
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface Injector {

    ClientFactory getClientFactory();

    ServiceFactory getServiceFactory();

    EditorController getEditorController();

    DocumentController getDocumentController();

    ErrorController getErrorController();

    AmendmentController getAmendmentController();

    ActivityMapper getActivityMapper();

    EditorPlaceFactory getPlaceFactory();
}
