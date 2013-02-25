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
package org.nsesa.editor.gwt.core.client.ui.pagination;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEventHandler;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerStatusUpdatedEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerStatusUpdatedEventHandler;
import org.nsesa.editor.gwt.core.client.util.Filter;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentRefreshRequestEventHandler;
import org.nsesa.editor.gwt.core.client.event.filter.FilterRequestEvent;
import org.nsesa.editor.gwt.core.client.event.filter.FilterResponseEvent;
import org.nsesa.editor.gwt.core.client.event.filter.FilterResponseEventHandler;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * The controller for pagination functionality
 * User: groza
 * Date: 30/11/12
 * Time: 15:29
 */
@Scope(DOCUMENT)
@Singleton
public class PaginationController {
    private int currentPage = 1;
    private int totalPages = 1;

    private DocumentEventBus documentEventBus;
    private PaginationView paginationView;

    private Filter currentFilter;

    @Inject
    public PaginationController(DocumentEventBus documentEventBus, PaginationView paginationView) {
        this.documentEventBus = documentEventBus;
        this.paginationView = paginationView;
        registerListeners();
    }

    public PaginationView getPaginationView() {
        return paginationView;
    }

    private void registerListeners() {
        documentEventBus.addHandler(DocumentRefreshRequestEvent.TYPE, new DocumentRefreshRequestEventHandler() {
            @Override
            public void onEvent(DocumentRefreshRequestEvent event) {
                resetPage();
            }
        });

        documentEventBus.addHandler(AmendmentContainerInjectedEvent.TYPE, new AmendmentContainerInjectedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerInjectedEvent event) {
                resetPage();
            }
        });

        documentEventBus.addHandler(AmendmentContainerStatusUpdatedEvent.TYPE, new AmendmentContainerStatusUpdatedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerStatusUpdatedEvent event) {
                resetPage();
            }
        });
        documentEventBus.addHandler(AmendmentContainerStatusUpdatedEvent.TYPE, new AmendmentContainerStatusUpdatedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerStatusUpdatedEvent event) {
                resetPage();
            }
        });

        documentEventBus.addHandler(FilterResponseEvent.TYPE, new FilterResponseEventHandler() {
            @Override
            public void onEvent(FilterResponseEvent event) {
                currentFilter = event.getFilter();
                currentPage = currentFilter.getStart() / currentFilter.getSize() + 1;
                totalPages = (int) Math.ceil((double) event.getTotalSize() / currentFilter.getSize());
                if (totalPages == 0) totalPages = 1;
                if (currentPage > totalPages) currentPage = totalPages;
                displayCurrentPage();
            }
        });

        HasClickHandlers first = paginationView.getFirst();
        first.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                currentPage = 1;
                moveTo();
            }
        });
        HasClickHandlers next = paginationView.getNext();
        next.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                currentPage++;
                moveTo();
            }
        });
        HasClickHandlers previous = paginationView.getPrevious();
        previous.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                currentPage--;
                moveTo();
            }
        });
        HasClickHandlers last = paginationView.getLast();
        last.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                currentPage = totalPages;
                moveTo();
            }
        });
    }

    private void moveTo() {
        if (currentPage <= 0) {
            currentPage = 1;
        } else if (currentPage >= totalPages) {
            currentPage = totalPages;
        }
        //trigger filter event
        currentFilter.setStart((currentPage - 1) * currentFilter.getSize());
        displayCurrentPage();
        documentEventBus.fireEvent(new FilterRequestEvent(currentFilter));
    }

    private void resetPage() {
        currentPage = 1;
        displayCurrentPage();
    }

    public void displayCurrentPage() {
        paginationView.displayCurrentPage(currentPage, totalPages);
    }

}
