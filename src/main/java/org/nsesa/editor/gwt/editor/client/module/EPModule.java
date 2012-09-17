package org.nsesa.editor.gwt.editor.client.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayStrategy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.ep.EPLocator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.ep.EPOverlayStrategy;

/**
 * Date: 05/07/12 22:45
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EPModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(OverlayStrategy.class).to(EPOverlayStrategy.class).in(Singleton.class);
        bind(Locator.class).to(EPLocator.class).in(Singleton.class);
    }
}
