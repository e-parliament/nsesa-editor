package org.nsesa.editor.gwt.dialog.client.ui.handler.delete;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Default 'simple' view for the creation and editing of amendments on simple widgets.
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AmendmentDialogDeleteViewImpl.class)
public interface AmendmentDialogDeleteView extends IsWidget {

    void setTitle(String title);

    void addView(IsWidget view, String title);

    HasClickHandlers getSaveButton();

    HasClickHandlers getCancelLink();
}
