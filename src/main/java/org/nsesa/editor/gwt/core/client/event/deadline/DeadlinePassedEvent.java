package org.nsesa.editor.gwt.core.client.event.deadline;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DeadlinePassedEvent extends GwtEvent<DeadlinePassedEventHandler> {

    public static final Type<DeadlinePassedEventHandler> TYPE = new Type<DeadlinePassedEventHandler>();

    private final DocumentController documentController;

    public DeadlinePassedEvent(DocumentController documentController) {
        this.documentController = documentController;
    }

    @Override
    public Type<DeadlinePassedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DeadlinePassedEventHandler handler) {
        handler.onEvent(this);
    }

    public DocumentController getDocumentController() {
        return documentController;
    }
}
