package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.dialog.client.ui.handler.bundle.AmendmentBundleController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.bundle.AmendmentBundleView;
import org.nsesa.editor.gwt.dialog.client.ui.handler.bundle.AmendmentBundleViewImpl;
import org.nsesa.editor.gwt.dialog.client.ui.handler.move.AmendmentMoveController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.move.AmendmentMoveView;
import org.nsesa.editor.gwt.dialog.client.ui.handler.move.AmendmentMoveViewImpl;
import org.nsesa.editor.gwt.dialog.client.ui.handler.table.AmendmentTableController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.table.AmendmentTableView;
import org.nsesa.editor.gwt.dialog.client.ui.handler.table.AmendmentTableViewImpl;
import org.nsesa.editor.gwt.dialog.client.ui.handler.widget.AmendmentWidgetController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.widget.AmendmentWidgetView;
import org.nsesa.editor.gwt.dialog.client.ui.handler.widget.AmendmentWidgetViewImpl;

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
        binder.bind(AmendmentBundleView.class).to(AmendmentBundleViewImpl.class).in(Singleton.class);
        binder.bind(AmendmentTableView.class).to(AmendmentTableViewImpl.class).in(Singleton.class);
        binder.bind(AmendmentMoveView.class).to(AmendmentMoveViewImpl.class).in(Singleton.class);
        binder.bind(AmendmentWidgetView.class).to(AmendmentWidgetViewImpl.class).in(Singleton.class);

        binder.bind(AmendmentDialogController.class).in(Singleton.class);

        binder.bind(AmendmentBundleController.class).in(Singleton.class);
        binder.bind(AmendmentMoveController.class).in(Singleton.class);
        binder.bind(AmendmentTableController.class).in(Singleton.class);
        binder.bind(AmendmentWidgetController.class).in(Singleton.class);
    }
}
