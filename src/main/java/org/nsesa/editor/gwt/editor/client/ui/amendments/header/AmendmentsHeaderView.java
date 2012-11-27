package org.nsesa.editor.gwt.editor.client.ui.amendments.header;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Amendments header view interface
 * User: groza
 * Date: 26/11/12
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
@ImplementedBy(AmendmentsHeaderViewImpl.class)
public interface AmendmentsHeaderView extends IsWidget {
    public void setSelections();
    public void setActions();
    public void setStyleName(String styleName);
    public HasChangeHandlers getSelections();
    public HasChangeHandlers getActions();
}
