package org.nsesa.editor.gwt.core.client.ui.error;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ErrorViewImpl.class)
public interface ErrorView extends IsWidget {

    void setErrorTitle(String title);

    void setErrorMessage(String message);

    HasClickHandlers getOkButton();
}
