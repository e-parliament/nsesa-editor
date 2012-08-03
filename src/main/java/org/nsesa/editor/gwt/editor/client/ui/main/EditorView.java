package org.nsesa.editor.gwt.editor.client.ui.main;

import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Date: 24/06/12 21:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(EditorViewImpl.class)
public interface EditorView extends IsWidget {
    CellPanel getDocumentsPanel();

    void switchToTab(int index);
}
