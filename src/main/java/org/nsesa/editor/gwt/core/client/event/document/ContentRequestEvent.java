package org.nsesa.editor.gwt.core.client.event.document;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ContentRequestEvent extends GwtEvent<ContentRequestEventHandler> {

    public static Type<ContentRequestEventHandler> TYPE = new Type<ContentRequestEventHandler>();

    private final DocumentDTO document;

    public ContentRequestEvent(DocumentDTO document) {
        this.document = document;
    }

    @Override
    public Type<ContentRequestEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ContentRequestEventHandler handler) {
        handler.onEvent(this);
    }

    public DocumentDTO getDocument() {
        return document;
    }
}
