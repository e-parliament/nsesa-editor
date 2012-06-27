package org.nsesa.editor.gwt.core.client.event.document;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentRefreshRequestEvent extends GwtEvent<DocumentRefreshRequestEventHandler> {

    public static Type<DocumentRefreshRequestEventHandler> TYPE = new Type<DocumentRefreshRequestEventHandler>();

    private final String documentID;

    public DocumentRefreshRequestEvent(String documentID) {
        this.documentID = documentID;
    }

    @Override
    public Type<DocumentRefreshRequestEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DocumentRefreshRequestEventHandler handler) {
        handler.onEvent(this);
    }

    public String getDocumentID() {
        return documentID;
    }
}
