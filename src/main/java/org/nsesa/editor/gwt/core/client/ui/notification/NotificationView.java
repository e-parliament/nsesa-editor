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
package org.nsesa.editor.gwt.core.client.ui.notification;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * View for the notification component.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(NotificationViewImpl.class)
public interface NotificationView extends IsWidget {

    /**
     * General method to set the style name on this view.
     * @param style the css style name
     */
    void setStyleName(String style);

    /**
     * Set the notification message.
     * @param message the message
     */
    void setMessage(String message);

    /**
     * Get a reference to the close button.
     * @return the close button
     */
    HasClickHandlers getCloseButton();

    /**
     * Add an on-mouse-over handler to register mouse-over events.
     * @param handler the handler
     * @return the handler registration
     */
    HandlerRegistration addMouseOverHandler(MouseOverHandler handler);

    /**
     * Add an on-mouse-out handler to register mouse-out events.
     * @param handler the handler
     * @return the handler registration
     */
    HandlerRegistration addMouseOutHandler(MouseOutHandler handler);
}
