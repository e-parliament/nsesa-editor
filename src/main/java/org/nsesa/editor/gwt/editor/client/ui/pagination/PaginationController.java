package org.nsesa.editor.gwt.editor.client.ui.pagination;

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
import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

import org.nsesa.editor.gwt.editor.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentRefreshRequestEventHandler;
import org.nsesa.editor.gwt.editor.client.event.filter.FilterRequestEvent;
import org.nsesa.editor.gwt.editor.client.event.filter.FilterResponseEvent;
import org.nsesa.editor.gwt.editor.client.event.filter.FilterResponseEventHandler;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

/**
 * The controller for filter
 * User: groza
 * Date: 30/11/12
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
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
                totalPages = Math.round((float)event.getTotalSize()/currentFilter.getSize());
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
        currentFilter.setStart((currentPage - 1)* currentFilter.getSize());
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
