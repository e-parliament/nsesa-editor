package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerCreateEvent extends GwtEvent<AmendmentContainerCreateEventHandler> {

    public static final Type<AmendmentContainerCreateEventHandler> TYPE = new Type<AmendmentContainerCreateEventHandler>();

    private final AmendableWidget amendableWidget;
    private final AmendmentAction amendmentAction;
    private final DocumentController documentController;

    public AmendmentContainerCreateEvent(AmendableWidget amendableWidget, AmendmentAction amendmentAction, DocumentController documentController) {
        this.amendableWidget = amendableWidget;
        this.amendmentAction = amendmentAction;
        this.documentController = documentController;
    }

    @Override
    public Type<AmendmentContainerCreateEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerCreateEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentAction getAmendmentAction() {
        return amendmentAction;
    }

    public AmendableWidget getAmendableWidget() {
        return amendableWidget;
    }

    public DocumentController getDocumentController() {
        return documentController;
    }
}
