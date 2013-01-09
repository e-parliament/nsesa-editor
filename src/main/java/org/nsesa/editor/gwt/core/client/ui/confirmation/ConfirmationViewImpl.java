package org.nsesa.editor.gwt.core.client.ui.confirmation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
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
public class ConfirmationViewImpl extends Composite implements ConfirmationView {

    interface MyUiBinder extends UiBinder<Widget, ConfirmationViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    HTML confirmationMessage;
    @UiField
    HTML confirmationTitle;
    @UiField
    Button confirmationButton;
    @UiField
    Anchor cancelButton;

    @Inject
    public ConfirmationViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public void setConfirmationTitle(String title) {
        confirmationTitle.setHTML(title);
    }

    @Override
    public void setConfirmationMessage(String message) {
        confirmationMessage.setHTML(message);
    }

    @Override
    public void setConfirmationButtonText(String text) {
        this.confirmationButton.setText(text);
    }

    @Override
    public void setCancelButtonText(String text) {
        this.cancelButton.setText(text);
    }

    @Override
    public HasClickHandlers getConfirmationButton() {
        return confirmationButton;
    }

    @Override
    public HasClickHandlers getCancelButton() {
        return cancelButton;
    }
}
