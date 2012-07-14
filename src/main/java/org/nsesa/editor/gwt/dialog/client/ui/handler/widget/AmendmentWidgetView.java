package org.nsesa.editor.gwt.dialog.client.ui.handler.widget;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Default 'simple' view for the creation and editing of amendments on simple widgets.
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendmentWidgetView extends IsWidget {

    void setTitle(String title);

    void setOriginalContent(String originalContent);

    void setAmendmentContent(String amendmentContent);

    HasClickHandlers getSaveButton();

    HasClickHandlers getCancelButton();
}
