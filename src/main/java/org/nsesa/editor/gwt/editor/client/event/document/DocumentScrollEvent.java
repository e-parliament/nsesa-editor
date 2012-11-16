package org.nsesa.editor.gwt.editor.client.event.document;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentScrollEvent extends GwtEvent<DocumentScrollEventHandler> {

    public static final Type<DocumentScrollEventHandler> TYPE = new Type<DocumentScrollEventHandler>();

    private final DocumentController documentController;

    public DocumentScrollEvent(DocumentController documentController) {
        this.documentController = documentController;
    }

    @Override
    public Type<DocumentScrollEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DocumentScrollEventHandler handler) {
        handler.onEvent(this);
    }

    public DocumentController getDocumentController() {
        return documentController;
    }
}
