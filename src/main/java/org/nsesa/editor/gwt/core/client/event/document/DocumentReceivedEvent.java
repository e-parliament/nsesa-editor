package org.nsesa.editor.gwt.core.client.event.document;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentReceivedEvent extends GwtEvent<DocumentReceivedEventHandler> {

    public static Type<DocumentReceivedEventHandler> TYPE = new Type<DocumentReceivedEventHandler>();

    private final String documentContent;

    public DocumentReceivedEvent(String documentContent) {
        this.documentContent = documentContent;
    }

    @Override
    public Type<DocumentReceivedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DocumentReceivedEventHandler handler) {
        handler.onEvent(this);
    }

    public String getDocumentContent() {
        return documentContent;
    }
}
