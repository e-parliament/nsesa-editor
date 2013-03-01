/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.ui.drafting;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEventHandler;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingInsertionEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.SelectionChangedEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.SelectionChangedEventHandler;
import org.nsesa.editor.gwt.core.client.ui.overlay.Creator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Occurrence;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayLocalizableResource;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * The <code>DraftingController</code> class acts as a controller to refresh <code>DraftingView</code> and
 * <code>DraftingAttributesView</code> widgets.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 16/01/13 13:37
 */
public class DraftingController {

    private DraftingView draftingView;
    private DraftingAttributesView draftingAttributesView;
    private ClientFactory clientFactory;
    private Creator creator;
    private OverlayFactory overlayFactory;
    private DocumentController documentController;
    private OverlayLocalizableResource overlayResource;
    private EventBus eventBus;
    private OverlayWidget originalOverlayWidget;

    /**
     * Create a <code>DraftingController</code> with the given parameters
     * @param draftingView The <code>DraftingView</code> widget
     * @param draftingAttributesView The <code>DraftingAttributesView</code> widget
     * @param clientFactory The client factory used to reference to event bus
     * @param creator The creator
     * @param overlayFactory The overlay factory
     * @param overlayResource The overlay resource
     * @param documentController The document controller
     */
    @Inject
    public DraftingController(DraftingView draftingView,
                              DraftingAttributesView draftingAttributesView,
                              ClientFactory clientFactory,
                              Creator creator,
                              OverlayFactory overlayFactory,
                              OverlayLocalizableResource overlayResource,
                              DocumentController documentController) {
        this.draftingView = draftingView;
        this.draftingAttributesView = draftingAttributesView;
        this.clientFactory = clientFactory;
        this.creator = creator;
        this.overlayFactory = overlayFactory;
        this.overlayResource = overlayResource;
        this.documentController = documentController;
        this.eventBus = clientFactory.getEventBus();
        registerListeners();
    }

    /**
     * Set the original <code>OverlayWidget</code>
     * @param overlayWidget the original
     */
    public void setOverlayWidgetWidget(final OverlayWidget overlayWidget) {
        this.originalOverlayWidget = overlayWidget;
        refreshView(overlayWidget, null);
    }

    /**
     * Register handlers for {@link SelectionChangedEvent} and {@link AmendmentContainerCreateEvent} gwt events.
     * When those events occur <code>DraftingView</code> and <code>DraftingAttributesView</code> widgets are refreshed.
     */
    private void registerListeners() {
        eventBus.addHandler(SelectionChangedEvent.TYPE, new SelectionChangedEventHandler() {
            @Override
            public void onEvent(SelectionChangedEvent event) {
                Element el = event.getParentElement();
                if (el == null) {
                    el = originalOverlayWidget.getOverlayElement();
                }
                if (el != null) {
                    if (event.isMoreTagsSelected()) {
                        refreshView(overlayFactory.getAmendableWidget(el), "");
                    } else {
                        refreshView(overlayFactory.getAmendableWidget(el), event.getSelectedText());
                    }
                }
            }
        });
        eventBus.addHandler(AmendmentContainerCreateEvent.TYPE, new AmendmentContainerCreateEventHandler() {
            @Override
            public void onEvent(AmendmentContainerCreateEvent event) {
                refreshView(event.getOverlayWidget(), null);
            }
        });

    }

    /**
     * Refresh <code>DraftingView</code> and <code>DraftingAttributesView</code> widgets. More specifically,
     * the drafting view will be filled in with 2 lists:
     * one contains the allowed children list of the given <code>OverlayWidget</code> and
     * the other contains the mandatory children list of the given <code>OverlayWidget</code>.
     * The drafting attributes view is filled in with the available attributes of the given <code>OverlayWidget</code>.
     * @param overlayWidget The {@link OverlayWidget} processed
     * @param selectedText When it is empty the allowed children in drafting view are displayed as labels,
     *                     otherwise as anchors.
     */
    public void refreshView(final OverlayWidget overlayWidget, final String selectedText) {

        final OverlayWidget widget = (overlayWidget == null) ? originalOverlayWidget : overlayWidget;
        if (widget == null) return;

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                //refresh the attributes
                draftingAttributesView.clearAll();
                if (!widget.equals(originalOverlayWidget))
                    draftingAttributesView.setAttributes(new TreeMap<String, String>(widget.getAttributes()));

                draftingView.clearAll();
                LinkedHashMap<OverlayWidget, Occurrence> children = creator.getAllowedChildren(documentController, widget);
                draftingView.setDraftTitle(widget.getType());
                for (final Map.Entry<OverlayWidget, Occurrence> child : children.entrySet()) {
                    // when selected text is empty do not add any click handler just display the tags
                    IsWidget allowedChild, mandatoryChild = null;
                    if (selectedText == null || selectedText.length() == 0) {
                        allowedChild = createLabelFrom(child.getKey());
                        if (child.getValue().getMinOccurs() >= 1) {
                            mandatoryChild = createLabelFrom(child.getKey());
                        }
                    } else {
                        allowedChild = createAnchorFrom(child.getKey());
                        if (child.getValue().getMinOccurs() >= 1) {
                            mandatoryChild = createAnchorFrom(child.getKey());
                        }
                    }
                    draftingView.addAllowedChild(allowedChild);
                    if (mandatoryChild != null) {
                        draftingView.addMandatoryChild(allowedChild);
                    }
                }
            }
        });

    }

    /**
     * Returns {@link DraftingView}
     * @return The drafting view
     */
    public DraftingView getView() {
        return draftingView;
    }

    /**
     * Returns {@link DraftingAttributesView}
     * @return The drafting attributes view
     */
    public DraftingAttributesView getAttributesView() {
        return draftingAttributesView;
    }

    /**
     * Create a label based on the given overlay widget
     * @param overlayWidget The {@link OverlayWidget} used to create a label
     * @return The label result
     */
    private Label createLabelFrom(OverlayWidget overlayWidget) {
        Label label = new Label(overlayResource.getName(overlayWidget));
        label.setTitle(overlayResource.getDescription(overlayWidget));
        label.getElement().addClassName("drafting-" + overlayWidget.getType());
        return label;
    };

    /**
     * Create an anchor from the given overlay widget which fire {@link DraftingInsertionEvent} gwt event
     * @param overlayWidget The {@link OverlayWidget} used to create an anchor
     * @return The anchor result
     */
    private Anchor createAnchorFrom(final OverlayWidget overlayWidget) {
        Anchor anchor = new Anchor(overlayResource.getName(overlayWidget));
        anchor.setTitle(overlayResource.getDescription(overlayWidget));
        anchor.getElement().addClassName("drafting-" + overlayWidget.getType());
        anchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // throw a drafting insertion event
                clientFactory.getEventBus().fireEvent(
                        new DraftingInsertionEvent(overlayWidget));
            }
        });
        return anchor;
    }


}
