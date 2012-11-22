package org.nsesa.editor.gwt.dialog.client.ui.handler.modify.content;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * View for the creation and editing of amendment bundles.
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ContentControllerViewImpl.class)
public interface ContentControllerView extends IsWidget {

    void setOriginalText(String content);
}
