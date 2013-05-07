/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.event;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * An event used to indicate that a confirmation from the user is required before proceeding.
 * Date: 24/06/12 20:14
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ConfirmationEvent extends GwtEvent<ConfirmationEventHandler> {

    public static final Type<ConfirmationEventHandler> TYPE = new Type<ConfirmationEventHandler>();

    private final String title;
    private final String message;
    private final String confirmationButtonText;
    private final ClickHandler confirmationHandler;
    private final String cancelButtonText;
    private final ClickHandler cancelHandler;

    public ConfirmationEvent(String title, String message, String confirmationButtonText, ClickHandler confirmationHandler, String cancelButtonText, ClickHandler cancelHandler) {
        this.title = title;
        this.message = message;
        this.confirmationButtonText = confirmationButtonText;
        this.confirmationHandler = confirmationHandler;
        this.cancelButtonText = cancelButtonText;
        this.cancelHandler = cancelHandler;
    }

    @Override
    public Type<ConfirmationEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ConfirmationEventHandler handler) {
        handler.onEvent(this);
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public ClickHandler getConfirmationHandler() {
        return confirmationHandler;
    }

    public ClickHandler getCancelHandler() {
        return cancelHandler;
    }

    public String getConfirmationButtonText() {
        return confirmationButtonText;
    }

    public String getCancelButtonText() {
        return cancelButtonText;
    }
}
