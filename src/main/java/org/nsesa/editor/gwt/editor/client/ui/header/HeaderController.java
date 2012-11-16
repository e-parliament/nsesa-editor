package org.nsesa.editor.gwt.editor.client.ui.header;

import com.google.inject.Inject;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class HeaderController {
    private final HeaderView view;

    @Inject
    public HeaderController(HeaderView view) {
        this.view = view;
    }

    public HeaderView getView() {
        return view;
    }
}
