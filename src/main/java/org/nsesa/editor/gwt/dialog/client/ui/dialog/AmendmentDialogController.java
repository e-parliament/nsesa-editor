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
package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEventHandler;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerEditEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerEditEventHandler;
import org.nsesa.editor.gwt.core.shared.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.util.UUID;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.dialog.client.event.CloseDialogEvent;
import org.nsesa.editor.gwt.dialog.client.event.CloseDialogEventHandler;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandler;
import org.nsesa.editor.gwt.dialog.client.ui.handler.bundle.AmendmentDialogBundleController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.create.AmendmentDialogCreateController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.delete.AmendmentDialogDeleteController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.modify.AmendmentDialogModifyController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.move.AmendmentDialogMoveController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.table.AmendmentDialogTableController;

/**
 * Main amendment dialog. Allows for the creation and editing of amendments. Typically consists of a two
 * column layout (with the original proposed text on the left, and a rich text editor on the right).
 * <p/>
 * Requires an {@link AmendmentContainerDTO}, a {@link AmendmentAction} and
 * {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget} to be set before it can be displayed.
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogController extends Composite implements ProvidesResize {
    /**
     * The encompassing popup panel used to display this view as a modal dialog.
     */
    //if the popup is modal then the CKeditor dialogs will not work properly
    private final PopupPanel popupPanel = new DecoratedPopupPanel(false, false);

    /**
     * The amendment dialog view.
     */
    private final AmendmentDialogView view;

    private final AmendmentDialogCreateController amendmentDialogCreateController;
    private final AmendmentDialogDeleteController amendmentDialogDeleteController;
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

    private DialogContext dialogContext;


    @Inject
    public AmendmentDialogController(final ClientFactory clientFactory, final AmendmentDialogView view,
                                     final AmendmentDialogCreateController amendmentDialogCreateController,
                                     final AmendmentDialogDeleteController amendmentDialogDeleteController,
                                     final AmendmentDialogBundleController amendmentDialogBundleController,
                                     final AmendmentDialogTableController amendmentDialogTableController,
                                     final AmendmentDialogMoveController amendmentDialogMoveController,
                                     final AmendmentDialogModifyController amendmentDialogModifyController,
                                     final OverlayFactory overlayFactory) {
        this.clientFactory = clientFactory;
        this.overlayFactory = overlayFactory;
        this.view = view;

        this.amendmentDialogCreateController = amendmentDialogCreateController;
        this.amendmentDialogDeleteController = amendmentDialogDeleteController;
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
                dialogContext = new DialogContext();
                dialogContext.setOverlayWidget(event.getOverlayWidget());
                dialogContext.setParentOverlayWidget(event.getParentOverlayWidget());
                dialogContext.setIndex(event.getIndex());
                dialogContext.setAmendmentAction(event.getAmendmentAction());
                dialogContext.setDocumentController(event.getDocumentController());
                dialogContext.setAmendment(createAmendment());
                handle();
                show();
            }
        });
        clientFactory.getEventBus().addHandler(AmendmentContainerEditEvent.TYPE, new AmendmentContainerEditEventHandler() {
            @Override
            public void onEvent(AmendmentContainerEditEvent event) {
                dialogContext = new DialogContext();
                dialogContext.setAmendmentController(event.getAmendmentController());
                dialogContext.setAmendment(event.getAmendmentController().getModel());
                dialogContext.setOverlayWidget(event.getAmendmentController().getAmendedOverlayWidget());
                dialogContext.setParentOverlayWidget(event.getAmendmentController().getAmendedOverlayWidget().getParentOverlayWidget());
                dialogContext.setAmendmentAction(event.getAmendmentController().getModel().getAmendmentAction());
                dialogContext.setDocumentController(event.getAmendmentController().getDocumentController());
                handle();
                show();
            }
        });
        clientFactory.getEventBus().addHandler(CloseDialogEvent.TYPE, new CloseDialogEventHandler() {
            @Override
            public void onEvent(CloseDialogEvent event) {
                dialogContext = new DialogContext();
                hide();
            }
        });
    }

    protected AmendmentContainerDTO createAmendment() {
        AmendmentContainerDTO dto = new AmendmentContainerDTO();
        // set the primary key
        dto.setId(UUID.uuid());
        // set the revision
        dto.setRevisionID(UUID.uuid());
        return dto;
    }

    protected AmendmentUIHandler getUIHandler() {
        if (dialogContext.getAmendmentAction() == AmendmentAction.CREATION) {
            return amendmentDialogCreateController;
        }
        if (dialogContext.getAmendmentAction() == AmendmentAction.MOVE) {
            return amendmentDialogMoveController;
        }
        if (dialogContext.getAmendmentAction() == AmendmentAction.DELETION) {
            return amendmentDialogDeleteController;
        }
        if (dialogContext.getAmendmentAction() == AmendmentAction.BUNDLE) {
            return amendmentDialogBundleController;
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
        amendmentUIHandler.setContext(dialogContext);
        amendmentUIHandler.handle();
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

    public AmendmentDialogView getView() {
        return view;
    }
}
