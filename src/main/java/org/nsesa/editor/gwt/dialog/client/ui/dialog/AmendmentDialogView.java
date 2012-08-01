package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Default 'simple' view for the creation and editing of amendments on simple widgets.
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AmendmentDialogViewImpl.class)
public interface AmendmentDialogView extends IsWidget {
    CellPanel getMainPanel();

    DockLayoutPanel getLayoutPanel();
}
