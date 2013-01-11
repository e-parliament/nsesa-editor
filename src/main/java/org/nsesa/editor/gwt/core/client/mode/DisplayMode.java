package org.nsesa.editor.gwt.core.client.mode;

import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 26/11/12 14:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DisplayMode implements DocumentMode<DocumentDisplayState> {

    public static final String KEY = "display";

    private final DocumentController documentController;
    private final ClientFactory clientFactory;

    private DocumentDisplayState state = new DocumentDisplayState();

    public DisplayMode(DocumentController documentController, ClientFactory clientFactory) {
        this.documentController = documentController;
        this.clientFactory = clientFactory;
    }

    @Override
    public boolean apply(final DocumentDisplayState state) {
        this.state = state;
        return true;
    }

    @Override
    public DocumentDisplayState getState() {
        return state;
    }
}
