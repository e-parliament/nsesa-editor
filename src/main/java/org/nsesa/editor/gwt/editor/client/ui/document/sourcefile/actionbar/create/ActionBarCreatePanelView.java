package org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.actionbar.create;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ActionBarCreatePanelViewImpl.class)
public interface ActionBarCreatePanelView extends IsWidget {
    void setUIListener(UIListener uiListener);

    void attach();

    void addChildAmendableWidget(String title, AmendableWidget amendableWidget);

    void addSiblingAmendableWidget(String title, final AmendableWidget amendableWidget);

    void setSeparatorVisible(boolean visible);

    void clearAmendableWidgets();

    public static interface UIListener {
        void onClick(AmendableWidget newChild, boolean sibling);
    }
}
