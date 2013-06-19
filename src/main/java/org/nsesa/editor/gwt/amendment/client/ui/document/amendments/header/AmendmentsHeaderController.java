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
package org.nsesa.editor.gwt.amendment.client.ui.document.amendments.header;

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
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerDeleteEvent;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerStatusUpdatedEvent;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.ConfirmationEvent;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.NotificationEvent;
import org.nsesa.editor.gwt.core.client.event.selection.OverlayWidgetAwareSelectedEvent;
import org.nsesa.editor.gwt.core.client.event.selection.OverlayWidgetAwareSelectedEventHandler;
import org.nsesa.editor.gwt.core.client.event.selection.OverlayWidgetAwareSelectionActionEvent;
import org.nsesa.editor.gwt.core.client.event.selection.OverlayWidgetAwareSelectionEvent;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.i18n.CoreMessages;
import org.nsesa.editor.gwt.core.client.util.Counter;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.ClientContext;

import java.util.ArrayList;
import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * <code>AmendmentsHeaderController</code> class is responsible to set up the selections and the actions available in
 * {@link org.nsesa.editor.gwt.amendment.client.ui.document.amendments.filter.AmendmentsFilterView} view.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 26/11/12 11:50
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentsHeaderController {
    /**
     * The view associated to the controller
     */
    private final AmendmentsHeaderView view;

    private final CoreMessages coreMessages;

    /**
     * the event bus to raise GWT events
     */
    private final DocumentEventBus documentEventBus;
    /**
     * Used to get reference to {@link ServiceFactory} when user request a given action over selected amendments
     */
    private DocumentController documentController;

    /**
     * Stores the number of amendments after applying a {@link Selection}
     */
    private final InlineHTML selectedAmount = new InlineHTML();
    private Anchor selectAll;
    private Anchor selectNone;
    private Anchor selectCandidate;
    private Anchor selectTabled;
    private Anchor selectWithdrawn;
    private Button tableButton;

    private Button withdrawButton;
    private Button deleteButton;

    private HandlerRegistration amendmentControllerSelectedEventHandlerRegistration;
    private com.google.gwt.event.shared.HandlerRegistration selectAllHandlerRegistration;
    private com.google.gwt.event.shared.HandlerRegistration selectNoneHandlerRegistration;
    private com.google.gwt.event.shared.HandlerRegistration selectCandidateHandlerRegistration;
    private com.google.gwt.event.shared.HandlerRegistration selectTabledHandlerRegistration;
    private com.google.gwt.event.shared.HandlerRegistration selectWithdrawnHandlerRegistration;
    private com.google.gwt.event.shared.HandlerRegistration tableClickButtonHandlerRegistration;
    private com.google.gwt.event.shared.HandlerRegistration withdrawButtonClickHandlerRegistration;
    private com.google.gwt.event.shared.HandlerRegistration deleteButtonClickHandlerRegistration;

    /**
     * Create <code>AmendmentsHeaderController</code> with the given properties
     *
     * @param view             The view associated
     * @param documentEventBus The document event bus
     */
    @Inject
    public AmendmentsHeaderController(final AmendmentsHeaderView view,
                                      final DocumentEventBus documentEventBus,
                                      final CoreMessages coreMessages

    ) {
        this.view = view;
        this.documentEventBus = documentEventBus;
        this.coreMessages = coreMessages;

        registerSelections();

        registerActions();

        registerListeners();
    }

    /**
     * Returns the view associated to the controller
     *
     * @return The view
     */
    public AmendmentsHeaderView getView() {
        return view;
    }

    /**
     * Register actions that could be performed by the user in the actions area. Three actions are available as
     * default: table, withdraw and delete. When the user performs the actions from the view for each type of action
     */
    private void registerActions() {
        tableButton = new Button(coreMessages.amendmentActionTable());
        view.addAction(tableButton);
        withdrawButton = new Button(coreMessages.amendmentActionWithdraw());
        view.addAction(withdrawButton);
        deleteButton = new Button(coreMessages.amendmentActionDelete());
        view.addAction(deleteButton);
    }

    /**
     * Returns the DTO models associated to amendment controllers
     *
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
    private void registerSelections() {
        selectAll = new Anchor(coreMessages.amendmentSelectionAll());
        view.addSelection(selectAll);
        view.addSelection(new InlineHTML(", "));
        selectNone = new Anchor(coreMessages.amendmentSelectionNone());
        view.addSelection(selectNone);
        view.addSelection(new InlineHTML("&nbsp;-&nbsp;"));
        selectCandidate = new Anchor(coreMessages.amendmentStatusCandidate());
        view.addSelection(selectCandidate);
        view.addSelection(new InlineHTML(", "));
        selectTabled = new Anchor(coreMessages.amendmentStatusTabled());
        view.addSelection(selectTabled);
        view.addSelection(new InlineHTML(", "));
        selectWithdrawn = new Anchor(coreMessages.amendmentStatusWithdrawn());
        view.addSelection(selectWithdrawn);
        view.addSelection(selectedAmount);
    }

    /**
     * Refresh the number of selected amendments by handling {@link org.nsesa.editor.gwt.core.client.event.selection.OverlayWidgetAwareSelectedEvent} event
     */
    private void registerListeners() {
        amendmentControllerSelectedEventHandlerRegistration = documentEventBus.addHandler(OverlayWidgetAwareSelectedEvent.TYPE, new OverlayWidgetAwareSelectedEventHandler() {
            @Override
            public void onEvent(OverlayWidgetAwareSelectedEvent event) {
                selectedAmount.setHTML("&nbsp;&nbsp;" + documentController.getClientFactory().getCoreMessages().amendmentSelection(event.getSelected().size()));
            }
        });

        selectAllHandlerRegistration = selectAll.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new OverlayWidgetAwareSelectionEvent(new Selection.AllSelection<AmendmentController>()));
            }
        });

        selectNoneHandlerRegistration = selectNone.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new OverlayWidgetAwareSelectionEvent(new Selection.NoneSelection<AmendmentController>()));
            }
        });


        selectCandidateHandlerRegistration = selectCandidate.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new OverlayWidgetAwareSelectionEvent(new Selection<AmendmentController>() {
                    @Override
                    public boolean select(AmendmentController amendmentController) {
                        return "candidate".equalsIgnoreCase(amendmentController.getModel().getAmendmentContainerStatus());
                    }
                }));
            }
        });


        selectTabledHandlerRegistration = selectTabled.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new OverlayWidgetAwareSelectionEvent(new Selection<AmendmentController>() {
                    @Override
                    public boolean select(AmendmentController amendmentController) {
                        return "tabled".equalsIgnoreCase(amendmentController.getModel().getAmendmentContainerStatus());
                    }
                }));
            }
        });


        selectWithdrawnHandlerRegistration = selectWithdrawn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new OverlayWidgetAwareSelectionEvent(new Selection<AmendmentController>() {
                    @Override
                    public boolean select(AmendmentController amendmentController) {
                        return "withdrawn".equalsIgnoreCase(amendmentController.getModel().getAmendmentContainerStatus());
                    }
                }));
            }
        });

        tableClickButtonHandlerRegistration = tableButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new OverlayWidgetAwareSelectionActionEvent<AmendmentController>(new OverlayWidgetAwareSelectionActionEvent.Action<AmendmentController>() {
                    @Override
                    public void execute(final List<AmendmentController> amendmentControllers) {

                        // validate the selection to see if we can actually table these amendments
                        final ClientContext clientContext = documentController.getClientFactory().getClientContext();
                        final ArrayList<AmendmentContainerDTO> amendmentContainers = new ArrayList<AmendmentContainerDTO>(Collections2.transform(amendmentControllers, new Function<AmendmentController, AmendmentContainerDTO>() {
                            @Override
                            public AmendmentContainerDTO apply(final AmendmentController input) {
                                return input.getModel();
                            }
                        }));

                        documentController.getServiceFactory().getGwtAmendmentService().canTableAmendmentContainers(clientContext, amendmentContainers, new AsyncCallback<Boolean[]>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                documentController.getClientFactory().getEventBus().fireEvent(new CriticalErrorEvent("Could not validate table request.", caught));
                            }

                            @Override
                            public void onSuccess(Boolean[] result) {
                                // now we check to see if they all can be deleted or not.
                                // if not, propose to remove the guilty one from the selection, and try again
                                final List<AmendmentController> toRemoveFromSelection = new ArrayList<AmendmentController>();
                                Counter counter = new Counter();
                                for (final Boolean b : result) {
                                    if (b != null && !b) {
                                        // damn, we're not allowed to withdraw this one
                                        final AmendmentController amendmentController = amendmentControllers.get(counter.get());
                                        toRemoveFromSelection.add(amendmentController);
                                    }
                                    counter.increment();
                                }

                                // ok, we're done filtering out the ones that cannot be tabled
                                if (!toRemoveFromSelection.isEmpty()) {
                                    // propose to remove the ones from the selection
                                    documentEventBus.fireEvent(new ConfirmationEvent(
                                            "Remove amendments from selection?", "It looks like not all the selected amendments can be tabled - would you like to remove them from the selection?", "Remove from selection",
                                            new ClickHandler() {
                                                @Override
                                                public void onClick(ClickEvent event) {
                                                    amendmentControllers.removeAll(toRemoveFromSelection);
                                                    // make a copy, because the original list will be modified
                                                    final List<AmendmentController> copy = new ArrayList<AmendmentController>(amendmentControllers);
                                                    documentEventBus.fireEvent(new OverlayWidgetAwareSelectionEvent(new Selection<AmendmentController>() {
                                                        @Override
                                                        public boolean select(AmendmentController amendmentController) {
                                                            return copy.contains(amendmentController);
                                                        }
                                                    }));
                                                    tableAmendmentControllers(amendmentControllers);
                                                }
                                            },
                                            coreMessages.amendmentActionCancel(),
                                            new ClickHandler() {
                                                @Override
                                                public void onClick(ClickEvent event) {
                                                    // don't do anything special
                                                }
                                            }
                                    ));
                                } else {
                                    tableAmendmentControllers(amendmentControllers);
                                }
                            }
                        });
                    }
                }));
            }
        });

        withdrawButtonClickHandlerRegistration = withdrawButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new OverlayWidgetAwareSelectionActionEvent<AmendmentController>(new OverlayWidgetAwareSelectionActionEvent.Action<AmendmentController>() {
                    @Override
                    public void execute(final List<AmendmentController> amendmentControllers) {

                        // validate the selection to see if we can actually withdraw these amendments
                        final ClientContext clientContext = documentController.getClientFactory().getClientContext();
                        final ArrayList<AmendmentContainerDTO> amendmentContainers = new ArrayList<AmendmentContainerDTO>(Collections2.transform(amendmentControllers, new Function<AmendmentController, AmendmentContainerDTO>() {
                            @Override
                            public AmendmentContainerDTO apply(final AmendmentController input) {
                                return input.getModel();
                            }
                        }));

                        documentController.getServiceFactory().getGwtAmendmentService().canWithdrawAmendmentContainers(clientContext, amendmentContainers, new AsyncCallback<Boolean[]>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                documentController.getClientFactory().getEventBus().fireEvent(new CriticalErrorEvent("Could not validate withdraw request.", caught));
                            }

                            @Override
                            public void onSuccess(Boolean[] result) {
                                // now we check to see if they all can be deleted or not.
                                // if not, propose to remove the guilty one from the selection, and try again
                                final List<AmendmentController> toRemoveFromSelection = new ArrayList<AmendmentController>();
                                Counter counter = new Counter();
                                for (final Boolean b : result) {
                                    if (b != null && !b) {
                                        // damn, we're not allowed to withdraw this one
                                        final AmendmentController amendmentController = amendmentControllers.get(counter.get());
                                        toRemoveFromSelection.add(amendmentController);
                                    }
                                    counter.increment();
                                }

                                // ok, we're done filtering out the ones that cannot be withdrawn
                                if (!toRemoveFromSelection.isEmpty()) {
                                    // propose to remove the ones from the selection
                                    documentEventBus.fireEvent(new ConfirmationEvent(
                                            "Remove amendments from selection?", "It looks like not all the selected amendments can be withdrawn - would you like to remove them from the selection?", "Remove from selection",
                                            new ClickHandler() {
                                                @Override
                                                public void onClick(ClickEvent event) {
                                                    amendmentControllers.removeAll(toRemoveFromSelection);
                                                    // make a copy, because the original list will be modified
                                                    final List<AmendmentController> copy = new ArrayList<AmendmentController>(amendmentControllers);
                                                    documentEventBus.fireEvent(new OverlayWidgetAwareSelectionEvent(new Selection<AmendmentController>() {
                                                        @Override
                                                        public boolean select(AmendmentController amendmentController) {
                                                            return copy.contains(amendmentController);
                                                        }
                                                    }));
                                                    withdrawnAmendmentControllers(amendmentControllers);
                                                }
                                            },
                                            coreMessages.amendmentActionCancel(),
                                            new ClickHandler() {
                                                @Override
                                                public void onClick(ClickEvent event) {
                                                    // don't do anything special
                                                }
                                            }
                                    ));

                                } else {
                                    withdrawnAmendmentControllers(amendmentControllers);
                                }
                            }
                        });
                    }
                }));
            }
        });

        deleteButtonClickHandlerRegistration = deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentEventBus.fireEvent(new OverlayWidgetAwareSelectionActionEvent<AmendmentController>(new OverlayWidgetAwareSelectionActionEvent.Action<AmendmentController>() {
                    @Override
                    public void execute(final List<AmendmentController> amendmentControllers) {

                        // validate the selection to see if we can actually delete these amendments
                        final ClientContext clientContext = documentController.getClientFactory().getClientContext();
                        final ArrayList<AmendmentContainerDTO> amendmentContainers = new ArrayList<AmendmentContainerDTO>(Collections2.transform(amendmentControllers, new Function<AmendmentController, AmendmentContainerDTO>() {
                            @Override
                            public AmendmentContainerDTO apply(final AmendmentController input) {
                                return input.getModel();
                            }
                        }));

                        documentController.getServiceFactory().getGwtAmendmentService().canDeleteAmendmentContainers(clientContext, amendmentContainers, new AsyncCallback<Boolean[]>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                documentController.getClientFactory().getEventBus().fireEvent(new CriticalErrorEvent("Could not validate delete request.", caught));
                            }

                            @Override
                            public void onSuccess(Boolean[] result) {
                                // now we check to see if they all can be deleted or not.
                                // if not, propose to remove the guilty one from the selection, and try again
                                final List<AmendmentController> toRemoveFromSelection = new ArrayList<AmendmentController>();
                                Counter counter = new Counter();
                                for (final Boolean b : result) {
                                    if (b != null && !b) {
                                        // damn, we're not allowed to withdraw this one
                                        final AmendmentController amendmentController = amendmentControllers.get(counter.get());
                                        toRemoveFromSelection.add(amendmentController);
                                    }
                                    counter.increment();
                                }

                                // ok, we're done filtering out the ones that cannot be withdrawn
                                if (!toRemoveFromSelection.isEmpty()) {
                                    // propose to remove the ones from the selection
                                    documentEventBus.fireEvent(new ConfirmationEvent(
                                            "Remove amendments from selection?", "It looks like not all the selected amendments can be deleted - would you like to remove them from the selection?", "Remove from selection",
                                            new ClickHandler() {
                                                @Override
                                                public void onClick(ClickEvent event) {
                                                    amendmentControllers.removeAll(toRemoveFromSelection);
                                                    // make a copy, because the original list will be modified
                                                    final List<AmendmentController> copy = new ArrayList<AmendmentController>(amendmentControllers);
                                                    documentEventBus.fireEvent(new OverlayWidgetAwareSelectionEvent(new Selection<AmendmentController>() {
                                                        @Override
                                                        public boolean select(AmendmentController amendmentController) {
                                                            return copy.contains(amendmentController);
                                                        }
                                                    }));
                                                    deleteAmendmentControllers(amendmentControllers);
                                                }
                                            },
                                            coreMessages.amendmentActionCancel(),
                                            new ClickHandler() {
                                                @Override
                                                public void onClick(ClickEvent event) {
                                                    // don't do anything special
                                                }
                                            }
                                    ));

                                } else {
                                    deleteAmendmentControllers(amendmentControllers);
                                }
                            }
                        });
                    }
                }));
            }
        });
    }

    private void deleteAmendmentControllers(final List<AmendmentController> amendmentControllers) {
        // if there are any remaining controllers, let's withdraw them after a confirmation
        if (!amendmentControllers.isEmpty()) {
            documentEventBus.fireEvent(new ConfirmationEvent(
                    coreMessages.confirmationAmendmentDeleteTitle(), coreMessages.confirmationAmendmentDeleteMessage(), coreMessages.amendmentActionDelete(),
                    new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            documentEventBus.fireEvent(new OverlayWidgetAwareSelectionActionEvent<AmendmentController>(new OverlayWidgetAwareSelectionActionEvent.Action<AmendmentController>() {
                                @Override
                                public void execute(final List<AmendmentController> amendmentControllers) {
                                    if (!amendmentControllers.isEmpty()) {
                                        documentEventBus.fireEvent(new AmendmentContainerDeleteEvent(amendmentControllers.toArray(new AmendmentController[amendmentControllers.size()])));
                                    }
                                }
                            }));
                        }
                    },
                    coreMessages.amendmentActionCancel(),
                    new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            // don't do anything special
                        }
                    }
            ));
        }
    }

    private void withdrawnAmendmentControllers(final List<AmendmentController> amendmentControllers) {
        // if there are any remaining controllers, let's withdraw them after a confirmation
        if (!amendmentControllers.isEmpty()) {
            documentEventBus.fireEvent(new ConfirmationEvent(
                    coreMessages.confirmationAmendmentWithdrawTitle(), coreMessages.confirmationAmendmentWithdrawMessage(), coreMessages.amendmentActionWithdraw(),
                    new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            documentController.getServiceFactory().getGwtAmendmentService().withdrawAmendmentContainers(documentController.getClientFactory().getClientContext(),
                                    transformToDTOs(amendmentControllers),
                                    new AsyncCallback<AmendmentContainerDTO[]>() {
                                        @Override
                                        public void onFailure(Throwable caught) {
                                            documentController.getClientFactory().getEventBus().fireEvent(new CriticalErrorEvent("Could not withdraw amendment(s).", caught));
                                        }

                                        @Override
                                        public void onSuccess(AmendmentContainerDTO[] result) {
                                            int index = 0;
                                            for (final AmendmentContainerDTO dto : result) {
                                                final AmendmentController amendmentController = amendmentControllers.get(index);
                                                final String oldStatus = amendmentController.getModel().getAmendmentContainerStatus();
                                                amendmentController.mergeModel(dto, true);
                                                documentEventBus.fireEvent(new AmendmentContainerStatusUpdatedEvent(amendmentController, oldStatus));
                                                index++;
                                            }
                                            documentEventBus.fireEvent(new NotificationEvent(coreMessages.amendmentActionWithdrawSuccessful(result.length)));
                                        }
                                    });
                        }
                    },
                    coreMessages.amendmentActionCancel(),
                    new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            // don't do anything special
                        }
                    }
            ));
        }
    }

    private void tableAmendmentControllers(final List<AmendmentController> amendmentControllers) {
        // if there are any remaining controllers, let's table them
        if (!amendmentControllers.isEmpty()) {
            documentEventBus.fireEvent(new ConfirmationEvent(
                    coreMessages.confirmationAmendmentTableTitle(), coreMessages.confirmationAmendmentTableMessage(), coreMessages.amendmentActionTable(),
                    new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            documentController.getServiceFactory().getGwtAmendmentService().tableAmendmentContainers(documentController.getClientFactory().getClientContext(),
                                    transformToDTOs(amendmentControllers),
                                    new AsyncCallback<AmendmentContainerDTO[]>() {
                                        @Override
                                        public void onFailure(Throwable caught) {
                                            documentController.getClientFactory().getEventBus().fireEvent(new CriticalErrorEvent("Could not table amendment(s).", caught));
                                        }

                                        @Override
                                        public void onSuccess(AmendmentContainerDTO[] result) {
                                            int index = 0;
                                            for (final AmendmentContainerDTO dto : result) {
                                                final AmendmentController amendmentController = amendmentControllers.get(index);
                                                final String oldStatus = amendmentController.getModel().getAmendmentContainerStatus();
                                                amendmentController.mergeModel(dto, true);
                                                documentEventBus.fireEvent(new AmendmentContainerStatusUpdatedEvent(amendmentController, oldStatus));
                                                index++;
                                            }
                                            documentEventBus.fireEvent(new NotificationEvent(coreMessages.amendmentActionWithdrawSuccessful(result.length)));
                                        }
                                    });
                        }
                    },
                    coreMessages.amendmentActionCancel(),
                    new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            // don't do anything special
                        }
                    }
            ));
        }
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        amendmentControllerSelectedEventHandlerRegistration.removeHandler();
        selectAllHandlerRegistration.removeHandler();
        selectNoneHandlerRegistration.removeHandler();
        selectCandidateHandlerRegistration.removeHandler();
        selectTabledHandlerRegistration.removeHandler();
        selectWithdrawnHandlerRegistration.removeHandler();
        tableClickButtonHandlerRegistration.removeHandler();
        withdrawButtonClickHandlerRegistration.removeHandler();
        deleteButtonClickHandlerRegistration.removeHandler();
    }

    /**
     * Set the document controller and register the selections and actions
     *
     * @param documentController The document controller
     */
    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
        this.selectedAmount.setHTML("&nbsp;&nbsp;" + documentController.getClientFactory().getCoreMessages().amendmentSelection(0));

    }
}
