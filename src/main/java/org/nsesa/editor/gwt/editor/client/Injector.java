package org.nsesa.editor.gwt.editor.client;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.service.GWTAmendmentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.GWTDocumentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.GWTServiceAsync;
import org.nsesa.editor.gwt.core.client.ui.error.ErrorController;
import org.nsesa.editor.gwt.editor.client.ui.main.EditorController;
import org.nsesa.editor.gwt.editor.client.ui.main.EditorModule;

/**
 * Date: 24/06/12 15:56
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GinModules({EditorModule.class})
public interface Injector extends Ginjector {

    ClientFactory getClientFactory();

    EditorController getEditorController();

    ErrorController getErrorController();

    GWTServiceAsync getGWTService();

    GWTAmendmentServiceAsync getGwtAmendmentService();

    GWTDocumentServiceAsync getGwtDocumentService();
}
