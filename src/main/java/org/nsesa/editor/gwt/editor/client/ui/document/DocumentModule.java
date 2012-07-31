package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineModule;
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
public class DocumentModule extends AbstractGinModule {

    @Override
    protected void configure() {
        install(new ContentModule());
        install(new MarkerModule());
        install(new DocumentHeaderModule());
        install(new DeadlineModule());
        bind(DocumentView.class).to(DocumentViewImpl.class);
        bind(AmendableWidgetListener.class).to(DocumentController.class);
        bind(EventBus.class).annotatedWith(Names.named("documentEventBus")).to(SimpleEventBus.class);
    }

    @Inject
    @Provides
    DocumentViewCss createStyle(final Resources resources) {
        DocumentViewCss style = resources.style();
        style.ensureInjected();
        return style;
    }
}
