/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
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
import org.nsesa.editor.gwt.core.client.diffing.DiffingManager;
import org.nsesa.editor.gwt.core.client.ref.ReferenceHandler;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineController;
import org.nsesa.editor.gwt.core.client.ui.document.info.InfoPanelController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.SourceFileController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar.ActionBarController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.content.ContentController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.header.SourceFileHeaderController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.marker.MarkerController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.undo.UndoManager;

/**
 * A GIN document injector - used to give a document controller access to its dependencies. Note that this
 * effectively makes singleton-scoped dependencies in fact document controller-scoped singletons.
 *
 * Date: 17/10/12 14:53
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GinModules({DocumentModule.class})
public interface DocumentInjector extends Ginjector {

    /**
     * Get the main document view.
     * @return the document view
     */
    DocumentView getDocumentView();

    /**
     * Get the document view CSS resource.
     * @return the document view css resource
     */
    DocumentViewCss getDocumentViewCss();

    /**
     * Get the controller for the deadline component.
     * @return the deadline controller
     */
    DeadlineController getDeadlineController();

    /**
     * Get the document event bus.
     * @return the document event bus.
     */
    DocumentEventBus getDocumentEventBus();

    /**
     * Get the source file controller.
     * @return the source file controller
     */
    SourceFileController getSourceFileController();

    /**
     * Get the header for the source file tab.
     * @return the header for the source file controller tab
     */
    SourceFileHeaderController getSourceFileHeaderController();

    /**
     * Get the marker controller.
     * @return the marker controller
     */
    MarkerController getMarkerController();

    /**
     * Get the actual content controller.
     * @return the content controller
     */
    ContentController getContentController();

    /**
     * Get the action bar controller.
     * @return the action bar controller
     */
    ActionBarController getActionBarController();

    /**
     * Get the info panel tab controller.
     * @return the info panel controller
     */
    InfoPanelController getInfoPanelController();

    /**
     * Get the local overlay widget reference handler for this document controller.
     * @return the local overlay widget reference handler
     */
    ReferenceHandler<OverlayWidget> getLocalOverlayWidgetReferenceHandler();

    /**
     * Get the diffing manager for this document controller.
     * @return the diffing manager
     */
    DiffingManager getDiffingManager();

    /**
     * Get the undo manager responsible for executing {@link org.nsesa.editor.gwt.core.client.undo.Action}s.
     * @return  the undo manager
     */
    UndoManager getUndoManager();
}
