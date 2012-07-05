package org.nsesa.editor.gwt.editor.client.module;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.overlay.OverlayStrategy;
import org.nsesa.editor.gwt.core.client.ui.overlay.ep.EPOverlayStrategy;

/**
 * Date: 05/07/12 22:45
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EPModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        binder.bind(OverlayStrategy.class).to(EPOverlayStrategy.class).in(Singleton.class);
    }
}
