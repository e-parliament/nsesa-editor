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
package org.nsesa.editor.gwt.amendment.client.ui.document.amendments.filter;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * <code>AmendmentsFilterView</code> view is part of the second tabbed panel of the application. It provides a list
 * of options that can be used to filter the amendments displayed in this second panel.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 26/11/12 13:44
 */
@ImplementedBy(AmendmentsFilterViewImpl.class)
public interface AmendmentsFilterView extends IsWidget {
    /**
     * Set the style name of the view
     * @param styleName The style name as String
     */
    public void setStyleName(String styleName);

    /**
     * Set the list of the available filters
     * @param filterList
     */
    public void setFilters(List<String> filterList);

    /**
     * Return the current filter
     * @return
     */
    public HasChangeHandlers getFilter();

    /**
     * Return the selected filter
     * @return The selected filter sa String
     */
    public String getSelectedFilter();
}
