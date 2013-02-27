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
package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.shared.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

/**
 * Date: 21/12/12 16:03
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DialogContext {
    /**
     * The amendment to add or edit.
     */
    AmendmentContainerDTO amendment;

    /**
     * This will be set during an editing of an existing amendment
     */
    AmendmentController amendmentController;

    /**
     * The amendment action (modification, deletion, ..). Can be retrieved via the amendment in case of an edit.
     */
    AmendmentAction amendmentAction;

    /**
     * The amendable widget.
     */
    OverlayWidget overlayWidget;

    /**
     * The logical parent amendable widget (only relevant in case of new elements).
     */
    OverlayWidget parentOverlayWidget;

    /**
     * The index where to position the (new) amendable widget (only relevant in case of new elements).
     */
    int index;

    /**
     * The document controller.
     */
    private DocumentController documentController;

    public AmendmentContainerDTO getAmendment() {
        return amendment;
    }

    public void setAmendment(AmendmentContainerDTO amendment) {
        this.amendment = amendment;
    }

    public AmendmentController getAmendmentController() {
        return amendmentController;
    }

    public void setAmendmentController(AmendmentController amendmentController) {
        this.amendmentController = amendmentController;
    }

    public AmendmentAction getAmendmentAction() {
        return amendmentAction;
    }

    public void setAmendmentAction(AmendmentAction amendmentAction) {
        this.amendmentAction = amendmentAction;
    }

    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }

    public void setOverlayWidget(OverlayWidget overlayWidget) {
        this.overlayWidget = overlayWidget;
    }

    public OverlayWidget getParentOverlayWidget() {
        return parentOverlayWidget;
    }

    public void setParentOverlayWidget(OverlayWidget parentOverlayWidget) {
        this.parentOverlayWidget = parentOverlayWidget;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public DocumentController getDocumentController() {
        return documentController;
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }
}
