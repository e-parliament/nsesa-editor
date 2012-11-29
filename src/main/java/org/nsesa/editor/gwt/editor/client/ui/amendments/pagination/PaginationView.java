package org.nsesa.editor.gwt.editor.client.ui.amendments.pagination;

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
    void setCallback(PaginationCallback callback);
    void setTotalPages(int totalPages);
    int getCurrentPage();
}
