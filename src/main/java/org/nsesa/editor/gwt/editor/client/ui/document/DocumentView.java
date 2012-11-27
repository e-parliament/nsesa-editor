package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Date: 24/06/12 21:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DocumentViewImpl.class)
public interface DocumentView extends IsWidget {

    void setWidth(String width);

    void setDocumentHeight(int height);

    void setStyleName(String style);

    void switchToTab(int index);
}
