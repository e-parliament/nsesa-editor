package org.nsesa.editor.gwt.editor.client.ui.amendments.header;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * Amendments header view interface
 * User: groza
 * Date: 26/11/12
 * Time: 11:50
 */
@ImplementedBy(AmendmentsHeaderViewImpl.class)
public interface AmendmentsHeaderView extends IsWidget {
    public void setSelections(List<String> selections);
    public void setActions(List<String> actions);
    public void setStyleName(String styleName);
}
