package org.nsesa.editor.gwt.core.client.ui.drafting;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
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
        refreshView(amendableWidget);
    }

    private void registerListeners() {
        eventBus.addHandler(SelectionChangedEvent.TYPE, new SelectionChangedEventHandler() {
            @Override
            public void onEvent(SelectionChangedEvent event) {
                if (event.isMoreTagsSelected()) {
                    //draftingView.clearAll();
                } else {
                    refreshView(overlayFactory.getAmendableWidget(event.getParentTagType()));
                }
            }
        });
        eventBus.addHandler(AmendmentContainerCreateEvent.TYPE, new AmendmentContainerCreateEventHandler() {
            @Override
            public void onEvent(AmendmentContainerCreateEvent event) {
                refreshView(event.getAmendableWidget());
            }
        });

    }

    public void refreshView(AmendableWidget amendableWidget) {
        draftingView.clearAll();
        LinkedHashMap<String, AmendableWidget> children = creator.getAllowedChildren(documentController, amendableWidget);
        draftingView.setDraftTitle(amendableWidget.getType());
        for(final Map.Entry<String, AmendableWidget> child : children.entrySet()) {
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
            draftingView.addWidget(anchor);
        }
    }

    public DraftingView getView() {
        return draftingView;
    }
}
