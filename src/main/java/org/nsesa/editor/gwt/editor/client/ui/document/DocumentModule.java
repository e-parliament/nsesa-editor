package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Inject;
import com.google.inject.Provides;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetListener;
import org.nsesa.editor.gwt.editor.client.ui.document.content.ContentModule;
import org.nsesa.editor.gwt.editor.client.ui.document.header.DocumentHeaderModule;
import org.nsesa.editor.gwt.editor.client.ui.document.marker.MarkerModule;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        binder.install(new ContentModule());
        binder.install(new MarkerModule());
        binder.install(new DocumentHeaderModule());
        binder.bind(DocumentView.class).to(DocumentViewImpl.class);
        binder.bind(AmendableWidgetListener.class).to(DocumentController.class);
    }

    @Inject
    @Provides
    DocumentViewCss createStyle(final Resources resources) {
        DocumentViewCss style = resources.style();
        style.ensureInjected();
        return style;
    }
}
