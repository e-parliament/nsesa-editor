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
package org.nsesa.editor.gwt.amendment.client.ui.document.amendments;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.amendment.client.event.amendment.*;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.amendment.client.ui.document.AmendmentDocumentController;
import org.nsesa.editor.gwt.core.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentRefreshRequestEventHandler;
import org.nsesa.editor.gwt.core.client.event.filter.FilterRequestEvent;
import org.nsesa.editor.gwt.core.client.event.filter.FilterRequestEventHandler;
import org.nsesa.editor.gwt.core.client.event.filter.FilterResponseEvent;
import org.nsesa.editor.gwt.core.client.event.selection.OverlayWidgetAwareSelectedEvent;
import org.nsesa.editor.gwt.core.client.event.selection.OverlayWidgetAwareSelectedEventHandler;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.util.Filter;
import org.nsesa.editor.gwt.core.client.util.FilterResponse;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.util.Selection;

import java.util.*;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * <code>AmendmentsPanelController</code> class is responsible to control set up the selections and the actions available in
 * {@link org.nsesa.editor.gwt.amendment.client.ui.document.amendments.filter.AmendmentsFilterView} view.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 26/11/12 11:50
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentsPanelController {

    private AmendmentsPanelView view;
    private DocumentEventBus documentEventBus;
    /**
     * Stores hoy many amendments will be displayed per page
     */
    private int amendmentsPerPage;
    private AmendmentDocumentController documentController;
    private Filter<AmendmentController> currentFilter;

    private Selection<AmendmentController> DEFAULT_SELECTION = new Selection.AllSelection<AmendmentController>();
    private HandlerRegistration documentRefreshRequestEventHandlerRegistration;
    private HandlerRegistration amendmentContainerInjectedEventHandlerRegistration;
    private HandlerRegistration amendmentContainerSkippedEventHandlerRegistration;
    private HandlerRegistration amendmentContainerDeletedEventHandlerRegistration;
    private HandlerRegistration amendmentContainerSavedEventHandlerRegistration;
    private HandlerRegistration amendmentContainerStatusUpdatedEventHandlerRegistration;
    private HandlerRegistration filterRequestEventHandlerRegistration;
    private HandlerRegistration amendmentControllerSelectedEventHandlerRegistration;

    /**
     * Create <code>AmendmentsPanelController</code> object with the given properties
     *
     * @param view             The view associated to controller
     * @param documentEventBus The event bus associated to controller
     */
    @Inject
    public AmendmentsPanelController(AmendmentsPanelView view,
                                     DocumentEventBus documentEventBus,
                                     @Named("amendmentsPerPage") int amendmentsPerPage) {
        this.view = view;
        this.documentEventBus = documentEventBus;
        this.amendmentsPerPage = amendmentsPerPage;
        this.currentFilter = new Filter<AmendmentController>(0, amendmentsPerPage,
                AmendmentController.ORDER_COMPARATOR, DEFAULT_SELECTION);
    }

    /**
     * Returns the view associated to controller
     *
     * @return the view
     */
    public AmendmentsPanelView getView() {
        return view;
    }

    /**
     * Refresh the amendments view whenever the user add/modify amendments or change the current filter
     */
    public void registerListeners() {
        documentRefreshRequestEventHandlerRegistration = documentEventBus.addHandler(DocumentRefreshRequestEvent.TYPE, new DocumentRefreshRequestEventHandler() {
            @Override
            public void onEvent(DocumentRefreshRequestEvent event) {
                refreshAmendments();
            }
        });

        amendmentContainerInjectedEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerInjectedEvent.TYPE, new AmendmentContainerInjectedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerInjectedEvent event) {
                refreshAmendments();
            }
        });

        amendmentContainerSkippedEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerSkippedEvent.TYPE, new AmendmentContainerSkippedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerSkippedEvent event) {
                refreshAmendments();
            }
        });

        amendmentContainerDeletedEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerDeletedEvent.TYPE, new AmendmentContainerDeletedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerDeletedEvent event) {
                refreshAmendments();
            }
        });

        amendmentContainerSavedEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerSavedEvent.TYPE, new AmendmentContainerSavedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerSavedEvent event) {
                refreshAmendments();
            }
        });
        amendmentContainerStatusUpdatedEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerStatusUpdatedEvent.TYPE, new AmendmentContainerStatusUpdatedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerStatusUpdatedEvent event) {
                refreshAmendments();
            }
        });
        filterRequestEventHandlerRegistration = documentEventBus.addHandler(FilterRequestEvent.TYPE, new FilterRequestEventHandler() {
            @Override
            public void onEvent(FilterRequestEvent event) {
                currentFilter = event.getFilter();
                refreshAmendments();
            }
        });
        amendmentControllerSelectedEventHandlerRegistration = documentEventBus.addHandler(OverlayWidgetAwareSelectedEvent.TYPE, new OverlayWidgetAwareSelectedEventHandler() {
            @Override
            public void onEvent(OverlayWidgetAwareSelectedEvent event) {
                // we're sure this cast will work ...
                final List<AmendmentController> selected = (List<AmendmentController>) event.getSelected();
                final Collection<String> ids = Collections2.transform(selected, new Function<AmendmentController, String>() {
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
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        documentRefreshRequestEventHandlerRegistration.removeHandler();
        amendmentContainerInjectedEventHandlerRegistration.removeHandler();
        amendmentContainerSkippedEventHandlerRegistration.removeHandler();
        amendmentContainerDeletedEventHandlerRegistration.removeHandler();
        amendmentContainerSavedEventHandlerRegistration.removeHandler();
        amendmentContainerStatusUpdatedEventHandlerRegistration.removeHandler();
        filterRequestEventHandlerRegistration.removeHandler();
        amendmentControllerSelectedEventHandlerRegistration.removeHandler();
    }

    /**
     * Set the document controller associated to this <code>AmendmentsPanelController</code>
     *
     * @param documentController the document controller
     */
    public void setDocumentController(final AmendmentDocumentController documentController) {
        this.documentController = documentController;
    }

    /**
     * Create an initial {@link Filter} if not exists and filter the amendments based on it
     */
    private void refreshAmendments() {
        if (currentFilter == null) {
            currentFilter = new Filter<AmendmentController>(0, amendmentsPerPage,
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

            view.selectAmendmentControllers(new ArrayList<String>(Collections2.transform(documentController.getSelector().getSelected(), new Function<AmendmentController, String>() {
                @Override
                public String apply(AmendmentController input) {
                    return input.getModel().getId();
                }
            })));
        }
    }
}

