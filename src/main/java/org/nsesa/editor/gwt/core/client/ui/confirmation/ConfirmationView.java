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
package org.nsesa.editor.gwt.core.client.ui.confirmation;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * View for the confirmation component.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ConfirmationViewImpl.class)
public interface ConfirmationView extends IsWidget {

    /**
     * Set the title for the confirmation popup.
     * @param title the title
     */
    void setConfirmationTitle(String title);

    /**
     * Set the message for the confirmation.
     * @param message the message
     */
    void setConfirmationMessage(String message);

    /**
     * Set the confirmation action button's text
     * @param text the confirmation action text
     */
    void setConfirmationButtonText(String text);

    /**
     * Set the cancel action button's text
     * @param text the cancel action text
     */
    void setCancelButtonText(String text);

    /**
     * Get a reference to the confirmation button.
     * @return the confirmation component
     */
    HasClickHandlers getConfirmationButton();

    /**
     * Get a reference to the cancel button.
     * @return the cancel component
     */
    HasClickHandlers getCancelButton();
}
