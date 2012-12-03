package org.nsesa.editor.gwt.editor.client.ui.document.marker;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.event.ResizeEvent;
import org.nsesa.editor.gwt.core.client.event.ResizeEventHandler;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEventHandler;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerStatusUpdatedEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerStatusUpdatedEventHandler;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentRefreshRequestEventHandler;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.logging.Logger;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class MarkerController {

    private static final Logger LOG = Logger.getLogger("MarkerController");

    private final MarkerView view;

    private DocumentController documentController;

    private final DocumentEventBus documentEventBus;

    private final Timer timer = new Timer() {
        public void run() {
            if (documentController != null) {
                view.clearMarkers();
                final ScrollPanel scrollPanel = documentController.getContentController().getView().getScrollPanel();
                for (final AmendmentController amendmentController : documentController.getAmendmentManager().getAmendmentControllers()) {
                    if (amendmentController.getDocumentController() == documentController && amendmentController.getView().asWidget().isAttached()) {
                        final int documentHeight = scrollPanel.getMaximumVerticalScrollPosition();
                        LOG.fine("Document height is: " + documentHeight);
                        final int amendmentTop = amendmentController.getView().asWidget().getAbsoluteTop() + scrollPanel.getVerticalScrollPosition();
                        final double division = (double) documentHeight / (double) amendmentTop;
                        LOG.fine("Amendment is: " + amendmentTop + ", and division is at " + division);
                        final FocusWidget focusWidget = view.addMarker(division);
                        focusWidget.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                documentController.scrollTo(amendmentController.getView().asWidget());
                            }
                        });
                    }
                }
            }
        }
    };

    @Inject
    public MarkerController(final DocumentEventBus documentEventBus, final MarkerView view) {
        assert view != null : "View is not set --BUG";

        this.documentEventBus = documentEventBus;
        this.view = view;

        registerListeners();
    }

    private void registerListeners() {
        //Log.info("Registering marker controller with event bus " + documentEventBus);
        documentEventBus.addHandler(ResizeEvent.TYPE, new ResizeEventHandler() {
            @Override
            public void onEvent(ResizeEvent event) {
                view.asWidget().setHeight((event.getHeight() - 30) + "px");
                drawAmendmentControllers();
            }
        });

        documentEventBus.addHandler(DocumentRefreshRequestEvent.TYPE, new DocumentRefreshRequestEventHandler() {
            @Override
            public void onEvent(DocumentRefreshRequestEvent event) {
                clearMarkers();
            }
        });

        documentEventBus.addHandler(AmendmentContainerInjectedEvent.TYPE, new AmendmentContainerInjectedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerInjectedEvent event) {
                drawAmendmentControllers();
            }
        });

        documentEventBus.addHandler(AmendmentContainerStatusUpdatedEvent.TYPE, new AmendmentContainerStatusUpdatedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerStatusUpdatedEvent event) {
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
