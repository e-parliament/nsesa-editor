package org.nsesa.editor.gwt.core.client.mode;

import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 26/11/12 14:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ConsolidationMode implements DocumentMode<ActiveState> {

    public static final String KEY = "consolidation";

    private final DocumentController documentController;
    private final ClientFactory clientFactory;

    private ActiveState state = new ActiveState(false);

    public ConsolidationMode(DocumentController documentController, ClientFactory clientFactory) {
        this.documentController = documentController;
        this.clientFactory = clientFactory;
    }

    @Override
    public boolean apply(ActiveState state) {
        this.state = state;
        return true;
    }


    @Override
    public ActiveState getState() {
        return state;
    }
}
