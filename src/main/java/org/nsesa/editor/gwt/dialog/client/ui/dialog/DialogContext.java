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
package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.shared.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

/**
 * Simple reference holder for various context related objects that will be instantiated and passed around
 * when the {@link AmendmentDialogController} is active.
 * Date: 21/12/12 16:03
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
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

    /**
     * Get the amendment container dto.
     * @return the amendment container dto
     */
    public AmendmentContainerDTO getAmendment() {
        return amendment;
    }

    /**
     * Set the amendment container dto.
     * @param amendment the amendment container dto
     */
    public void setAmendment(AmendmentContainerDTO amendment) {
        this.amendment = amendment;
    }

    /**
     * Get the amendment controller.
     * @return the amendment controller
     */
    public AmendmentController getAmendmentController() {
        return amendmentController;
    }

    /**
     * Set the amendment controller
     * @param amendmentController the amendment controller
     */
    public void setAmendmentController(AmendmentController amendmentController) {
        this.amendmentController = amendmentController;
    }

    /**
     * Get the amendment action
     * @return the amendment action
     */
    public AmendmentAction getAmendmentAction() {
        return amendmentAction;
    }

    /**
     * Set the amendment action
     * @param amendmentAction the amendment action
     */
    public void setAmendmentAction(AmendmentAction amendmentAction) {
        this.amendmentAction = amendmentAction;
    }

    /**
     * Get the overlay widget
     * @return the overlay widget
     */
    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }

    /**
     * Set the overlay widget
     * @param overlayWidget the overlay widget
     */
    public void setOverlayWidget(OverlayWidget overlayWidget) {
        this.overlayWidget = overlayWidget;
    }

    /**
     * Get the parent overlay widget.
     * @return the parent overlay widget
     */
    public OverlayWidget getParentOverlayWidget() {
        return parentOverlayWidget;
    }

    /**
     * Set the parent overlay widget
     * @param parentOverlayWidget the parent overlay widget
     */
    public void setParentOverlayWidget(OverlayWidget parentOverlayWidget) {
        this.parentOverlayWidget = parentOverlayWidget;
    }

    /**
     * Get the index for the insertion of the created widget in the parent
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set the index for the insertion of the created widget
     * @param index the index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Get the parent document controller
     * @return the document controller
     */
    public DocumentController getDocumentController() {
        return documentController;
    }

    /**
     * Set the parent document controller
     * @param documentController the document controller
     */
    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }
}
