package org.nsesa.editor.gwt.core.client.ui.error;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Singleton;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ErrorModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        binder.bind(ErrorView.class).to(ErrorViewImpl.class).in(Singleton.class);
    }
}
