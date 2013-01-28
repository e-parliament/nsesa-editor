package org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.actionbar;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Inject;
import com.google.inject.Provides;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ActionBarModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
    }

    @Inject
    @Provides
    ActionBarViewCss createStyle(final Resources resources) {
        ActionBarViewCss style = resources.style();
        style.ensureInjected();
        return style;
    }
}
