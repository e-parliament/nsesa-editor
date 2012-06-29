package org.nsesa.editor.gwt.core.client.ui.actionbar;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Singleton;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ActionBarModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        binder.bind(ActionBarView.class).to(ActionBarViewImpl.class).in(Singleton.class);
    }
}
