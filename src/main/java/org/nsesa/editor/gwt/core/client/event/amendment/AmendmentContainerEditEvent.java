package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerEditEvent extends GwtEvent<AmendmentContainerEditEventHandler> {

    public static final Type<AmendmentContainerEditEventHandler> TYPE = new Type<AmendmentContainerEditEventHandler>();

    private final AmendableWidget amendableWidget;
    private final AmendmentContainerDTO amendment;
    private final DocumentController documentController;

    public AmendmentContainerEditEvent(AmendableWidget amendableWidget, AmendmentContainerDTO amendment, DocumentController documentController) {
        this.amendableWidget = amendableWidget;
        this.amendment = amendment;
        this.documentController = documentController;
    }

    @Override
    public Type<AmendmentContainerEditEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerEditEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentContainerDTO getAmendment() {
        return amendment;
    }

    public AmendableWidget getAmendableWidget() {
        return amendableWidget;
    }

    public DocumentController getDocumentController() {
        return documentController;
    }
}
