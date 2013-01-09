package org.nsesa.editor.gwt.dialog.client.ui.handler.common;

import com.google.gwt.inject.client.AbstractGinModule;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.author.AuthorPanelModule;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.content.ContentPanelModule;

/**
 * Date: 09/01/13 11:05
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogCommonModule extends AbstractGinModule {
    @Override
    protected void configure() {
        install(new AuthorPanelModule());
        install(new ContentPanelModule());

    }
}
