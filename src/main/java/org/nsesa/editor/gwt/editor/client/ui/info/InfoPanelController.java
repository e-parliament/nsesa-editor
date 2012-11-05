package org.nsesa.editor.gwt.editor.client.ui.info;

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
public class InfoPanelController {
    private InfoPanelView view;


    public InfoPanelView getView() {
        return view;
    }
}
