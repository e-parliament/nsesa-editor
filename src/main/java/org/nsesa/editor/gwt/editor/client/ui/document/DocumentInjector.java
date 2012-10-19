package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import org.nsesa.editor.gwt.core.client.CoreModule;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineController;
import org.nsesa.editor.gwt.editor.client.ui.document.content.ContentController;
import org.nsesa.editor.gwt.editor.client.ui.document.header.DocumentHeaderController;
import org.nsesa.editor.gwt.editor.client.ui.document.marker.MarkerController;

/**
 * Date: 17/10/12 14:53
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GinModules({DocumentModule.class, CoreModule.class})
public interface DocumentInjector extends Ginjector {

    DocumentView getDocumentView();

    DeadlineController getDeadlineController();

    DocumentEventBus getDocumentEventBus();

    ContentController getContentController();

    MarkerController getMarkerController();

    DocumentHeaderController getDocumentHeaderController();
}
