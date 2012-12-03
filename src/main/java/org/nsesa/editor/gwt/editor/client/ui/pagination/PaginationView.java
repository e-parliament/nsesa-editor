package org.nsesa.editor.gwt.editor.client.ui.pagination;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Created with IntelliJ IDEA.
 * User: groza
 * Date: 28/11/12
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
@ImplementedBy(PaginationViewImpl.class)
public interface PaginationView extends IsWidget {
    void setStyleName(String styleName);
    void displayCurrentPage(int currentPage, int totalPages);
    HasClickHandlers getFirst();
    HasClickHandlers getLast();
    HasClickHandlers getNext();
    HasClickHandlers getPrevious();
}
