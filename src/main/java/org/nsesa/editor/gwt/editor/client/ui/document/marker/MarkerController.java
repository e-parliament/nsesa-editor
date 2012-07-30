package org.nsesa.editor.gwt.editor.client.ui.document.marker;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.ResizeEvent;
import org.nsesa.editor.gwt.core.client.event.ResizeEventHandler;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEventHandler;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class MarkerController extends Composite {

    private final MarkerView view;

    private DocumentController documentController;

    private final ClientFactory clientFactory;

    private EventBus documentEventBus;

    private Timer timer;

    @Inject
    public MarkerController(final ClientFactory clientFactory, final MarkerView view) {
        assert view != null : "View is not set --BUG";

        this.clientFactory = clientFactory;
        this.view = view;

        registerGlobalListeners();
    }

    private void registerGlobalListeners() {
        clientFactory.getEventBus().addHandler(ResizeEvent.TYPE, new ResizeEventHandler() {
            @Override
            public void onEvent(ResizeEvent event) {
                view.asWidget().setHeight((event.getHeight() - 30) + "px");
            }
        });
    }

    private void registerPrivateListeners() {
        assert documentEventBus != null;
        documentEventBus.addHandler(AmendmentContainerInjectedEvent.TYPE, new AmendmentContainerInjectedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerInjectedEvent event) {
                drawAmendmentController(event.getAmendmentController());
            }
        });
    }

    private void drawAmendmentController(final AmendmentController amendmentController) {
        timer = new Timer() {
            public void run() {
                if (documentController != null) {
                    final ScrollPanel scrollPanel = documentController.getContentController().getView().getScrollPanel();
                    final int documentHeight = scrollPanel.getMaximumVerticalScrollPosition();
                    Log.info("Document height is: " + documentHeight);
                    final int amendmentTop = amendmentController.getView().asWidget().getAbsoluteTop() + scrollPanel.getVerticalScrollPosition();
                    Log.info("Amendment top is: " + amendmentTop);
                    double division = (double) documentHeight / (double) amendmentTop;
                    view.addMarker(division);
                }
            }
        };
        timer.schedule(1000);
    }


    private void clearMarkers() {
        view.clearMarkers();
    }

    public MarkerView getView() {
        return view;
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }

    public void setDocumentEventBus(EventBus documentEventBus) {
        this.documentEventBus = documentEventBus;
        registerPrivateListeners();
    }
}
