/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.ui.document;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import org.nsesa.editor.gwt.core.client.CoreModule;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.amendment.action.AmendmentActionPanelController;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineController;
import org.nsesa.editor.gwt.core.client.ui.document.amendments.AmendmentsPanelController;
import org.nsesa.editor.gwt.core.client.ui.document.header.DocumentHeaderController;
import org.nsesa.editor.gwt.core.client.ui.document.info.InfoPanelController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.SourceFileController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar.ActionBarController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.content.ContentController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.header.SourceFileHeaderController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.marker.MarkerController;

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

    DocumentEventBus getDocumentEventBus();

    AmendmentsPanelController getAmendmentsPanelController();

    SourceFileController getSourceFileController();

    SourceFileHeaderController getSourceFileHeaderController();

    MarkerController getMarkerController();

    ContentController getContentController();

    ActionBarController getActionBarController();

    AmendmentActionPanelController getAmendmentActionPanelController();

    InfoPanelController getInfoPanelController();

    DocumentHeaderController getDocumentHeaderController();

    AmendmentManager getAmendmentManager();

    AmendmentController getAmendmentController();
}
