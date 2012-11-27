package org.nsesa.editor.gwt.editor.client.event.document;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.mode.DocumentMode;
import org.nsesa.editor.gwt.core.client.mode.DocumentState;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentModeChangeEvent<D extends DocumentMode> extends GwtEvent<DocumentModeChangeEventHandler> {

    public static final Type<DocumentModeChangeEventHandler> TYPE = new Type<DocumentModeChangeEventHandler>();

    private final DocumentController documentController;
    private final D documentMode;
    private final DocumentState state;

    public DocumentModeChangeEvent(final DocumentController documentController,
                                   final D documentMode,
                                   final DocumentState state) {
        this.documentController = documentController;
        this.documentMode = documentMode;
        this.state = state;
    }

    @Override
    public Type<DocumentModeChangeEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DocumentModeChangeEventHandler handler) {
        handler.onEvent(this);
    }

    public DocumentController getDocumentController() {
        return documentController;
    }

    public D getDocumentMode() {
        return documentMode;
    }

    public DocumentState getState() {
        return state;
    }
}
