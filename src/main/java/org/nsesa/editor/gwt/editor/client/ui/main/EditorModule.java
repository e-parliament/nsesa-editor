package org.nsesa.editor.gwt.editor.client.ui.main;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.CoreModule;
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
        binder.install(new HeaderModule());
        binder.install(new FooterModule());

        binder.bind(EditorController.class).in(Singleton.class);
        binder.bind(EditorView.class).to(EditorViewImpl.class).in(Singleton.class);
    }
}
