package org.nsesa.editor.gwt.editor.client.event.document;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Widget;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentScrollToEvent extends GwtEvent<DocumentScrollToEventHandler> {

    public static final Type<DocumentScrollToEventHandler> TYPE = new Type<DocumentScrollToEventHandler>();

    private final DocumentController documentController;
    private final Widget target;

    public DocumentScrollToEvent(Widget target, DocumentController documentController) {
        this.target = target;
        this.documentController = documentController;
    }

    @Override
    public Type<DocumentScrollToEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DocumentScrollToEventHandler handler) {
        handler.onEvent(this);
    }

    public DocumentController getDocumentController() {
        return documentController;
    }

    public Widget getTarget() {
        return target;
    }
}
