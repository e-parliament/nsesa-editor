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
package org.nsesa.editor.gwt.core.client.ui.pagination;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * <code>PaginationView</code> view offers general pagination support functionality.
 * Specifically it is used in the second tabbed panel of the application
 * {@link org.nsesa.editor.gwt.amendment.client.ui.document.amendments.AmendmentsPanelView} where the
 * existing amendments are displayed in a "pagination" way.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 28/11/12 15:37
 */
@ImplementedBy(PaginationViewImpl.class)
public interface PaginationView extends IsWidget {
    /**
     * Set the style name of the view
     * @param styleName
     */
    void setStyleName(String styleName);

    /**
     * Display the current page from total pages in the view
     * @param currentPage The current page as int
     * @param totalPages The total number of pages as int
     */
    void displayCurrentPage(int currentPage, int totalPages);

    /**
     * Get "first" pagination link as <code>HasClickHandlers</code> to attach specific handler
     * in the controller
     * @return "first" link as HasClickHandlers
     */
    HasClickHandlers getFirst();
    /**
     * Get "last" pagination link as <code>HasClickHandlers</code> to attach specific handler
     * in the controller
     * @return "last" link as HasClickHandlers
     */
    HasClickHandlers getLast();
    /**
     * Get "next" pagination link as <code>HasClickHandlers</code> to attach specific handler
     * in the controller
     * @return "next" link as HasClickHandlers
     */
    HasClickHandlers getNext();
    /**
     * Get "previous" pagination link as <code>HasClickHandlers</code> to attach specific handler
     * in the controller
     * @return "previous" link as HasClickHandlers
     */
    HasClickHandlers getPrevious();
}
