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
package org.nsesa.editor.gwt.core.client.ui.amendment.action;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.ConfirmationEvent;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.NotificationEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerDeleteEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerStatusUpdatedEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.i18n.CoreMessages;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

import java.util.ArrayList;
import java.util.Arrays;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.AMENDMENT;

/**
 * The is the controller for the popup with available actions for a single amendment, after clicking the
 * {@link org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentView#getMoreActionsButton()}. This panel
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
     * The enclosing popup.
     */
    protected final PopupPanel popupPanel = new PopupPanel(true, false);
    private HandlerRegistration anchorTableHandlerRegistration;
    private HandlerRegistration anchorWithdrawHandlerRegistration;
    private HandlerRegistration anchorDeleteHandlerRegistration;

    @Inject
    public AmendmentActionPanelController(final AmendmentActionPanelView amendmentActionPanelView,
                                          final CoreMessages coreMessages) {
        this.view = amendmentActionPanelView;
        this.popupPanel.setWidget(amendmentActionPanelView);

        // create operations on the amendment
        addWidget(anchorTable);
        addWidget(anchorWithdraw);
        addSeparator();
        addWidget(anchorDelete);

        anchorTable.getElement().getStyle().setCursor(Style.Cursor.POINTER);
        anchorWithdraw.getElement().getStyle().setCursor(Style.Cursor.POINTER);
        anchorDelete.getElement().getStyle().setCursor(Style.Cursor.POINTER);

        // set the correct anchor labels
        anchorTable.setText(coreMessages.amendmentActionTable());
        anchorWithdraw.setText(coreMessages.amendmentActionWithdraw());
        anchorDelete.setText(coreMessages.amendmentActionDelete());

        registerListeners();
    }

    /**
     * Registers the event listeners on the various anchors.
     */
    private void registerListeners() {
        anchorTableHandlerRegistration = anchorTable.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final ClientFactory clientFactory = amendmentController.getDocumentController().getClientFactory();
                final ServiceFactory serviceFactory = amendmentController.getDocumentController().getServiceFactory();
                final String oldStatus = amendmentController.getModel().getAmendmentContainerStatus();
                popupPanel.hide();
                serviceFactory.getGwtAmendmentService().tableAmendmentContainers(clientFactory.getClientContext(),
                        new ArrayList<AmendmentContainerDTO>(Arrays.asList(amendmentController.getModel())),
                        new AsyncCallback<AmendmentContainerDTO[]>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not table amendment.", caught));
                            }

                            @Override
                            public void onSuccess(AmendmentContainerDTO[] result) {
                                amendmentController.setModel(result[0]);
                                final AmendmentContainerStatusUpdatedEvent updatedEvent = new AmendmentContainerStatusUpdatedEvent(amendmentController, oldStatus);
                                final DocumentEventBus documentEventBus = amendmentController.getDocumentController().getDocumentEventBus();
                                documentEventBus.fireEvent(updatedEvent);
                                documentEventBus.fireEvent(new NotificationEvent(clientFactory.getCoreMessages().amendmentActionTableSuccessful(result.length)));
                            }
                        });
            }
        });

        anchorWithdrawHandlerRegistration = anchorWithdraw.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final ClientFactory clientFactory = amendmentController.getDocumentController().getClientFactory();
                final ServiceFactory serviceFactory = amendmentController.getDocumentController().getServiceFactory();
                final String oldStatus = amendmentController.getModel().getAmendmentContainerStatus();
                popupPanel.hide();
                serviceFactory.getGwtAmendmentService().withdrawAmendmentContainers(clientFactory.getClientContext(),
                        new ArrayList<AmendmentContainerDTO>(Arrays.asList(amendmentController.getModel())),
                        new AsyncCallback<AmendmentContainerDTO[]>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not withdraw amendment.", caught));
                            }

                            @Override
                            public void onSuccess(AmendmentContainerDTO[] result) {
                                amendmentController.setModel(result[0]);
                                final AmendmentContainerStatusUpdatedEvent updatedEvent = new AmendmentContainerStatusUpdatedEvent(amendmentController, oldStatus);
                                final DocumentEventBus documentEventBus = amendmentController.getDocumentController().getDocumentEventBus();
                                documentEventBus.fireEvent(updatedEvent);
                                documentEventBus.fireEvent(new NotificationEvent(clientFactory.getCoreMessages().amendmentActionWithdrawSuccessful(result.length)));
                            }
                        });
            }
        });

        anchorDeleteHandlerRegistration = anchorDelete.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final ClientFactory clientFactory = amendmentController.getDocumentController().getClientFactory();
                final ServiceFactory serviceFactory = amendmentController.getDocumentController().getServiceFactory();
                popupPanel.hide();
                // ask for confirmation
                amendmentController.getDocumentController().getDocumentEventBus().fireEvent(new ConfirmationEvent(
                        clientFactory.getCoreMessages().confirmationAmendmentDeleteTitle(),
                        clientFactory.getCoreMessages().confirmationAmendmentDeleteMessage(),
                        clientFactory.getCoreMessages().confirmationAmendmentDeleteButtonConfirm(), new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        serviceFactory.getGwtAmendmentService().deleteAmendmentContainers(clientFactory.getClientContext(),
                                new ArrayList<AmendmentContainerDTO>(Arrays.asList(amendmentController.getModel())),
                                new AsyncCallback<AmendmentContainerDTO[]>() {
                                    @Override
                                    public void onFailure(Throwable caught) {
                                        clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not delete amendment.", caught));
                                    }

                                    @Override
                                    public void onSuccess(AmendmentContainerDTO[] result) {
                                        amendmentController.setModel(result[0]);
                                        final DocumentEventBus documentEventBus = amendmentController.getDocumentController().getDocumentEventBus();
                                        documentEventBus.fireEvent(new AmendmentContainerDeleteEvent(amendmentController));
                                        documentEventBus.fireEvent(new NotificationEvent(clientFactory.getCoreMessages().amendmentActionDeleteSuccessful(result.length)));
                                    }
                                });
                    }
                }, clientFactory.getCoreMessages().confirmationAmendmentDeleteButtonCancel(), new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        // does not do anything
                    }
                }
                ));

            }
        });
    }

    public void removeListeners() {
        anchorTableHandlerRegistration.removeHandler();
        anchorWithdrawHandlerRegistration.removeHandler();
        anchorDeleteHandlerRegistration.removeHandler();
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
        addWidget(new HTML("<hr class='separator'/>"));
    }

    /**
     * Shows the popup at the given coordinates.
     *
     * @param x the x (left) position, in pixels, relative to the browser window
     * @param y the y (top) position, in pixels, relative to the browser window
     */
    public void show(int x, int y) {
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
     * Sets the parent amendment controller, and registers the event listeners.
     *
     * @param amendmentController the parent amendment controller
     */
    public void setAmendmentController(AmendmentController amendmentController) {
        this.amendmentController = amendmentController;
    }
}
