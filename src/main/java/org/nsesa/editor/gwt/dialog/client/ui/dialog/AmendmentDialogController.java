package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEventHandler;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerEditEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerEditEventHandler;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

/**
 * Main amendment dialog. Allows for the creation and editing of amendments. Typically consists of a two
 * column layout (with the original proposed text on the left, and a rich text editor on the right).
 * <p/>
 * Requires an {@link AmendmentContainerDTO} and {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget} to be set before it can be displayed.
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogController extends Composite implements ProvidesResize {

    /**
     * The encompassing popup panel used to display this view as a modal dialog.
     */
    private final PopupPanel popupPanel = new DecoratedPopupPanel(false, true);

    /**
     * The amendment dialog view.
     */
    private final AmendmentDialogView view;

    /**
     * The client factory, with access to the {@link com.google.web.bindery.event.shared.EventBus},
     * {@link org.nsesa.editor.gwt.core.shared.ClientContext}, ..
     */
    private final ClientFactory clientFactory;

    /**
     * The amendment to add or edit.
     */
    private AmendmentContainerDTO amendment;

    /**
     * The amendment action (modification, deletion, ..). Can be retrieved via the amendment in case of an edit.
     */
    private AmendmentAction amendmentAction;

    /**
     * The amendable widget.
     */
    private AmendableWidget amendableWidget;

    @Inject
    public AmendmentDialogController(final ClientFactory clientFactory, final AmendmentDialogView view) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.popupPanel.setWidget(view);
        this.popupPanel.setGlassEnabled(true);

        registerListeners();
    }

    private void registerListeners() {
        clientFactory.getEventBus().addHandler(AmendmentContainerCreateEvent.TYPE, new AmendmentContainerCreateEventHandler() {
            @Override
            public void onEvent(AmendmentContainerCreateEvent event) {
                amendableWidget = event.getAmendableWidget();
                amendmentAction = event.getAmendmentAction();
                show();
            }
        });
        clientFactory.getEventBus().addHandler(AmendmentContainerEditEvent.TYPE, new AmendmentContainerEditEventHandler() {
            @Override
            public void onEvent(AmendmentContainerEditEvent event) {
                amendment = event.getAmendment();
                show();
            }
        });
        this.view.getCancelButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        this.view.getSaveButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
    }

    /**
     * Resizes the dialog, centers and shows the popup.
     */
    public void show() {
        adaptSize();
        popupPanel.center();
        popupPanel.show();
    }

    /**
     * Changes the size of the dialog to become the size of the window - 100 pixels.
     */
    public void adaptSize() {
        view.asWidget().setHeight((Window.getClientHeight() - 100) + "px");
        view.asWidget().setWidth((Window.getClientWidth() - 100) + "px");
    }

    public void hide() {
        popupPanel.hide(true);
    }

    public AmendmentContainerDTO getAmendment() {
        return amendment;
    }

    public void setAmendment(AmendmentContainerDTO amendment) {
        this.amendment = amendment;
    }

    public AmendmentDialogView getView() {
        return view;
    }

    public AmendmentAction getAmendmentAction() {
        return amendmentAction;
    }

    public void setAmendmentAction(AmendmentAction amendmentAction) {
        this.amendmentAction = amendmentAction;
    }

    public AmendableWidget getAmendableWidget() {
        return amendableWidget;
    }

    public void setAmendableWidget(AmendableWidget amendableWidget) {
        this.amendableWidget = amendableWidget;
    }
}
