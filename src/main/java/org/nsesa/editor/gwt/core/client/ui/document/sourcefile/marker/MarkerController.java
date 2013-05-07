/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.ui.document.sourcefile.marker;

import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.core.client.event.ResizeEvent;
import org.nsesa.editor.gwt.core.client.event.ResizeEventHandler;
import org.nsesa.editor.gwt.core.client.event.SwitchTabEvent;
import org.nsesa.editor.gwt.core.client.event.SwitchTabEventHandler;
import org.nsesa.editor.gwt.core.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentRefreshRequestEventHandler;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.SourceFileController;
import org.nsesa.editor.gwt.core.client.util.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Controller for the markers component. This component is displayed usually on the right side of the
 * {@link org.nsesa.editor.gwt.core.client.ui.document.sourcefile.content.ContentController}, with markers
 * displayed according to their relative position in the overlay tree.
 * <p/>
 * Date: 24/06/12 18:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class MarkerController {

    private static final Logger LOG = Logger.getLogger("MarkerController");

    /**
     * The main view of this component.
     */
    protected final MarkerView view;

    /**
     * The parent sourcefile controller.
     */
    protected SourceFileController sourceFileController;

    /**
     * Document scoped event bus.
     */
    protected final DocumentEventBus documentEventBus;

    /**
     * A list of color codes that will be used to set the color of the markers based on the
     * {@link org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO#getAmendmentContainerStatus()}.
     */
    protected static final Map<String, String> colorCodes = new HashMap<String, String>() {
        {
            put("candidate", "blue");
            put("tabled", "green");
            put("withdrawn", "brown");
        }
    };

    /**
     * A timer to actually draw the markers in the panel. Using a timer so we do not constantly mark the changes, but
     * we rather wait a few seconds, and only if nothing has changed in the mean time, we will attempt to calculate
     * and draw the markers.
     */
    private final Timer timer = new Timer() {
        public void run() {
            onTimerRun();
        }
    };
    private HandlerRegistration documentRefreshRequestEventHandlerRegistration;
    private HandlerRegistration switchTabEventHandlerRegistration;
    private HandlerRegistration resizeEventHandlerRegistration;

    @Inject
    public MarkerController(final DocumentEventBus documentEventBus, final MarkerView view) {
        assert view != null : "View is not set --BUG";

        this.documentEventBus = documentEventBus;
        this.view = view;

        registerListeners();
    }

    private void registerListeners() {
        documentRefreshRequestEventHandlerRegistration = documentEventBus.addHandler(DocumentRefreshRequestEvent.TYPE, new DocumentRefreshRequestEventHandler() {
            @Override
            public void onEvent(DocumentRefreshRequestEvent event) {
                clearMarkers();
            }
        });

        switchTabEventHandlerRegistration = documentEventBus.addHandler(SwitchTabEvent.TYPE, new SwitchTabEventHandler() {
            @Override
            public void onEvent(SwitchTabEvent event) {
                drawMarkers();
            }
        });

        // redraw the markers when the document window has resized
        resizeEventHandlerRegistration = documentEventBus.addHandler(ResizeEvent.TYPE, new ResizeEventHandler() {
            @Override
            public void onEvent(ResizeEvent event) {
                final int height = event.getHeight() - 122;
                view.asWidget().setHeight(height + "px");
                drawMarkers();
            }
        });

    }

    protected void onTimerRun() {

    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        documentRefreshRequestEventHandlerRegistration.removeHandler();
        switchTabEventHandlerRegistration.removeHandler();
        resizeEventHandlerRegistration.removeHandler();
    }

    /**
     * Start a timer to draw the amendment controller markers in one second.
     */
    public void drawMarkers() {
        timer.cancel();
        timer.schedule(1000);
    }

    /**
     * Clear any existing markers.
     */
    public void clearMarkers() {
        view.clearMarkers();
    }

    /**
     * Return the view associated with this component.
     *
     * @return the view
     */
    public MarkerView getView() {
        return view;
    }

    /**
     * Set the parent sourcefile controller and register the private listeners.
     *
     * @param sourceFileController the parent source file controller
     */
    public void setSourceFileController(SourceFileController sourceFileController) {
        this.sourceFileController = sourceFileController;
//        registerPrivateListeners();
    }

    private void registerPrivateListeners() {
    }

}
