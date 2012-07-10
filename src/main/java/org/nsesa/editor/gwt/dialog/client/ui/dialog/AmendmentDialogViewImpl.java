package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogViewImpl extends Composite implements AmendmentDialogView, ProvidesResize {

    interface MyUiBinder extends UiBinder<Widget, AmendmentDialogViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    VerticalPanel mainPanel;
    @UiField
    DockLayoutPanel dockPanel;

    @Inject
    public AmendmentDialogViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public CellPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public DockLayoutPanel getLayoutPanel() {
        return dockPanel;
    }
}
