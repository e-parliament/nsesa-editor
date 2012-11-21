package org.nsesa.editor.gwt.dialog.client.ui.handler.modify;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.dialog.client.ui.handler.modify.author.AuthorPanelModule;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogModifyModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        binder.install(new AuthorPanelModule());
        binder.bind(AmendmentDialogModifyController.class).in(Singleton.class);
    }
}
