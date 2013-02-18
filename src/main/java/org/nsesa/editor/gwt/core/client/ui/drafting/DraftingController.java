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
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Occurrence;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayLocalizableResource;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Controls the elaboration of a amendment by displaying a list of possible children
 * that could be used when the user is creating/modifying an amendment
 * User: groza
 * Date: 16/01/13
 * Time: 13:37
 */
public class DraftingController {

    private DraftingView draftingView;
    private ClientFactory clientFactory;
    private Creator creator;
    private OverlayFactory overlayFactory;
    private DocumentController documentController;
    private OverlayLocalizableResource overlayResource;
    private EventBus eventBus;
    private String originalType;

    @Inject
    public DraftingController(DraftingView draftingView,
                              ClientFactory clientFactory,
                              Creator creator,
                              OverlayFactory overlayFactory,
                              OverlayLocalizableResource overlayResource,
                              DocumentController documentController) {
        this.draftingView = draftingView;
        this.clientFactory = clientFactory;
        this.creator = creator;
        this.overlayFactory = overlayFactory;
        this.overlayResource = overlayResource;
        this.documentController = documentController;
        this.eventBus = clientFactory.getEventBus();
        registerListeners();
    }

    public void setAmendableWidget(final OverlayWidget overlayWidget) {
        originalType = overlayWidget.getType();
        refreshView(overlayWidget, null);
    }

    private void registerListeners() {
        eventBus.addHandler(SelectionChangedEvent.TYPE, new SelectionChangedEventHandler() {
            @Override
            public void onEvent(SelectionChangedEvent event) {
                String type = originalType;
                if (event.getParentTagType() != null && !"".equals(event.getParentTagType())) {
                    type = event.getParentTagType();
                }
                if (event.isMoreTagsSelected()) {
                    refreshView(overlayFactory.getAmendableWidget(type), "");
                    //eventBus.fireEvent(new CriticalErrorEvent("Too many tags selected..."));
                } else {
                    refreshView(overlayFactory.getAmendableWidget(type), event.getSelectedText());
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

    public void refreshView(final OverlayWidget overlayWidget, final String selectedText) {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                draftingView.clearAll();
                LinkedHashMap<OverlayWidget, Occurrence> children = creator.getAllowedChildren(documentController, overlayWidget);
                draftingView.setDraftTitle(overlayWidget.getType());
                for (final Map.Entry<OverlayWidget, Occurrence> child : children.entrySet()) {
                    // when selected text is empty do not add any click handler just display the tags
                    IsWidget allowedChild, mandatoryChild = null;
                    boolean isMandatory;
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

    public DraftingView getView() {
        return draftingView;
    }

    //create a label from amendable widget
    private Label createLabelFrom(OverlayWidget overlayWidget) {
        Label label = new Label(overlayResource.getName(overlayWidget));
        label.setTitle(overlayResource.getDescription(overlayWidget));
        label.getElement().addClassName("drafting-" + overlayWidget.getType());
        return label;
    };

    //create an anchor from amendable widget
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
