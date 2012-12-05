package org.nsesa.editor.gwt.dialog.client.ui.handler.move;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * View for handling amendments on table structures.
 * <p/>
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AmendmentDialogMoveViewImpl.class)
public interface AmendmentDialogMoveView extends IsWidget {

    HasClickHandlers getSaveButton();

    HasClickHandlers getCancelLink();
}
