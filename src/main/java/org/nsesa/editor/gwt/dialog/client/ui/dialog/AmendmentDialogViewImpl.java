package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
    HorizontalPanel contentPanel;

    @Inject
    public AmendmentDialogViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        contentPanel.setWidth("300px");
        contentPanel.setHeight("250px");
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
