package org.nsesa.editor.gwt.editor.client.ui.document.amendments.filter;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * Amendments Filter view interface
 * User: groza
 * Date: 26/11/12
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
@ImplementedBy(AmendmentsFilterViewImpl.class)
public interface AmendmentsFilterView extends IsWidget {
    public void setStyleName(String styleName);

    public void setFilters(List<String> filterList);

    public HasChangeHandlers getFilter();

    public String getSelectedFilter();
}
