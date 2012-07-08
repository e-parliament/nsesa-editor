package org.nsesa.editor.gwt.editor.client.ui.main;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.CoreModule;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentModule;
import org.nsesa.editor.gwt.editor.client.module.EPModule;
import org.nsesa.editor.gwt.editor.client.ui.actionbar.ActionBarModule;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentModule;
import org.nsesa.editor.gwt.editor.client.ui.footer.FooterModule;
import org.nsesa.editor.gwt.editor.client.ui.header.HeaderModule;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EditorModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {

        binder.install(new CoreModule());
        binder.install(new DocumentModule());
        binder.install(new ActionBarModule());
        binder.install(new HeaderModule());
        binder.install(new FooterModule());
        binder.install(new AmendmentModule());

        binder.install(new EPModule());
        // binder.install(new AkomaNtoso20Module());

        binder.bind(EditorController.class).in(Singleton.class);
        binder.bind(EditorView.class).to(EditorViewImpl.class).in(Singleton.class);

        binder.bind(AmendmentManager.class).in(Singleton.class);
    }
}
