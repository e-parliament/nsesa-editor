package org.nsesa.editor.gwt.editor.client.ui.main;

import com.allen_sauer.gwt.log.client.Log;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.event.BootstrapEvent;
import org.nsesa.editor.gwt.core.client.event.BootstrapEventHandler;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EditorController {

    private EditorView view;

    @Inject
    public EditorController(final EventBus eventBus, final EditorViewImpl view) {
        assert eventBus != null : "EventBus not set in constructor --BUG";
        assert view != null : "View is not set --BUG";

        this.view = view;

        eventBus.addHandler(BootstrapEvent.TYPE, new BootstrapEventHandler() {
            @Override
            public void onEvent(BootstrapEvent event) {
                Log.debug("Received bootstrap event.");
            }
        });
    }

    public EditorView getView() {
        return view;
    }
}
