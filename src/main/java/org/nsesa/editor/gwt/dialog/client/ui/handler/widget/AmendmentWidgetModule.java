package org.nsesa.editor.gwt.dialog.client.ui.handler.widget;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.dialog.client.ui.tab.author.AuthorPanelModule;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentWidgetModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        binder.install(new AuthorPanelModule());
        binder.bind(AmendmentWidgetController.class).in(Singleton.class);
    }
}
