package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.EventBusProvider;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineController;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayStrategy;
import org.nsesa.editor.gwt.editor.client.ui.actionbar.ActionBarController;
import org.nsesa.editor.gwt.editor.client.ui.document.content.ContentController;
import org.nsesa.editor.gwt.editor.client.ui.document.header.DocumentHeaderController;
import org.nsesa.editor.gwt.editor.client.ui.document.marker.MarkerController;

/**
 * Date: 15/10/12 23:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentControllerProvider implements Provider<DocumentController> {

    @Inject
    ClientFactory clientFactory;
    @Inject
    ServiceFactory serviceFactory;

    @Inject
    OverlayFactory overlayFactory;

    @Inject
    OverlayStrategy overlayStrategy;

    @Inject
    Locator locator;

    @Inject
    AmendmentManager amendmentManager;

    // providers

    @Inject
    ActionBarController actionBarController;

    @Inject
    DeadlineController deadlineController;

    @Inject
    ContentController contentController;

    @Inject
    MarkerController markerController;

    @Inject
    DocumentHeaderController documentHeaderController;

    @Inject
    DocumentView documentView;

    @Inject
    EventBusProvider eventBusProvider;


    @Override
    public DocumentController get() {
        return new DocumentController(clientFactory, serviceFactory, overlayFactory, overlayStrategy,
                actionBarController, locator, amendmentManager);
    }
}
