package org.nsesa.editor.gwt.editor.client.ui.amendment;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Singleton;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        binder.bind(AmendmentView.class).to(AmendmentViewImpl.class).in(Singleton.class);
    }
}
