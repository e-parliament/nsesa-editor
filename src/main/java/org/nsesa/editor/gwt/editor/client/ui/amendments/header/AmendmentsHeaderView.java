package org.nsesa.editor.gwt.editor.client.ui.amendments.header;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsAction;
import org.nsesa.editor.gwt.core.client.util.Selection;

import java.util.List;

/**
 * Amendments header view interface
 * User: groza
 * Date: 26/11/12
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
@ImplementedBy(AmendmentsHeaderViewImpl.class)
public interface AmendmentsHeaderView extends IsWidget {
    public void setSelections(List<Selection> selections);
    public void setActions(List<AmendmentsAction> actions);
    public void setStyleName(String styleName);
}
