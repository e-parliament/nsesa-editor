/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
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
