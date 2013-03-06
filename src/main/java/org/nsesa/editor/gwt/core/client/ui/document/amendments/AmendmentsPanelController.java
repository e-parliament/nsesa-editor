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
package org.nsesa.editor.gwt.core.client.ui.document.amendments;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.event.amendment.*;
import org.nsesa.editor.gwt.core.client.event.amendments.AmendmentControllerSelectedEvent;
import org.nsesa.editor.gwt.core.client.event.amendments.AmendmentControllerSelectedEventHandler;
import org.nsesa.editor.gwt.core.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentRefreshRequestEventHandler;
import org.nsesa.editor.gwt.core.client.event.filter.FilterRequestEvent;
import org.nsesa.editor.gwt.core.client.event.filter.FilterRequestEventHandler;
import org.nsesa.editor.gwt.core.client.event.filter.FilterResponseEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.util.Filter;
import org.nsesa.editor.gwt.core.client.util.FilterResponse;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.util.Selection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * <code>AmendmentsPanelController</code> class is responsible to control set up the selections and the actions available in
 * {@link org.nsesa.editor.gwt.core.client.ui.document.amendments.filter.AmendmentsFilterView} view.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 26/11/12 11:50
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentsPanelController {
    /**
     * Stores hoy many amendments will be displayed per page
     */
    private static final int AMENDMENTS_PER_PAGE = 2;

    private AmendmentsPanelView view;
    private DocumentEventBus documentEventBus;
    private DocumentController documentController;
    private Filter<AmendmentController> currentFilter;

    private Selection<AmendmentController> DEFAULT_SELECTION = new Selection.AllSelection<AmendmentController>();

    /**
     * Create <code>AmendmentsPanelController</code> object with the given properties
     * @param view The view associated to controller
     * @param documentEventBus The event bus associated to controller
     */
    @Inject
    public AmendmentsPanelController(AmendmentsPanelView view,
                                     DocumentEventBus documentEventBus) {
        this.view = view;
        this.documentEventBus = documentEventBus;
        this.currentFilter = new Filter<AmendmentController>(0, AMENDMENTS_PER_PAGE,
                AmendmentController.ORDER_COMPARATOR, DEFAULT_SELECTION);
        registerListeners();
    }

    /**
     * Returns the view associated to controller
     * @return the view
     */
    public AmendmentsPanelView getView() {
        return view;
    }

    /**
     * Refresh the amendments view whenever the user add/modify amendments or change the current filter
     *
     */
    private void registerListeners() {
        documentEventBus.addHandler(DocumentRefreshRequestEvent.TYPE, new DocumentRefreshRequestEventHandler() {
            @Override
            public void onEvent(DocumentRefreshRequestEvent event) {
                refreshAmendments();
            }
        });

        documentEventBus.addHandler(AmendmentContainerInjectedEvent.TYPE, new AmendmentContainerInjectedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerInjectedEvent event) {
                refreshAmendments();
            }
        });

        documentEventBus.addHandler(AmendmentContainerSkippedEvent.TYPE, new AmendmentContainerSkippedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerSkippedEvent event) {
                refreshAmendments();
            }
        });

        documentEventBus.addHandler(AmendmentContainerDeletedEvent.TYPE, new AmendmentContainerDeletedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerDeletedEvent event) {
                refreshAmendments();
            }
        });

        documentEventBus.addHandler(AmendmentContainerStatusUpdatedEvent.TYPE, new AmendmentContainerStatusUpdatedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerStatusUpdatedEvent event) {
                refreshAmendments();
            }
        });
        documentEventBus.addHandler(AmendmentContainerStatusUpdatedEvent.TYPE, new AmendmentContainerStatusUpdatedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerStatusUpdatedEvent event) {
                refreshAmendments();
            }
        });
        documentEventBus.addHandler(FilterRequestEvent.TYPE, new FilterRequestEventHandler() {
            @Override
            public void onEvent(FilterRequestEvent event) {
                currentFilter = event.getFilter();
                filterAmendments();
            }
        });
        documentEventBus.addHandler(AmendmentControllerSelectedEvent.TYPE, new AmendmentControllerSelectedEventHandler() {
            @Override
            public void onEvent(AmendmentControllerSelectedEvent event) {
                final Collection<String> ids = Collections2.transform(event.getSelected(), new Function<AmendmentController, String>() {
                    @Override
                    public String apply(final AmendmentController input) {
                        return input.getModel().getId();
                    }
                });
                view.selectAmendmentControllers(new ArrayList<String>(ids));
            }
        });

    }

    /**
     * Set the document controller associated to this <code>AmendmentsPanelController</code>
     * @param documentController the document controller
     */
    public void setDocumentController(final DocumentController documentController) {
        this.documentController = documentController;
    }

    /**
     * Create an initial {@link Filter} if not exists and filter the amendments based on it
     */
    private void refreshAmendments() {
        if (currentFilter == null) {
            currentFilter = new Filter<AmendmentController>(0, AMENDMENTS_PER_PAGE,
                    AmendmentController.ORDER_COMPARATOR,
                    DEFAULT_SELECTION);

        }
        filterAmendments();
    }

    /**
     * Filter the amendments based on the current filter and refresh the view. Keep also the state of the selection
     * for the amendments selected before
     */
    private void filterAmendments() {
        final Map<String, AmendmentController> amendments = new LinkedHashMap<String, AmendmentController>();
        final FilterResponse<AmendmentController> response = documentController.getAmendmentManager().getAmendmentControllers(currentFilter);
        if (response.getResult().size() == 0 && currentFilter.getStart() != 0) {
            //fire a new event to go to the first page when they are no results to be displayed in the current page
            currentFilter.setStart(0);
            documentEventBus.fireEvent(new FilterRequestEvent(currentFilter));
        } else {
            for (final AmendmentController amendmentController : response.getResult()) {
                amendments.put(amendmentController.getModel().getId(), amendmentController);
            }
            view.refreshAmendmentControllers(amendments);
            // fire a filter response
            documentEventBus.fireEvent(new FilterResponseEvent(response.getTotalSize(), currentFilter));

            view.selectAmendmentControllers(new ArrayList<String>(Collections2.transform(documentController.getSelectedAmendmentControllers(), new Function<AmendmentController, String>() {
                @Override
                public String apply(AmendmentController input) {
                    return input.getModel().getId();
                }
            })));
        }
    }
}

