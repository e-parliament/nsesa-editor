package org.nsesa.editor.gwt.editor.client.ui.amendments;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import org.nsesa.editor.gwt.editor.client.ui.amendments.header.AmendmentsHeaderModule;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentsPanelModule extends AbstractGinModule {
    @Override
    protected void configure() {
        install(new AmendmentsHeaderModule());
    }
}
