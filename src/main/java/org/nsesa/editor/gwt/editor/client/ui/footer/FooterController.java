package org.nsesa.editor.gwt.editor.client.ui.footer;

import com.google.inject.Singleton;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class FooterController {

    private final FooterView view;

    public FooterController(FooterView view) {
        this.view = view;
    }

    public FooterView getView() {
        return view;
    }
}
