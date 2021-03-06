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
package org.nsesa.editor.gwt.core.client.ui.visualstructure;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureInsertionEvent;
import org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureSelectionChangedEvent;
import org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureSelectionChangedEventHandler;
import org.nsesa.editor.gwt.core.client.event.widget.OverlayWidgetNewEvent;
import org.nsesa.editor.gwt.core.client.event.widget.OverlayWidgetNewEventHandler;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.Creator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayLocalizableResource;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

import java.util.List;
import java.util.TreeMap;

/**
 * The <code>VisualStructureController</code> class acts as a controller to refresh <code>VisualStructureView</code> and
 * <code>VisualStructureAttributesView</code> widgets.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 16/01/13 13:37
 */
public class VisualStructureController {

    private VisualStructureView visualStructureView;
    private VisualStructureAttributesView visualStructureAttributesView;
    private ClientFactory clientFactory;
    private Creator creator;
    private OverlayFactory overlayFactory;
    private DocumentController documentController;
    private OverlayLocalizableResource overlayResource;
    private EventBus eventBus;
    private OverlayWidget originalOverlayWidget;

    private final VisualStructureView.VisualStructureCallback draftingCallback = new VisualStructureView.VisualStructureCallback() {
        @Override
        public void onChildrenSelect(OverlayWidget child) {
            eventBus.fireEvent(new VisualStructureInsertionEvent(child));
        }
    };

    private HandlerRegistration selectionChangedEventHandlerRegistration;
    private HandlerRegistration amendmentContainerCreateEventHandlerRegistration;

    /**
     * Create a <code>VisualStructureController</code> with the given parameters
     *
     * @param visualStructureView           The <code>VisualStructureView</code> widget
     * @param visualStructureAttributesView The <code>VisualStructureAttributesView</code> widget
     * @param clientFactory                 The client factory used to reference to event bus
     * @param creator                       The creator
     * @param overlayFactory                The overlay factory
     * @param overlayResource               The overlay resource
     * @param documentController            The document controller
     */
    @Inject
    public VisualStructureController(VisualStructureView visualStructureView,
                                     VisualStructureAttributesView visualStructureAttributesView,
                                     ClientFactory clientFactory,
                                     Creator creator,
                                     OverlayFactory overlayFactory,
                                     OverlayLocalizableResource overlayResource,
                                     DocumentController documentController) {
        this.visualStructureView = visualStructureView;
        this.visualStructureAttributesView = visualStructureAttributesView;
        this.clientFactory = clientFactory;
        this.creator = creator;
        this.overlayFactory = overlayFactory;
        this.overlayResource = overlayResource;
        this.documentController = documentController;
        this.eventBus = clientFactory.getEventBus();
    }

    /**
     * Set the original <code>OverlayWidget</code>
     *
     * @param overlayWidget the original
     */
    public void setOverlayWidgetWidget(final OverlayWidget overlayWidget) {
        this.originalOverlayWidget = overlayWidget;
        refreshView(overlayWidget, null);
    }

    /**
     * Register handlers for {@link org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureSelectionChangedEvent} and {@link OverlayWidgetNewEvent} gwt events.
     * It also create a drafting callback to be called when selecting children from drafting view interface
     * When those events occur <code>VisualStructureView</code> and <code>VisualStructureAttributesView</code> widgets are refreshed.
     */
    public void registerListeners() {
        selectionChangedEventHandlerRegistration = eventBus.addHandler(VisualStructureSelectionChangedEvent.TYPE, new VisualStructureSelectionChangedEventHandler() {
            @Override
            public void onEvent(VisualStructureSelectionChangedEvent eventVisualStructure) {
                Element el = eventVisualStructure.getParentElement();
                if (el == null) {
                    el = originalOverlayWidget.getOverlayElement();
                }
                if (el != null) {
                    if (eventVisualStructure.isMoreTagsSelected()) {
                        refreshView(overlayFactory.getAmendableWidget(el), "");
                    } else {
                        refreshView(overlayFactory.getAmendableWidget(el), eventVisualStructure.getSelectedText());
                    }
                }
            }
        });
        amendmentContainerCreateEventHandlerRegistration = eventBus.addHandler(OverlayWidgetNewEvent.TYPE, new OverlayWidgetNewEventHandler() {
            @Override
            public void onEvent(OverlayWidgetNewEvent event) {
                refreshView(event.getReference(), null);
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        amendmentContainerCreateEventHandlerRegistration.removeHandler();
        selectionChangedEventHandlerRegistration.removeHandler();
    }

    /**
     * Refresh <code>VisualStructureView</code> and <code>VisualStructureAttributesView</code> widgets. More specifically,
     * the drafting view will be filled in with 2 lists:
     * one contains the allowed children list of the given <code>OverlayWidget</code> and
     * the other contains the mandatory children list of the given <code>OverlayWidget</code>.
     * The drafting attributes view is filled in with the available attributes of the given <code>OverlayWidget</code>.
     *
     * @param overlayWidget The {@link OverlayWidget} processed
     * @param selectedText  When it is empty the allowed children in drafting view are displayed as labels,
     *                      otherwise as anchors.
     */
    public void refreshView(final OverlayWidget overlayWidget, final String selectedText) {

        final OverlayWidget widget = (overlayWidget == null) ? originalOverlayWidget : overlayWidget;
        if (widget == null) return;

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                //refresh the attributes
                visualStructureAttributesView.clearAll();
                if (!widget.equals(originalOverlayWidget))
                    visualStructureAttributesView.setAttributes(new TreeMap<String, String>(widget.getAttributes()));

                List<OverlayWidget> children = creator.getAllowedChildren(documentController, widget);
                visualStructureView.clearAll();
                visualStructureView.setVisualStructureTitle(widget.getType());
                visualStructureView.refreshAllowedChildren(
                        children,
                        selectedText == null || selectedText.length() == 0 ? null : draftingCallback);
            }
        });

    }

    /**
     * Returns {@link VisualStructureView}
     *
     * @return The drafting view
     */
    public VisualStructureView getView() {
        return visualStructureView;
    }

    /**
     * Returns {@link VisualStructureAttributesView}
     *
     * @return The visual structure attributes view
     */
    public VisualStructureAttributesView getAttributesView() {
        return visualStructureAttributesView;
    }


}
