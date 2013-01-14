package org.nsesa.editor.gwt.core.client.mode;

import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.NotificationEvent;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.inline.client.event.DetachInlineEditorEvent;

/**
 * Date: 26/11/12 14:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class InlineEditingMode implements DocumentMode<ActiveState> {

    public static final String KEY = "inline";

    private final DocumentController documentController;
    private final ClientFactory clientFactory;

    private ActiveState activeState = new ActiveState(false);

    public InlineEditingMode(DocumentController documentController, ClientFactory clientFactory) {
        this.documentController = documentController;
        this.clientFactory = clientFactory;
    }

    @Override
    public boolean apply(ActiveState state) {
        if (state.isActive()) {
            // don't do anything when this state is enabled
            clientFactory.getEventBus().fireEvent(new NotificationEvent("Inline editing mode is now active."));
        } else {
            clientFactory.getEventBus().fireEvent(new DetachInlineEditorEvent(documentController));
            clientFactory.getEventBus().fireEvent(new NotificationEvent("Inline editing mode is now disabled."));
        }
        this.activeState = state;
        return true;
    }

    @Override
    public ActiveState getState() {
        return activeState;
    }
}
