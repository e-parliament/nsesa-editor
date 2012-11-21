package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.dialog.client.ui.handler.bundle.AmendmentDialogBundleController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.modify.AmendmentDialogModifyModule;
import org.nsesa.editor.gwt.dialog.client.ui.handler.move.AmendmentDialogMoveController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.table.AmendmentDialogTableController;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {

        binder.install(new AmendmentDialogModifyModule());

        binder.bind(AmendmentDialogController.class).in(Singleton.class);

        binder.bind(AmendmentDialogBundleController.class).in(Singleton.class);
        binder.bind(AmendmentDialogMoveController.class).in(Singleton.class);
        binder.bind(AmendmentDialogTableController.class).in(Singleton.class);
    }
}
