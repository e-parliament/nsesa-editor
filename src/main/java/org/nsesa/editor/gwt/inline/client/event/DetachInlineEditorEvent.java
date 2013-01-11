package org.nsesa.editor.gwt.inline.client.event;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DetachInlineEditorEvent extends GwtEvent<DetachInlineEditorEventHandler> {

    public static final Type<DetachInlineEditorEventHandler> TYPE = new Type<DetachInlineEditorEventHandler>();

    private final DocumentController documentController;

    public DetachInlineEditorEvent(DocumentController documentController) {
        this.documentController = documentController;
    }

    @Override
    public Type<DetachInlineEditorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DetachInlineEditorEventHandler handler) {
        handler.onEvent(this);
    }

    public DocumentController getDocumentController() {
        return documentController;
    }
}
