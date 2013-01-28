package org.nsesa.editor.gwt.editor.client.ui.document.amendments;

import com.google.gwt.inject.client.AbstractGinModule;
import org.nsesa.editor.gwt.editor.client.ui.document.amendments.header.AmendmentsHeaderModule;

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
