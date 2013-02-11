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
