package org.nsesa.editor.gwt.editor.client.ui.header;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(EDITOR)
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
