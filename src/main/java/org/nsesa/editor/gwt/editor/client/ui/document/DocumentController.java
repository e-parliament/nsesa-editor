package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.event.document.DocumentReceivedEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentReceivedEventHandler;
import org.nsesa.editor.gwt.editor.client.ui.document.marker.MarkerController;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentController {

    private DocumentView view;

    private final MarkerController markerController;

    @Inject
    public DocumentController(final EventBus eventBus, final DocumentView view, final MarkerController markerController) {
        assert eventBus != null : "EventBus not set in constructor --BUG";
        assert view != null : "View is not set --BUG";

        this.view = view;
        this.markerController = markerController;

        eventBus.addHandler(DocumentReceivedEvent.TYPE, new DocumentReceivedEventHandler() {
            @Override
            public void onEvent(DocumentReceivedEvent event) {

            }
        });
    }

    public DocumentView getView() {
        return view;
    }
}
