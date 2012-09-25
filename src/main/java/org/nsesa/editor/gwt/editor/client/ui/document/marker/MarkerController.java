package org.nsesa.editor.gwt.editor.client.ui.document.marker;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.event.ResizeEvent;
import org.nsesa.editor.gwt.core.client.event.ResizeEventHandler;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEventHandler;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.ArrayList;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class MarkerController {

    private final MarkerView view;

    private DocumentController documentController;

    private ArrayList<AmendmentController> amendmentControllers = new ArrayList<AmendmentController>();

    private final EventBus eventBus;

    private final Timer timer = new Timer() {
        public void run() {
            if (documentController != null) {
                view.clearMarkers();
                final ScrollPanel scrollPanel = documentController.getContentController().getView().getScrollPanel();
                final int documentHeight = scrollPanel.getMaximumVerticalScrollPosition();
                Log.info("Document height is: " + documentHeight);
                for (final AmendmentController amendmentController : amendmentControllers) {
                    if (amendmentController.getView().asWidget().isAttached()) {
                        final int amendmentTop = amendmentController.getView().asWidget().getAbsoluteTop() + scrollPanel.getVerticalScrollPosition();
                        Log.info("Amendment top is: " + amendmentTop);
                        double division = (double) documentHeight / (double) amendmentTop;
                        view.addMarker(division);
                    }
                }
            }
        }
    };

    @Inject
    public MarkerController(final EventBus eventBus, final MarkerView view) {
        assert view != null : "View is not set --BUG";

        this.eventBus = eventBus;
        this.view = view;

        registerListeners();
    }

    private void registerListeners() {
        eventBus.addHandler(ResizeEvent.TYPE, new ResizeEventHandler() {
            @Override
            public void onEvent(ResizeEvent event) {
                view.asWidget().setHeight((event.getHeight() - 30) + "px");
                drawAmendmentControllers();
            }
        });
        eventBus.addHandler(AmendmentContainerInjectedEvent.TYPE, new AmendmentContainerInjectedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerInjectedEvent event) {
                amendmentControllers.add(event.getAmendmentController());
                drawAmendmentControllers();
            }
        });
    }

    private void drawAmendmentControllers() {
        timer.cancel();
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
}
