package org.nsesa.editor.gwt.editor.client.ui.main;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.CoreModule;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentModule;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.AmendmentDialogModule;
import org.nsesa.editor.gwt.dialog.client.ui.rte.tinymce.TinyMCEModule;
import org.nsesa.editor.gwt.editor.client.ui.amendments.AmendmentsPanelModule;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentModule;
import org.nsesa.editor.gwt.editor.client.ui.footer.FooterModule;
import org.nsesa.editor.gwt.editor.client.ui.header.HeaderModule;
import org.nsesa.editor.gwt.editor.client.ui.info.InfoPanelModule;

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
        install(new DocumentModule());
        install(new HeaderModule());
        install(new FooterModule());
        install(new AmendmentModule());
        install(new AmendmentsPanelModule());
        install(new InfoPanelModule());
        install(new AmendmentDialogModule());
        install(new TinyMCEModule());

        bind(AmendmentManager.class).in(Singleton.class);
    }
}
