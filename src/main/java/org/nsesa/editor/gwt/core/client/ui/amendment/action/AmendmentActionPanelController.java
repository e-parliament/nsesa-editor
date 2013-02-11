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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.ArrayList;
import java.util.Arrays;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.AMENDMENT;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(AMENDMENT)
public class AmendmentActionPanelController {

    protected final AmendmentActionPanelView view;

    protected final ClientFactory clientFactory;
    protected final ServiceFactory serviceFactory;

    // parent amendment controller
    protected AmendmentController amendmentController;

    protected final Anchor anchorTable = new Anchor();

    protected final Anchor anchorWithdraw = new Anchor();

    protected final Anchor anchorDelete = new Anchor();

    protected final PopupPanel popupPanel = new PopupPanel(true, false);

    @Inject
    public AmendmentActionPanelController(final ClientFactory clientFactory,
                                          final ServiceFactory serviceFactory,
                                          final AmendmentActionPanelView amendmentActionPanelView) {
        this.clientFactory = clientFactory;
        this.serviceFactory = serviceFactory;
        this.view = amendmentActionPanelView;
        this.popupPanel.setWidget(amendmentActionPanelView);

        // set the correct anchor labels
        anchorTable.setText(clientFactory.getCoreMessages().amendmentActionTable());
        anchorWithdraw.setText(clientFactory.getCoreMessages().amendmentActionWithdraw());
        anchorDelete.setText(clientFactory.getCoreMessages().amendmentActionDelete());

        // create operations on the amendment
        addWidget(anchorTable);
        addWidget(anchorWithdraw);
        addSeparator();
        addWidget(anchorDelete);

        registerListeners();
    }

    private void registerListeners() {
        anchorTable.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
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
                                amendmentController.setAmendment(result[0]);
                                final AmendmentContainerStatusUpdatedEvent updatedEvent = new AmendmentContainerStatusUpdatedEvent(amendmentController, oldStatus);
                                final DocumentEventBus documentEventBus = amendmentController.getDocumentController().getDocumentEventBus();
                                documentEventBus.fireEvent(updatedEvent);
                                documentEventBus.fireEvent(new NotificationEvent(clientFactory.getCoreMessages().amendmentActionTableSuccessful(result.length)));
                            }
                        });
            }
        });

        anchorWithdraw.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
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
                                amendmentController.setAmendment(result[0]);
                                final AmendmentContainerStatusUpdatedEvent updatedEvent = new AmendmentContainerStatusUpdatedEvent(amendmentController, oldStatus);
                                final DocumentEventBus documentEventBus = amendmentController.getDocumentController().getDocumentEventBus();
                                documentEventBus.fireEvent(updatedEvent);
                                documentEventBus.fireEvent(new NotificationEvent(clientFactory.getCoreMessages().amendmentActionWithdrawSuccessful(result.length)));
                            }
                        });
            }
        });

        anchorDelete.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
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
                                        amendmentController.setAmendment(result[0]);
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

    public void addWidget(IsWidget widget) {
        view.getMainPanel().add(widget);
    }

    public void addWidget(Widget widget) {
        view.getMainPanel().add(widget);
    }

    public void addSeparator() {
        addWidget(new HTML("<hr class='separator'/>"));
    }

    public void show(int x, int y) {
        popupPanel.setPopupPosition(x, y);
        popupPanel.show();
    }

    public void hide() {
        popupPanel.hide();
    }

    public void setAmendmentController(AmendmentController amendmentController) {
        this.amendmentController = amendmentController;
    }
}
