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
package org.nsesa.editor.gwt.amendment.client.ui.amendment.action;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.amendment.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerDeleteEvent;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.amendment.client.ui.document.AmendmentDocumentController;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.ConfirmationEvent;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.InformationEvent;
import org.nsesa.editor.gwt.core.client.event.widget.OverlayWidgetMoveEvent;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTAmendmentServiceAsync;
import org.nsesa.editor.gwt.core.client.ui.i18n.CoreMessages;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentAction;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.PersonDTO;

import java.util.ArrayList;
import java.util.Arrays;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.AMENDMENT;

/**
 * The is the controller for the popup with available actions for a single amendment, after clicking the
 * {@link org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentView#getMoreActionsButton()}. This panel
 * gives access to several actions that are possible at the time of appearance (they might or might not be
 * valid anymore during the actual action invocation).
 * <p/>
 * This controller can be extended by calling the {@link #addWidget(com.google.gwt.user.client.ui.Widget)} or
 * {@link #addWidget(com.google.gwt.user.client.ui.IsWidget)} methods.
 * <p/>
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(AMENDMENT)
public class AmendmentActionPanelController {

    /**
     * The view.
     */
    protected final AmendmentActionPanelView view;

    /**
     * The parent amendment controller.
     */
    protected AmendmentController amendmentController;

    /**
     * An anchor to table the amendment.
     */
    protected final Anchor anchorTable = new Anchor();

    /**
     * An anchor to withdraw an amendment.
     */
    protected final Anchor anchorWithdraw = new Anchor();

    /**
     * An anchor to delete an amendment.
     */
    protected final Anchor anchorDelete = new Anchor();

    /**
     * An separator widget.
     */
    protected final Widget shareSeparator;

    /**
     * An anchor to share an amendment.
     */
    protected final Anchor anchorShare = new Anchor();

    /**
     * An separator widget.
     */
    protected final Widget moveSeparator;

    /**
     * An anchor to move up an amendment.
     */
    protected final Anchor anchorMoveUp = new Anchor();

    /**
     * An anchor to move down an amendment.
     */
    protected final Anchor anchorMoveDown = new Anchor();

    /**
     * The enclosing popup.
     */
    protected final PopupPanel popupPanel = new PopupPanel(true, false);

    private ClickHandler confirmDeleteHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            final String status = amendmentController.getModel().getAmendmentContainerStatus();
            if (!"candidate".equalsIgnoreCase(status) && !"withdrawn".equalsIgnoreCase(status)) {
                // you're only allowed to remove
                amendmentController.getDocumentController().getClientFactory().getEventBus().fireEvent(new InformationEvent("You cannot do that.", "You can only delete candidate or withdrawn amendments. Please withdraw the amendment first."));
            } else {
                amendmentController.getDocumentController().getDocumentEventBus().fireEvent(new AmendmentContainerDeleteEvent(amendmentController));
            }
        }
    };
    private ClickHandler confirmTableHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            final AmendmentManager amendmentManager = ((AmendmentDocumentController) amendmentController.getDocumentController()).getAmendmentManager();
            amendmentManager.tableAmendmentContainers(amendmentController);
        }
    };
    private ClickHandler confirmWithdrawHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            final AmendmentManager amendmentManager = ((AmendmentDocumentController) amendmentController.getDocumentController()).getAmendmentManager();
            amendmentManager.withdrawAmendmentContainers(amendmentController);
        }
    };
    private ClickHandler cancelHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            // this does not do anything
        }
    };


    private HandlerRegistration anchorTableHandlerRegistration;
    private HandlerRegistration anchorWithdrawHandlerRegistration;
    private HandlerRegistration anchorDeleteHandlerRegistration;
    private HandlerRegistration anchorShareHandlerRegistration;
    private HandlerRegistration anchorMoveUpHandlerRegistration;
    private HandlerRegistration anchorMoveDownHandlerRegistration;


    @Inject
    public AmendmentActionPanelController(final AmendmentActionPanelView amendmentActionPanelView,
                                          final CoreMessages coreMessages) {
        this.view = amendmentActionPanelView;
        this.popupPanel.setWidget(amendmentActionPanelView);
        // create operations on the amendment
        addWidget(anchorTable);
        addWidget(anchorWithdraw);
        addWidget(anchorDelete);
        shareSeparator = getSeparator();
        addWidget(shareSeparator);
        addWidget(anchorShare);
        moveSeparator = getSeparator();
        addWidget(moveSeparator);
        addWidget(anchorMoveUp);
        addWidget(anchorMoveDown);

        anchorTable.getElement().getStyle().setCursor(Style.Cursor.POINTER);
        anchorWithdraw.getElement().getStyle().setCursor(Style.Cursor.POINTER);
        anchorDelete.getElement().getStyle().setCursor(Style.Cursor.POINTER);
        anchorShare.getElement().getStyle().setCursor(Style.Cursor.POINTER);
        anchorMoveUp.getElement().getStyle().setCursor(Style.Cursor.POINTER);
        anchorMoveDown.getElement().getStyle().setCursor(Style.Cursor.POINTER);

        // set the correct anchor labels
        anchorTable.setText(coreMessages.amendmentActionTable());
        anchorWithdraw.setText(coreMessages.amendmentActionWithdraw());
        anchorDelete.setText(coreMessages.amendmentActionDelete());
        anchorShare.setText(coreMessages.amendmentActionShare());
        anchorMoveUp.setText(coreMessages.amendmentActionMoveUp());
        anchorMoveDown.setText(coreMessages.amendmentActionMoveDown());
    }

    /**
     * Registers the event listeners on the various anchors.
     */
    public void registerListeners() {
        anchorTableHandlerRegistration = anchorTable.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final AmendmentManager amendmentManager = ((AmendmentDocumentController) amendmentController.getDocumentController()).getAmendmentManager();
                if (amendmentManager != null) {
                    final ClientFactory clientFactory = amendmentController.getDocumentController().getClientFactory();
                    final ConfirmationEvent confirmationEvent = new ConfirmationEvent(
                            clientFactory.getCoreMessages().confirmationAmendmentTableTitle(),
                            clientFactory.getCoreMessages().confirmationAmendmentTableMessage(),
                            clientFactory.getCoreMessages().confirmationAmendmentTableButtonConfirm(),
                            confirmTableHandler,
                            clientFactory.getCoreMessages().confirmationAmendmentTableButtonCancel(),
                            cancelHandler);

                    amendmentController.getDocumentController().getDocumentEventBus().fireEvent(confirmationEvent);
                } else {
                    // you cannot table the amendment if no document controller has been set
                }
                popupPanel.hide();
            }
        });

        anchorWithdrawHandlerRegistration = anchorWithdraw.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final AmendmentManager amendmentManager = ((AmendmentDocumentController) amendmentController.getDocumentController()).getAmendmentManager();
                if (amendmentManager != null) {
                    final ClientFactory clientFactory = amendmentController.getDocumentController().getClientFactory();
                    final ConfirmationEvent confirmationEvent = new ConfirmationEvent(
                            clientFactory.getCoreMessages().confirmationAmendmentWithdrawTitle(),
                            clientFactory.getCoreMessages().confirmationAmendmentWithdrawMessage(),
                            clientFactory.getCoreMessages().confirmationAmendmentWithdrawButtonConfirm(),
                            confirmWithdrawHandler,
                            clientFactory.getCoreMessages().confirmationAmendmentWithdrawButtonCancel(),
                            cancelHandler);

                    amendmentController.getDocumentController().getDocumentEventBus().fireEvent(confirmationEvent);
                } else {
                    // you cannot withdraw the amendment if no document controller has been set
                }
                popupPanel.hide();
            }
        });

        anchorDeleteHandlerRegistration = anchorDelete.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final AmendmentManager amendmentManager = ((AmendmentDocumentController) amendmentController.getDocumentController()).getAmendmentManager();
                if (amendmentManager != null) {
                    final ClientFactory clientFactory = amendmentController.getDocumentController().getClientFactory();
                    final ConfirmationEvent confirmationEvent = new ConfirmationEvent(
                            clientFactory.getCoreMessages().confirmationAmendmentDeleteTitle(),
                            clientFactory.getCoreMessages().confirmationAmendmentDeleteMessage(),
                            clientFactory.getCoreMessages().confirmationAmendmentDeleteButtonConfirm(),
                            confirmDeleteHandler,
                            clientFactory.getCoreMessages().confirmationAmendmentDeleteButtonCancel(),
                            cancelHandler);

                    amendmentController.getDocumentController().getDocumentEventBus().fireEvent(confirmationEvent);

                } else {
                    // you cannot delete the amendment if no document controller has been set
                }
                popupPanel.hide();

            }
        });

        anchorMoveUpHandlerRegistration = anchorMoveUp.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                OverlayWidget amendedOverlayWidget = amendmentController.getOverlayWidget();
                amendmentController.getDocumentController().getDocumentEventBus().fireEvent(new OverlayWidgetMoveEvent(amendedOverlayWidget,
                        OverlayWidgetMoveEvent.MoveType.Up, amendmentController));
                popupPanel.hide();
            }
        });

        anchorMoveDownHandlerRegistration = anchorMoveDown.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                OverlayWidget amendedOverlayWidget = amendmentController.getOverlayWidget();
                amendmentController.getDocumentController().getDocumentEventBus().fireEvent(new OverlayWidgetMoveEvent(amendedOverlayWidget,
                        OverlayWidgetMoveEvent.MoveType.Down, amendmentController));
                popupPanel.hide();
            }
        });

    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        anchorTableHandlerRegistration.removeHandler();
        anchorWithdrawHandlerRegistration.removeHandler();
        anchorDeleteHandlerRegistration.removeHandler();
        anchorShareHandlerRegistration.removeHandler();
        anchorMoveUpHandlerRegistration.removeHandler();
        anchorMoveDownHandlerRegistration.removeHandler();

    }

    /**
     * Adds a widget to the action panel.
     *
     * @param widget the widget to add.
     */
    public void addWidget(IsWidget widget) {
        view.getMainPanel().add(widget);
    }

    /**
     * Adds a widget to the action panel.
     *
     * @param widget the widget to add.
     */
    public void addWidget(Widget widget) {
        view.getMainPanel().add(widget);
    }

    /**
     * Adds a visual separator (an hr element with the css class 'separator') to the actions listed.
     */
    public void addSeparator() {
        addWidget(getSeparator());
    }

    /**
     * Shows the popup at the given coordinates.
     *
     * @param x the x (left) position, in pixels, relative to the browser window
     * @param y the y (top) position, in pixels, relative to the browser window
     */
    public void show(int x, int y) {
        validatePossibleActions();
        popupPanel.setPopupPosition(x, y);
        popupPanel.show();
    }

    /**
     * Hides the popup panel.
     */
    public void hide() {
        popupPanel.hide();
    }

    /**
     * Checks the various possible actions (currently limited to delete, table and withdraw) against the backend
     * to make sure they are possible on the selected amendment container.
     */
    public void validatePossibleActions() {
        final ClientFactory clientFactory = amendmentController.getDocumentController().getClientFactory();
        final ClientContext clientContext = clientFactory.getClientContext();
        final ArrayList<AmendmentContainerDTO> amendmentContainers = new ArrayList<AmendmentContainerDTO>(Arrays.asList(amendmentController.getModel()));

        // delete anchor
        anchorDelete.setVisible(false);
        final GWTAmendmentServiceAsync gwtAmendmentService = amendmentController.getDocumentController().getServiceFactory().getGwtAmendmentService();
        gwtAmendmentService.canDeleteAmendmentContainers(clientContext, amendmentContainers, new AsyncCallback<Boolean[]>() {
            @Override
            public void onFailure(Throwable caught) {
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not validate delete request.", caught));
            }

            @Override
            public void onSuccess(Boolean[] result) {
                anchorDelete.setVisible(result[0]);
            }
        });

        // table anchor
        anchorTable.setVisible(false);
        gwtAmendmentService.canTableAmendmentContainers(clientContext, amendmentContainers, new AsyncCallback<Boolean[]>() {
            @Override
            public void onFailure(Throwable caught) {
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not validate table request.", caught));
            }

            @Override
            public void onSuccess(Boolean[] result) {
                anchorTable.setVisible(result[0]);
            }
        });

        // withdraw anchor
        anchorWithdraw.setVisible(false);
        gwtAmendmentService.canWithdrawAmendmentContainers(clientContext, amendmentContainers, new AsyncCallback<Boolean[]>() {
            @Override
            public void onFailure(Throwable caught) {
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not validate withdraw request.", caught));
            }

            @Override
            public void onSuccess(Boolean[] result) {
                anchorWithdraw.setVisible(result[0]);
            }
        });

        // share anchor
        PersonDTO person = amendmentController.getDocumentController().getClientFactory().getClientContext().getLoggedInPerson();
        boolean canShare = !person.getGroups().isEmpty();
        anchorShare.setVisible(canShare);
        shareSeparator.setVisible(canShare);
    }

    /**
     * Sets the parent amendment controller, and registers the event listeners.
     *
     * @param amendmentController the parent amendment controller
     */
    public void setAmendmentController(AmendmentController amendmentController) {
        this.amendmentController = amendmentController;
        hideMoveOperations();
    }

    /**
     * Hide move actions for creation
     */
    private void hideMoveOperations() {
        boolean isCreation = AmendmentAction.CREATION.equals(amendmentController.getModel().getAmendmentAction());
        moveSeparator.setVisible(isCreation);
        anchorMoveUp.setVisible(isCreation);
        anchorMoveDown.setVisible(isCreation);
    }

    /**
     * Returns a widget separator as hr html tag
     *
     * @return {@link Widget} separator
     */
    protected Widget getSeparator() {
        return new HTML("<hr class='separator'/>");
    }

    public AmendmentActionPanelView getView() {
        return view;
    }
}
