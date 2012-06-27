package org.nsesa.editor.gwt.editor.client.ui.document.content;

import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ContentController extends Composite {

    private ContentView view;

    @Inject
    public ContentController(final EventBus eventBus, final ContentView view) {
        assert eventBus != null : "EventBus not set in constructor --BUG";
        assert view != null : "View is not set --BUG";

        this.view = view;
    }

    public ContentView getView() {
        return view;
    }

    public void setContent(String documentContent) {
        view.setContent(documentContent);
    }
}
