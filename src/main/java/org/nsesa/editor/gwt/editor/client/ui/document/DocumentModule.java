package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineModule;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetListener;
import org.nsesa.editor.gwt.editor.client.ui.actionbar.ActionBarModule;
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
        install(new ActionBarModule());

        bind(AmendableWidgetListener.class).to(DocumentController.class);
        //bind(DocumentController.class).toProvider(DocumentControllerProvider.class);
    }

    @Inject
    @Provides
    DocumentViewCss createStyle(final Resources resources) {
        DocumentViewCss style = resources.style();
        style.ensureInjected();
        return style;
    }

    /*@Inject
    @Provides
    DocumentControllerProvider createDocumentControllerProvider(
            final ClientFactory clientFactory,
            final ServiceFactory serviceFactory,
            final OverlayFactory overlayFactory,
            final OverlayStrategy overlayStrategy,
            final ActionBarController actionBarController,
            final Locator locator,
            final AmendmentManager amendmentManager,
            final DocumentView documentView,
            final MarkerController markerController,
            final ContentController contentController,
            final DocumentHeaderController documentHeaderController,
            final DeadlineController deadlineController,
            final EventBusProvider eventBusProvider) {

        DocumentControllerProvider provider = new DocumentControllerProvider();
        provider.actionBarController = actionBarController;
        provider.amendmentManager = amendmentManager;
        provider.clientFactory = clientFactory;
        provider.contentController = contentController;
        provider.deadlineController = deadlineController;
        provider.documentHeaderController = documentHeaderController;
        provider.documentView = documentView;
        provider.locator = locator;
        provider.markerController = markerController;
        provider.overlayFactory = overlayFactory;
        return new DocumentController(clientFactory, serviceFactory, overlayFactory, overlayStrategy,
                actionBarController, locator, amendmentManager, documentView, markerController, contentController,
                documentHeaderController, deadlineController, eventBusProvider.get());
    }*/
}
