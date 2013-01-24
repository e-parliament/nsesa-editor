package org.nsesa.editor.gwt.dialog.client.ui.handler.modify;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditor;

/**
 * Default 'simple' view for the creation and editing of amendments on simple widgets.
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AmendmentDialogModifyViewImpl.class)
public interface AmendmentDialogModifyView extends IsWidget {

    void setTitle(String title);

    void setAmendmentContent(String amendmentContent);

    String getAmendmentContent();

    void addView(IsWidget view, String title);

    RichTextEditor getRichTextEditor();

    HasClickHandlers getSaveButton();

    HasClickHandlers getCancelLink();
}
