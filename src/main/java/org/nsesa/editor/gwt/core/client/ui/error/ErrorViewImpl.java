package org.nsesa.editor.gwt.core.client.ui.error;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ErrorViewImpl extends Composite implements ErrorView {

    interface MyUiBinder extends UiBinder<Widget, ErrorViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    HTML errorMessage;
    @UiField
    HTML errorTitle;
    @UiField
    Button okButton;

    @Inject
    public ErrorViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public void setErrorTitle(String title) {
        errorTitle.setHTML(title);
    }

    @Override
    public void setErrorMessage(String message) {
        errorMessage.setHTML(message);
    }

    @Override
    public HasClickHandlers getOkButton() {
        return okButton;
    }
}
