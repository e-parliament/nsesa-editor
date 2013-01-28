package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineModule;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetUIListener;
import org.nsesa.editor.gwt.editor.client.ui.document.header.DocumentHeaderModule;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.SourceFileController;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.actionbar.ActionBarModule;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.content.ContentModule;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.marker.MarkerModule;
import org.nsesa.editor.gwt.inline.client.ui.inline.InlineEditorModule;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentModule extends AbstractGinModule {

    @Override
    protected void configure() {
        install(new ContentModule());
        install(new MarkerModule());
        install(new DocumentHeaderModule());
        install(new DeadlineModule());
        install(new ActionBarModule());
        install(new InlineEditorModule());

        bind(AmendableWidgetUIListener.class).to(SourceFileController.class);
    }

    @Inject
    @Provides
    DocumentViewCss createStyle(final Resources resources) {
        DocumentViewCss style = resources.style();
        style.ensureInjected();
        return style;
    }
}
