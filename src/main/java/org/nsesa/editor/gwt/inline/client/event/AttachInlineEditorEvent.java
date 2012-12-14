package org.nsesa.editor.gwt.inline.client.event;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AttachInlineEditorEvent extends GwtEvent<AttachInlineEditorEventHandler> {

    public static final Type<AttachInlineEditorEventHandler> TYPE = new Type<AttachInlineEditorEventHandler>();

    private final AmendableWidget amendableWidget;
    private final DocumentController documentController;

    public AttachInlineEditorEvent(AmendableWidget amendableWidget, DocumentController documentController) {
        this.amendableWidget = amendableWidget;
        this.documentController = documentController;
    }

    @Override
    public Type<AttachInlineEditorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AttachInlineEditorEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendableWidget getAmendableWidget() {
        return amendableWidget;
    }

    public DocumentController getDocumentController() {
        return documentController;
    }
}
