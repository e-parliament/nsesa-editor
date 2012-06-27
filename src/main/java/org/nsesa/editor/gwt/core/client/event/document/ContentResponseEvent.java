package org.nsesa.editor.gwt.core.client.event.document;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ContentResponseEvent extends GwtEvent<ContentResponseEventHandler> {

    public static Type<ContentResponseEventHandler> TYPE = new Type<ContentResponseEventHandler>();

    private final DocumentDTO document;
    private final String documentContent;

    public ContentResponseEvent(DocumentDTO document, String documentContent) {
        this.document = document;
        this.documentContent = documentContent;
    }

    @Override
    public Type<ContentResponseEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ContentResponseEventHandler handler) {
        handler.onEvent(this);
    }

    public String getDocumentContent() {
        return documentContent;
    }

    public DocumentDTO getDocument() {
        return document;
    }
}
