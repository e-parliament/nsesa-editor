package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogViewImpl extends Composite implements AmendmentDialogView {

    interface MyUiBinder extends UiBinder<Widget, AmendmentDialogViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    Button cancelButton;
    @UiField
    Button saveButton;
    @UiField
    DockLayoutPanel dockPanel;

    @Inject
    public AmendmentDialogViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        dockPanel.setWidth("500");
        dockPanel.setHeight("400");
    }

    @Override
    public HasClickHandlers getSaveButton() {
        return saveButton;
    }

    @Override
    public HasClickHandlers getCancelButton() {
        return cancelButton;
    }
}
