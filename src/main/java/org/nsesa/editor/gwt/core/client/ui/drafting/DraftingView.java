package org.nsesa.editor.gwt.core.client.ui.drafting;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * The drafting view
 * User: groza
 * Date: 16/01/13
 * Time: 13:39
 */
@ImplementedBy(DraftingViewImpl.class)
public interface DraftingView extends IsWidget {
    void clearAll();
    void addWidget(IsWidget widget);
    void setDraftTitle(String title);
}
