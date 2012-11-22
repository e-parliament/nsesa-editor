package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.dialog.client.ui.handler.create.AmendmentDialogCreateModule;
import org.nsesa.editor.gwt.dialog.client.ui.handler.modify.AmendmentDialogModifyModule;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogModule extends AbstractGinModule {
    @Override
    public void configure() {
        install(new AmendmentDialogCreateModule());
        install(new AmendmentDialogModifyModule());

        bind(AmendmentDialogController.class).in(Singleton.class);
    }
}
