package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEventHandler;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerEditEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerEditEventHandler;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.util.UUID;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.dialog.client.event.CloseDialogEvent;
import org.nsesa.editor.gwt.dialog.client.event.CloseDialogEventHandler;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandler;
import org.nsesa.editor.gwt.dialog.client.ui.handler.bundle.AmendmentDialogBundleController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.create.AmendmentDialogCreateController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.modify.AmendmentDialogModifyController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.move.AmendmentDialogMoveController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.table.AmendmentDialogTableController;

/**
 * Main amendment dialog. Allows for the creation and editing of amendments. Typically consists of a two
 * column layout (with the original proposed text on the left, and a rich text editor on the right).
 * <p/>
 * Requires an {@link AmendmentContainerDTO}, a {@link AmendmentAction} and
 * {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget} to be set before it can be displayed.
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

    private final AmendmentDialogCreateController amendmentDialogCreateController;
    private final AmendmentDialogBundleController amendmentDialogBundleController;
    private final AmendmentDialogMoveController amendmentDialogMoveController;
    private final AmendmentDialogTableController amendmentDialogTableController;
    private final AmendmentDialogModifyController amendmentDialogModifyController;

    /**
     * The client factory, with access to the {@link com.google.web.bindery.event.shared.EventBus},
     * {@link org.nsesa.editor.gwt.core.shared.ClientContext}, ..
     */
    private final ClientFactory clientFactory;

    private final OverlayFactory overlayFactory;

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
                                     final AmendmentDialogCreateController amendmentDialogCreateController,
                                     final AmendmentDialogBundleController amendmentDialogBundleController,
                                     final AmendmentDialogTableController amendmentDialogTableController,
                                     final AmendmentDialogMoveController amendmentDialogMoveController,
                                     final AmendmentDialogModifyController amendmentDialogModifyController,
                                     final OverlayFactory overlayFactory) {
        this.clientFactory = clientFactory;
        this.overlayFactory = overlayFactory;
        this.view = view;

        this.amendmentDialogCreateController = amendmentDialogCreateController;
        this.amendmentDialogBundleController = amendmentDialogBundleController;
        this.amendmentDialogTableController = amendmentDialogTableController;
        this.amendmentDialogMoveController = amendmentDialogMoveController;
        this.amendmentDialogModifyController = amendmentDialogModifyController;

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
                amendment = createAmendment();

                handle();
                show();
            }
        });
        clientFactory.getEventBus().addHandler(AmendmentContainerEditEvent.TYPE, new AmendmentContainerEditEventHandler() {
            @Override
            public void onEvent(AmendmentContainerEditEvent event) {
                amendment = event.getAmendment();
                amendableWidget = event.getAmendableWidget();
                amendmentAction = amendment.getAmendmentAction();
                handle();
                show();
            }
        });
        clientFactory.getEventBus().addHandler(CloseDialogEvent.TYPE, new CloseDialogEventHandler() {
            @Override
            public void onEvent(CloseDialogEvent event) {
                hide();
            }
        });
    }

    protected AmendmentContainerDTO createAmendment() {
        AmendmentContainerDTO dto = new AmendmentContainerDTO();
        dto.setId(UUID.uuid());
        return dto;
    }

    protected AmendmentUIHandler getUIHandler() {
        if (amendmentAction == AmendmentAction.CREATION) {
            return amendmentDialogCreateController;
        }
        if (amendmentAction == AmendmentAction.MOVE) {
            return amendmentDialogMoveController;
        }
        if (amendmentAction == AmendmentAction.BUNDLE) {
            return amendmentDialogBundleController;
        }
        if ("table".equalsIgnoreCase(amendableWidget.getType()) || "tr".equalsIgnoreCase(amendableWidget.getType())) {
            return amendmentDialogTableController;
        }
        if ("img".equalsIgnoreCase(amendableWidget.getType())) {
            throw new UnsupportedOperationException("Not yet implemented.");
        }
        return amendmentDialogModifyController;
    }

    private void handle() {
        AmendmentUIHandler amendmentUIHandler = getUIHandler();

        final int widgetCount = this.view.getMainPanel().getWidgetCount();
        if (widgetCount > 0) {
            final Widget previous = this.view.getMainPanel().getWidget(0);
            if (previous != null) {
                previous.removeFromParent();
            }
        }

        this.view.getMainPanel().add(amendmentUIHandler.getView());
        view.getMainPanel().setCellHeight(amendmentUIHandler.getView().asWidget(), "100%");
        amendmentUIHandler.setAmendmentAndWidget(amendment, amendableWidget);
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
