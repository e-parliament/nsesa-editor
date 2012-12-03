package org.nsesa.editor.gwt.editor.client.ui.amendments.filter;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsAction;

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
}
