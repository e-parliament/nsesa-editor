package org.nsesa.editor.gwt.dialog.client.ui.handler.common.meta;

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
public class MetaPanelModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
    }

    @Inject
    @Provides
    MetaPanelViewCss createStyle(final Resources resources) {
        MetaPanelViewCss style = resources.style();
        style.ensureInjected();
        return style;
    }
}
