package org.nsesa.editor.gwt.core.client.ui.confirmation;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ConfirmationViewImpl.class)
public interface ConfirmationView extends IsWidget {

    void setConfirmationTitle(String title);

    void setConfirmationMessage(String message);

    void setConfirmationButtonText(String text);

    void setCancelButtonText(String text);

    HasClickHandlers getConfirmationButton();

    HasClickHandlers getCancelButton();
}
