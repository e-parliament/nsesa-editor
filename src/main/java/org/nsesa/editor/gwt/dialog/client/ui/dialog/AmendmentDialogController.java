package org.nsesa.editor.gwt.dialog.client.ui.dialog;

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
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandler;
import org.nsesa.editor.gwt.dialog.client.ui.handler.bundle.AmendmentBundleController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.move.AmendmentMoveController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.table.AmendmentTableController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.widget.AmendmentWidgetController;

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

    private final AmendmentBundleController amendmentBundleController;
    private final AmendmentMoveController amendmentMoveController;
    private final AmendmentTableController amendmentTableController;
    private final AmendmentWidgetController amendmentWidgetController;

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
    public AmendmentDialogController(final ClientFactory clientFactory, final AmendmentDialogView view,
                                     final AmendmentBundleController amendmentBundleController,
                                     final AmendmentTableController amendmentTableController,
                                     final AmendmentMoveController amendmentMoveController,
                                     final AmendmentWidgetController amendmentWidgetController) {
        this.clientFactory = clientFactory;
        this.view = view;

        this.amendmentBundleController = amendmentBundleController;
        this.amendmentTableController = amendmentTableController;
        this.amendmentMoveController = amendmentMoveController;
        this.amendmentWidgetController = amendmentWidgetController;

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

                AmendmentUIHandler handler = getHandler(amendableWidget);
                handle(handler);
                show();
            }
        });
        clientFactory.getEventBus().addHandler(AmendmentContainerEditEvent.TYPE, new AmendmentContainerEditEventHandler() {
            @Override
            public void onEvent(AmendmentContainerEditEvent event) {
                amendment = event.getAmendment();
                amendableWidget = event.getAmendableWidget();
                amendmentAction = amendment.getAmendmentAction();

                AmendmentUIHandler handler = getHandler(amendableWidget);
                handle(handler);
                show();
            }
        });
    }

    protected AmendmentUIHandler getHandler(final AmendableWidget amendableWidget) {
        return amendmentWidgetController;
    }

    private void handle(AmendmentUIHandler amendmentUIHandler) {
        this.view.getMainPanel().add(amendmentUIHandler.getView());

        view.getMainPanel().setCellHeight(amendmentUIHandler.getView().asWidget(), "100%");
        amendmentUIHandler.setAmendableWidget(amendableWidget);
        amendmentUIHandler.setAmendment(amendment);
    }

    private void hideAll() {
        view.getMainPanel().clear();
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
        view.getLayoutPanel().setHeight((Window.getClientHeight() - 100) + "px");
        view.getLayoutPanel().setWidth((Window.getClientWidth() - 100) + "px");
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
