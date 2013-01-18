package org.nsesa.editor.gwt.core.client.ui.drafting;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingInsertionEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.SelectionChangedEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.SelectionChangedEventHandler;
import org.nsesa.editor.gwt.core.client.ui.overlay.Creator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
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
    private EventBus eventBus;


    @Inject
    public DraftingController(DraftingView draftingView,
                              ClientFactory clientFactory,
                              Creator creator,
                              OverlayFactory overlayFactory,
                              DocumentController documentController) {
        this.draftingView = draftingView;
        this.clientFactory = clientFactory;
        this.creator = creator;
        this.overlayFactory = overlayFactory;
        this.documentController = documentController;
        this.eventBus = clientFactory.getEventBus();
        registerListeners();
    }

    private void registerListeners() {
        eventBus.addHandler(SelectionChangedEvent.TYPE, new SelectionChangedEventHandler() {
            @Override
            public void onEvent(SelectionChangedEvent event) {
                if (event.isMoreTagsSelected()) {
                    draftingView.clearAll();
                } else {
                    refreshView(overlayFactory.getAmendableWidget(event.getParentTagType()), event.getSelectedText());
                }
            }
        });
    }

    public void refreshView(AmendableWidget amendableWidget, String text) {
        draftingView.clearAll();
        LinkedHashMap<String, AmendableWidget> children = creator.getAllowedChildren(documentController, amendableWidget);
        for(final Map.Entry<String, AmendableWidget> child : children.entrySet()) {
            Anchor anchor = new Anchor(child.getKey());
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

    public IsWidget getView() {
        return draftingView;
    }
}
