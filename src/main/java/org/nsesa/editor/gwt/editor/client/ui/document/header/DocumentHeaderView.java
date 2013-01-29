package org.nsesa.editor.gwt.editor.client.ui.document.header;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Date: 24/06/12 21:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DocumentHeaderViewImpl.class)
public interface DocumentHeaderView extends IsWidget {

    void setStyleName(String style);
}
