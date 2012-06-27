package org.nsesa.editor.gwt.editor.client.ui.document.marker;

import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class MarkerController extends Composite {

    private MarkerView view;
    private DocumentController documentController;

    @Inject
    public MarkerController(final EventBus eventBus, final MarkerView view) {
        assert eventBus != null : "EventBus not set in constructor --BUG";
        assert view != null : "View is not set --BUG";

        this.view = view;
    }

    public MarkerView getView() {
        return view;
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }
}
