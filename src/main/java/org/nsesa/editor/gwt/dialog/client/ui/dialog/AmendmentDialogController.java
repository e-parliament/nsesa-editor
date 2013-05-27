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
package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerCreateEvent;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerCreateEventHandler;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerEditEvent;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerEditEventHandler;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.util.UUID;
import org.nsesa.editor.gwt.core.shared.AmendmentAction;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.dialog.client.event.CloseDialogEvent;
import org.nsesa.editor.gwt.dialog.client.event.CloseDialogEventHandler;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandler;
import org.nsesa.editor.gwt.dialog.client.ui.handler.bundle.AmendmentDialogBundleController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.create.AmendmentDialogCreateController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.delete.AmendmentDialogDeleteController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.modify.AmendmentDialogModifyController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.move.AmendmentDialogMoveController;

/**
 * Main amendment dialog. Allows for the creation and editing of amendments. Typically consists of a two
 * column layout (with the original proposed text on the left, and a rich text editor on the right).
 * <p/>
 * Requires an {@link AmendmentContainerDTO}, a {@link AmendmentAction} and
 * {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget} to be set before it can be displayed.
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
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

    /**
     * Controller for handling creation amendments.
     */
    private final AmendmentDialogCreateController amendmentDialogCreateController;

    /**
     * Controller for handling deletion amendments.
     */
    private final AmendmentDialogDeleteController amendmentDialogDeleteController;

    /**
     * Controller for handling bundle amendments.
     */
    private final AmendmentDialogBundleController amendmentDialogBundleController;

    /**
     * Controller for move amendments.
     */
    private final AmendmentDialogMoveController amendmentDialogMoveController;

    /**
     * Controller for amendments on
     */
    private final AmendmentDialogModifyController amendmentDialogModifyController;

    /**
     * The client factory, with access to the {@link com.google.web.bindery.event.shared.EventBus},
     * {@link org.nsesa.editor.gwt.core.shared.ClientContext}, ..
     */
    private final ClientFactory clientFactory;

    /**
     * Service factory giving access to the RPC services.
     */
    private final OverlayFactory overlayFactory;

    /**
     * The dialog context, giving access to the {@link AmendmentContainerDTO},
     * {@link org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController}, ...
     */
    private DialogContext dialogContext;
    private HandlerRegistration amendmentContainerCreateEventHandlerRegistration;
    private HandlerRegistration amendmentContainerEditEventHandlerRegistration;
    private HandlerRegistration closeDialogEventHandlerRegistration;


    @Inject
    public AmendmentDialogController(final ClientFactory clientFactory, final AmendmentDialogView view,
                                     final AmendmentDialogCreateController amendmentDialogCreateController,
                                     final AmendmentDialogDeleteController amendmentDialogDeleteController,
                                     final AmendmentDialogBundleController amendmentDialogBundleController,
                                     final AmendmentDialogMoveController amendmentDialogMoveController,
                                     final AmendmentDialogModifyController amendmentDialogModifyController,
                                     final OverlayFactory overlayFactory) {
        this.clientFactory = clientFactory;
        this.overlayFactory = overlayFactory;
        this.view = view;

        this.amendmentDialogCreateController = amendmentDialogCreateController;
        this.amendmentDialogDeleteController = amendmentDialogDeleteController;
        this.amendmentDialogBundleController = amendmentDialogBundleController;
        this.amendmentDialogMoveController = amendmentDialogMoveController;
        this.amendmentDialogModifyController = amendmentDialogModifyController;

        this.popupPanel.setWidget(view);
        this.popupPanel.setGlassEnabled(true);

        registerListeners();
    }

    private void registerListeners() {

        // register a listener when a request to make an amendment is fired (regardless of its type)
        amendmentContainerCreateEventHandlerRegistration = clientFactory.getEventBus().addHandler(AmendmentContainerCreateEvent.TYPE, new AmendmentContainerCreateEventHandler() {
            @Override
            public void onEvent(AmendmentContainerCreateEvent event) {
                dialogContext = new DialogContext();
                dialogContext.setOverlayWidget(event.getOverlayWidget());
                dialogContext.setReferenceOverlayWidget(event.getReference());
                dialogContext.setParentOverlayWidget(event.getParentOverlayWidget());
                dialogContext.setAmendmentAction(event.getAmendmentAction());
                dialogContext.setDocumentController(event.getDocumentController());
                dialogContext.setAmendment(createAmendment());
                handle();
                show();
            }
        });

        // register a listener to edit an existing amendment (regardless of its type)
        amendmentContainerEditEventHandlerRegistration = clientFactory.getEventBus().addHandler(AmendmentContainerEditEvent.TYPE, new AmendmentContainerEditEventHandler() {
            @Override
            public void onEvent(AmendmentContainerEditEvent event) {
                dialogContext = new DialogContext();
                dialogContext.setAmendmentController(event.getAmendmentController());
                dialogContext.setAmendment(event.getAmendmentController().getModel());
                dialogContext.setOverlayWidget(event.getAmendmentController().getOverlayWidget());
                dialogContext.setReferenceOverlayWidget(event.getAmendmentController().getOverlayWidget());
                dialogContext.setParentOverlayWidget(event.getAmendmentController().getOverlayWidget().getParentOverlayWidget());
                dialogContext.setAmendmentAction(event.getAmendmentController().getModel().getAmendmentAction());
                dialogContext.setDocumentController(event.getAmendmentController().getDocumentController());
                handle();
                show();
            }
        });

        // respond to dialog close requests
        closeDialogEventHandlerRegistration = clientFactory.getEventBus().addHandler(CloseDialogEvent.TYPE, new CloseDialogEventHandler() {
            @Override
            public void onEvent(CloseDialogEvent event) {
                dialogContext = new DialogContext();
                hide();
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        amendmentContainerCreateEventHandlerRegistration.removeHandler();
        amendmentContainerEditEventHandlerRegistration.removeHandler();
        closeDialogEventHandlerRegistration.removeHandler();
    }

    /**
     * Create a default {@link AmendmentContainerDTO} with a
     * {@link org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO#getId()} and
     * {@link org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO#getRevisionID()} set.
     *
     * @return the amendment container DTO
     */
    protected AmendmentContainerDTO createAmendment() {
        final AmendmentContainerDTO dto = new AmendmentContainerDTO();
        // set the primary key
        dto.setId(UUID.uuid());
        // set the revision
        dto.setRevisionID(UUID.uuid());
        return dto;
    }

    /**
     * Returns the {@link AmendmentUIHandler} to handle a given
     * {@link org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext#getAmendmentAction()}. Can easily be extended
     * to also take the {@link org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext#getOverlayWidget()}'s type
     * into account (eg. on tables).
     *
     * @return the ui handler
     */
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

    /**
     * Actually handle the request by removing any prior displayed {@link AmendmentUIHandler}s' view.
     */
    private void handle() {
        final AmendmentUIHandler amendmentUIHandler = getUIHandler();

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

    /**
     * Hide the dialog popup.
     */
    public void hide() {
        popupPanel.hide(true);
    }

    /**
     * Return the associated view.
     *
     * @return
     */
    public AmendmentDialogView getView() {
        return view;
    }
}
