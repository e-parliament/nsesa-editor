package org.nsesa.editor.gwt.editor.client.ui.main;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Singleton;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EditorModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        binder.bind(EditorController.class).in(Singleton.class);
        binder.bind(EditorViewImpl.class).in(Singleton.class);
        binder.bind(EditorView.class).to(EditorViewImpl.class).in(Singleton.class);
    }
}
