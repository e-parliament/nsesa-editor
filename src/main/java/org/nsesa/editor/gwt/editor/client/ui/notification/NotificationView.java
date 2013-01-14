package org.nsesa.editor.gwt.editor.client.ui.notification;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(NotificationViewImpl.class)
public interface NotificationView extends IsWidget {
    void setStyleName(String style);

    void setMessage(String message);

    HasClickHandlers getCloseButton();

    HandlerRegistration addMouseOverHandler(MouseOverHandler handler);

    HandlerRegistration addMouseOutHandler(MouseOutHandler handler);
}
