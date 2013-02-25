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
package org.nsesa.editor.gwt.core.client.ui.document.amendments.header;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.ConfirmationEvent;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.NotificationEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerDeleteEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerStatusUpdatedEvent;
import org.nsesa.editor.gwt.core.client.event.amendments.AmendmentControllerSelectedEvent;
import org.nsesa.editor.gwt.core.client.event.amendments.AmendmentControllerSelectedEventHandler;
import org.nsesa.editor.gwt.core.client.event.amendments.AmendmentControllerSelectionActionEvent;
import org.nsesa.editor.gwt.core.client.event.amendments.AmendmentControllerSelectionEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

import java.util.ArrayList;
import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * The controller for amendments panel header
 * User: groza
 * Date: 26/11/12
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentsHeaderController {

    private final AmendmentsHeaderView view;
    private DocumentEventBus documentEventBus;
    private DocumentController documentController;
    private final InlineHTML selectedAmount = new InlineHTML();

    @Inject
    public AmendmentsHeaderController(final AmendmentsHeaderView view,
                                      final DocumentEventBus documentEventBus
    ) {
        this.view = view;
        this.documentEventBus = documentEventBus;
    }

    public AmendmentsHeaderView getView() {
        return view;
    }

    protected void registerActions() {

        final ClientFactory clientFactory = documentController.getClientFactory();
        final ServiceFactory serviceFactory = documentController.getServiceFactory();

        final Button tableButton = new Button(clientFactory.getCoreMessages().amendmentActionTable());
        tableButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new AmendmentControllerSelectionActionEvent(new AmendmentControllerSelectionActionEvent.Action() {
                    @Override
                    public void execute(final List<AmendmentController> amendmentControllers) {
                        if (!amendmentControllers.isEmpty()) {
                            serviceFactory.getGwtAmendmentService().tableAmendmentContainers(clientFactory.getClientContext(),
                                    transformToDTOs(amendmentControllers),
                                    new AsyncCallback<AmendmentContainerDTO[]>() {
                                        @Override
                                        public void onFailure(Throwable caught) {
                                            clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not table amendment(s).", caught));
                                        }

                                        @Override
                                        public void onSuccess(AmendmentContainerDTO[] result) {
                                            int index = 0;
                                            for (final AmendmentContainerDTO dto : result) {
                                                final AmendmentController amendmentController = amendmentControllers.get(index);
                                                final String oldStatus = amendmentController.getModel().getAmendmentContainerStatus();
                                                amendmentController.setAmendment(dto);
                                                documentEventBus.fireEvent(new AmendmentContainerStatusUpdatedEvent(amendmentController, oldStatus));
                                                index++;
                                            }
                                            documentEventBus.fireEvent(new NotificationEvent(clientFactory.getCoreMessages().amendmentActionTableSuccessful(result.length)));
                                        }
                                    });
                        }
                    }
                }));
            }
        });
        view.addAction(tableButton);

        final Button withdrawButton = new Button(clientFactory.getCoreMessages().amendmentActionWithdraw());
        withdrawButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new AmendmentControllerSelectionActionEvent(new AmendmentControllerSelectionActionEvent.Action() {
                    @Override
                    public void execute(final List<AmendmentController> amendmentControllers) {
                        if (!amendmentControllers.isEmpty()) {
                            serviceFactory.getGwtAmendmentService().withdrawAmendmentContainers(clientFactory.getClientContext(),
                                    transformToDTOs(amendmentControllers),
                                    new AsyncCallback<AmendmentContainerDTO[]>() {
                                        @Override
                                        public void onFailure(Throwable caught) {
                                            clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not withdraw amendment(s).", caught));
                                        }

                                        @Override
                                        public void onSuccess(AmendmentContainerDTO[] result) {
                                            int index = 0;
                                            for (final AmendmentContainerDTO dto : result) {
                                                final AmendmentController amendmentController = amendmentControllers.get(index);
                                                final String oldStatus = amendmentController.getModel().getAmendmentContainerStatus();
                                                amendmentController.setAmendment(dto);
                                                documentEventBus.fireEvent(new AmendmentContainerStatusUpdatedEvent(amendmentController, oldStatus));
                                                index++;
                                            }
                                            documentEventBus.fireEvent(new NotificationEvent(clientFactory.getCoreMessages().amendmentActionWithdrawSuccessful(result.length)));
                                        }
                                    });
                        }
                    }
                }));
            }
        });
        view.addAction(withdrawButton);

        final Button deleteButton = new Button(clientFactory.getCoreMessages().amendmentActionDelete());
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new ConfirmationEvent(
                        clientFactory.getCoreMessages().confirmationAmendmentDeleteTitle(), clientFactory.getCoreMessages().confirmationAmendmentDeleteMessage(), clientFactory.getCoreMessages().amendmentActionDelete(),
                        new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                documentEventBus.fireEvent(new AmendmentControllerSelectionActionEvent(new AmendmentControllerSelectionActionEvent.Action() {
                                    @Override
                                    public void execute(final List<AmendmentController> amendmentControllers) {
                                        if (!amendmentControllers.isEmpty()) {
                                            documentEventBus.fireEvent(new AmendmentContainerDeleteEvent(amendmentControllers.toArray(new AmendmentController[amendmentControllers.size()])));
                                        }
                                    }
                                }));
                            }
                        },
                        clientFactory.getCoreMessages().amendmentActionCancel(),
                        new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                // don't do anything special
                            }
                        }
                ));

            }
        });
        view.addAction(deleteButton);
    }

    private ArrayList<AmendmentContainerDTO> transformToDTOs(final List<AmendmentController> amendmentControllers) {
        return new ArrayList<AmendmentContainerDTO>(Collections2.transform(amendmentControllers, new Function<AmendmentController, AmendmentContainerDTO>() {
            @Override
            public AmendmentContainerDTO apply(AmendmentController input) {
                return input.getModel();
            }
        }));
    }


    protected void registerSelections() {
        final ClientFactory clientFactory = documentController.getClientFactory();

        final Anchor selectAll = new Anchor("All");
        selectAll.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new AmendmentControllerSelectionEvent(new Selection.AllSelection<AmendmentController>()));
            }
        });
        view.addSelection(selectAll);

        view.addSelection(new InlineHTML(", "));

        final Anchor selectNone = new Anchor("None");
        selectNone.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new AmendmentControllerSelectionEvent(new Selection.NoneSelection<AmendmentController>()));
            }
        });
        view.addSelection(selectNone);

        view.addSelection(new InlineHTML("&nbsp;-&nbsp;"));

        final Anchor selectCandidate = new Anchor(clientFactory.getCoreMessages().amendmentStatusCandidate());
        selectCandidate.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new AmendmentControllerSelectionEvent(new Selection<AmendmentController>() {
                    @Override
                    public boolean select(AmendmentController amendmentController) {
                        return "candidate".equalsIgnoreCase(amendmentController.getModel().getAmendmentContainerStatus());
                    }
                }));
            }
        });
        view.addSelection(selectCandidate);

        view.addSelection(new InlineHTML(", "));

        final Anchor selectTabled = new Anchor(clientFactory.getCoreMessages().amendmentStatusTabled());
        selectTabled.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new AmendmentControllerSelectionEvent(new Selection<AmendmentController>() {
                    @Override
                    public boolean select(AmendmentController amendmentController) {
                        return "tabled".equalsIgnoreCase(amendmentController.getModel().getAmendmentContainerStatus());
                    }
                }));
            }
        });
        view.addSelection(selectTabled);

        view.addSelection(new InlineHTML(", "));

        final Anchor selectWithdrawn = new Anchor(clientFactory.getCoreMessages().amendmentStatusWithdrawn());
        selectWithdrawn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new AmendmentControllerSelectionEvent(new Selection<AmendmentController>() {
                    @Override
                    public boolean select(AmendmentController amendmentController) {
                        return "withdrawn".equalsIgnoreCase(amendmentController.getModel().getAmendmentContainerStatus());
                    }
                }));
            }
        });
        view.addSelection(selectWithdrawn);
        view.addSelection(selectedAmount);
    }

    private void registerListeners() {
        documentEventBus.addHandler(AmendmentControllerSelectedEvent.TYPE, new AmendmentControllerSelectedEventHandler() {
            @Override
            public void onEvent(AmendmentControllerSelectedEvent event) {
                selectedAmount.setHTML("&nbsp;&nbsp;" + event.getSelected().size() + " amendments selected.");
            }
        });
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
        registerListeners();
        // register the selections
        registerSelections();
        // register the actions
        registerActions();
    }
}
