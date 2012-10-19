package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.dialog.client.ui.handler.bundle.AmendmentBundleController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.move.AmendmentMoveController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.table.AmendmentTableController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.widget.AmendmentWidgetModule;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {

        binder.install(new AmendmentWidgetModule());

        binder.bind(AmendmentDialogController.class).in(Singleton.class);

        binder.bind(AmendmentBundleController.class).in(Singleton.class);
        binder.bind(AmendmentMoveController.class).in(Singleton.class);
        binder.bind(AmendmentTableController.class).in(Singleton.class);
    }
}
