package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

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
    AmendableWidget amendableWidget;

    /**
     * The logical parent amendable widget (only relevant in case of new elements).
     */
    AmendableWidget parentAmendableWidget;

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

    public AmendableWidget getAmendableWidget() {
        return amendableWidget;
    }

    public void setAmendableWidget(AmendableWidget amendableWidget) {
        this.amendableWidget = amendableWidget;
    }

    public AmendableWidget getParentAmendableWidget() {
        return parentAmendableWidget;
    }

    public void setParentAmendableWidget(AmendableWidget parentAmendableWidget) {
        this.parentAmendableWidget = parentAmendableWidget;
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
