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
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
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

    public void setAmendableWidget(final AmendableWidget amendableWidget) {
        refreshView(amendableWidget, null);
    }

    private void registerListeners() {
        eventBus.addHandler(SelectionChangedEvent.TYPE, new SelectionChangedEventHandler() {
            @Override
            public void onEvent(SelectionChangedEvent event) {
                if (event.isMoreTagsSelected()) {
                    refreshView(overlayFactory.getAmendableWidget(event.getParentTagType()), "");
                    //eventBus.fireEvent(new CriticalErrorEvent("Too many tags selected..."));
                } else {
                    refreshView(overlayFactory.getAmendableWidget(event.getParentTagType()), event.getSelectedText());
                }
            }
        });
        eventBus.addHandler(AmendmentContainerCreateEvent.TYPE, new AmendmentContainerCreateEventHandler() {
            @Override
            public void onEvent(AmendmentContainerCreateEvent event) {
                refreshView(event.getAmendableWidget(), null);
            }
        });

    }

    public void refreshView(final AmendableWidget amendableWidget, final String selectedText) {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                draftingView.clearAll();
                LinkedHashMap<String, AmendableWidget> children = creator.getAllowedChildren(documentController, amendableWidget);
                draftingView.setDraftTitle(amendableWidget.getType());
                for(final Map.Entry<String, AmendableWidget> child : children.entrySet()) {
                    // when selected text is empty do not add any click handler just display the tags
                    IsWidget tagToAdd;
                    if (selectedText == null || selectedText.length() == 0) {
                        Label label = new Label(overlayResource.getName(child.getValue()));
                        label.setTitle(overlayResource.getDescription(child.getValue()));
                        label.getElement().addClassName("drafting-" + child.getKey());
                        tagToAdd = label;

                    } else {
                        Anchor anchor = new Anchor(overlayResource.getName(child.getValue()));
                        anchor.setTitle(overlayResource.getDescription(child.getValue()));
                        anchor.getElement().addClassName("drafting-" + child.getKey());
                        anchor.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                // throw a drafting insertion event
                                clientFactory.getEventBus().fireEvent(
                                        new DraftingInsertionEvent(child.getValue()));
                            }
                        });
                        tagToAdd = anchor;
                    }
                    draftingView.addWidget(tagToAdd);
                }
            }
        });

    }

    public DraftingView getView() {
        return draftingView;
    }
}
