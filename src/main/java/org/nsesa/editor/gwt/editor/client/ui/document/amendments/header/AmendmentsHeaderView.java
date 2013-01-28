package org.nsesa.editor.gwt.editor.client.ui.document.amendments.header;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Amendments header view interface
 * User: groza
 * Date: 26/11/12
 * Time: 11:50
 */
@ImplementedBy(AmendmentsHeaderViewImpl.class)
public interface AmendmentsHeaderView extends IsWidget {

    void addSelection(IsWidget selectionWidget);

    void addAction(IsWidget actionWidget);

    void setStyleName(String styleName);
}
