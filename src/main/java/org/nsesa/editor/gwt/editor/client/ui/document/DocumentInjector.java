package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import org.nsesa.editor.gwt.core.client.CoreModule;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.amendment.action.AmendmentActionPanelController;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineController;
import org.nsesa.editor.gwt.editor.client.ui.document.amendments.AmendmentsPanelController;
import org.nsesa.editor.gwt.editor.client.ui.document.header.DocumentHeaderController;
import org.nsesa.editor.gwt.editor.client.ui.document.info.InfoPanelController;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.actionbar.ActionBarController;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.content.ContentController;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.marker.MarkerController;

/**
 * Date: 17/10/12 14:53
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GinModules({DocumentModule.class, CoreModule.class})
public interface DocumentInjector extends Ginjector {

    DocumentView getDocumentView();

    DocumentViewCss getDocumentViewCss();

    DeadlineController getDeadlineController();

    ActionBarController getActionBarController();

    DocumentEventBus getDocumentEventBus();

    ContentController getContentController();

    MarkerController getMarkerController();

    AmendmentsPanelController getAmendmentsPanelController();

    AmendmentActionPanelController getAmendmentActionPanelController();

    InfoPanelController getInfoPanelController();

    DocumentHeaderController getDocumentHeaderController();

    AmendmentManager getAmendmentManager();

    AmendmentController getAmendmentController();
}
