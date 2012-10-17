package org.nsesa.editor.gwt.core.client.event.deadline;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class Deadline1HourEvent extends GwtEvent<Deadline1HourEventHandler> {

    public static Type<Deadline1HourEventHandler> TYPE = new Type<Deadline1HourEventHandler>();

    private final DocumentController documentController;

    public Deadline1HourEvent(DocumentController documentController) {
        this.documentController = documentController;
    }

    @Override
    public Type<Deadline1HourEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Deadline1HourEventHandler handler) {
        handler.onEvent(this);
    }

    public DocumentController getDocumentController() {
        return documentController;
    }
}
