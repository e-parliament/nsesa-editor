package org.nsesa.editor.gwt.editor.client;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.ui.error.ErrorController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
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

    ServiceFactory getServiceFactory();

    EditorController getEditorController();

    DocumentController getDocumentController();

    ErrorController getErrorController();
}
