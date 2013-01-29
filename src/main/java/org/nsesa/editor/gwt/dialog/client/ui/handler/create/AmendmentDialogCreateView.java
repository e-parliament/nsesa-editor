package org.nsesa.editor.gwt.dialog.client.ui.handler.create;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditor;

/**
 * View for the creation and editing of amendment bundles.
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AmendmentDialogCreateViewImpl.class)
public interface AmendmentDialogCreateView extends IsWidget {

    String getAmendmentContent();

    void setAmendmentContent(String content);

    HasClickHandlers getSaveButton();

    HasClickHandlers getCancelLink();

    void setTitle(String title);

    RichTextEditor getRichTextEditor();
}
