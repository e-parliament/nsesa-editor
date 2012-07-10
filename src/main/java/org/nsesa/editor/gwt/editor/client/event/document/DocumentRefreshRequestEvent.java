package org.nsesa.editor.gwt.editor.client.event.document;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentRefreshRequestEvent extends GwtEvent<DocumentRefreshRequestEventHandler> {

    public static Type<DocumentRefreshRequestEventHandler> TYPE = new Type<DocumentRefreshRequestEventHandler>();

    private final DocumentController documentController;

    public DocumentRefreshRequestEvent(DocumentController documentController) {
        this.documentController = documentController;
    }

    @Override
    public Type<DocumentRefreshRequestEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DocumentRefreshRequestEventHandler handler) {
        handler.onEvent(this);
    }

    public DocumentController getDocumentController() {
        return documentController;
    }
}