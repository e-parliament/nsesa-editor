package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineController;
import org.nsesa.editor.gwt.editor.client.ui.document.content.ContentController;
import org.nsesa.editor.gwt.editor.client.ui.document.header.DocumentHeaderController;
import org.nsesa.editor.gwt.editor.client.ui.document.marker.MarkerController;

/**
 * Date: 03/08/12 10:25
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GinModules({DocumentModule.class})
public interface DocumentInjector extends Ginjector {
    DocumentView getDocumentView();

    MarkerController getMarkerController();

    DeadlineController getDeadlineController();

    EventBus getEventBus();

    DocumentHeaderController getDocumentHeaderController();

    ContentController getContentController();
}