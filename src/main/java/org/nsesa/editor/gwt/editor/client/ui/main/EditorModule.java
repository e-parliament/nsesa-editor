package org.nsesa.editor.gwt.editor.client.ui.main;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.CoreModule;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentModule;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.AmendmentDialogModule;
import org.nsesa.editor.gwt.editor.client.module.EPModule;
import org.nsesa.editor.gwt.editor.client.ui.actionbar.ActionBarModule;
import org.nsesa.editor.gwt.editor.client.ui.footer.FooterModule;
import org.nsesa.editor.gwt.editor.client.ui.header.HeaderModule;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EditorModule extends AbstractGinModule {
    @Override
    protected void configure() {
        install(new CoreModule());
        install(new ActionBarModule());
        install(new HeaderModule());
        install(new FooterModule());
        install(new AmendmentModule());
        install(new AmendmentDialogModule());

        install(new EPModule());

        bind(AmendmentManager.class).in(Singleton.class);
    }
}
