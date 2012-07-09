package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Singleton;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        binder.bind(AmendmentDialogView.class).to(AmendmentDialogViewImpl.class).in(Singleton.class);
        binder.bind(AmendmentDialogController.class).in(Singleton.class);
    }
}
