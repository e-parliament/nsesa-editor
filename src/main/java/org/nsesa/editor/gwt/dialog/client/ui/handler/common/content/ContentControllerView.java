package org.nsesa.editor.gwt.dialog.client.ui.handler.common.content;

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
@ImplementedBy(ContentControllerViewImpl.class)
public interface ContentControllerView extends IsWidget {

    void addBodyClass(String className);

    void resetBodyClass();

    void setOriginalText(String content);

    String getOriginalText();

    public RichTextEditor getRichTextEditor();
}
