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
 * <code>AmendmentsHeaderController</code> class is responsible to set up the selections and the actions available in
 * {@link org.nsesa.editor.gwt.core.client.ui.document.amendments.filter.AmendmentsFilterView} view.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 26/11/12 11:50
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentsHeaderController {
    /**
     * The view associated to the controller
     */
    private final AmendmentsHeaderView view;

    /**
     * the event bus to raise GWT events
     */
    private DocumentEventBus documentEventBus;
    private ClientFactory clientFactory;
    /**
     * Used to get reference to {@link ServiceFactory} when user request a given action over selected amendments
     */
    private DocumentController documentController;

    /**
     * Stores the number of amendments after applying a {@link Selection}
     */
    private final InlineHTML selectedAmount = new InlineHTML();

    /**
     * Create <code>AmendmentsHeaderController</code> with the given properties
     * @param view The view associated
     * @param documentEventBus The document event bus
     */
    @Inject
    public AmendmentsHeaderController(final AmendmentsHeaderView view,
                                      final DocumentEventBus documentEventBus,
                                      final ClientFactory clientFactory
    ) {
        this.view = view;
        this.documentEventBus = documentEventBus;
        this.clientFactory = clientFactory;

        this.selectedAmount.setHTML("&nbsp;&nbsp;" + clientFactory.getCoreMessages().amendmentSelection(0));

    }

    /**
     * Returns the view associated to the controller
     * @return The view
     */
    public AmendmentsHeaderView getView() {
        return view;
    }

    /**
     * Register actions that could be performed by the user in the actions area. Three actions are available as
     * default: table, withdraw and delete. When the user performs the actions from the view for each type of action
     *
     */
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
                                                amendmentController.setModel(dto);
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
                                                amendmentController.setModel(dto);
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

    /**
     * Returns the DTO models associated to amendment controllers
     * @param amendmentControllers The amendment controllers processed
     * @return An ArrayList of <code>AmendmentContainerDTO</code>
     */
    private ArrayList<AmendmentContainerDTO> transformToDTOs(final List<AmendmentController> amendmentControllers) {
        return new ArrayList<AmendmentContainerDTO>(Collections2.transform(amendmentControllers, new Function<AmendmentController, AmendmentContainerDTO>() {
            @Override
            public AmendmentContainerDTO apply(AmendmentController input) {
                return input.getModel();
            }
        }));
    }

    /**
     * Set up the selections (all, none, candidate, tabled, withdrawn) that could be performed against the existing
     * amendments and the associated handlers for each type of selection. Basically a handler fire a GWT
     * event for each type of selection performed
     */
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

    /**
     * Refresh the number of selected amendments by handling {@link AmendmentControllerSelectedEvent} event
     */
    private void registerListeners() {
        documentEventBus.addHandler(AmendmentControllerSelectedEvent.TYPE, new AmendmentControllerSelectedEventHandler() {
            @Override
            public void onEvent(AmendmentControllerSelectedEvent event) {
                selectedAmount.setHTML("&nbsp;&nbsp;" + clientFactory.getCoreMessages().amendmentSelection(event.getSelected().size()));
            }
        });
    }

    /**
     * Set the document controller and register the selections and actions
     * @param documentController The document controller
     */
    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
        registerListeners();
        // register the selections
        registerSelections();
        // register the actions
        registerActions();
    }
}
