package org.nsesa.editor.gwt.core.client.ui.drafting;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * GWT implementation for drafting view
 * User: groza
 * Date: 16/01/13
 * Time: 13:40
 */
@Singleton
@Scope(DOCUMENT)
public class DraftingViewImpl extends Composite implements DraftingView {
    interface MyUiBinder extends UiBinder<Widget, DraftingViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    VerticalPanel mainPanel;

    @Inject
    public DraftingViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        if (!GWT.isScript())
            this.setTitle(this.getClass().getName());
    }

    @Override
    public void clearAll() {
        mainPanel.clear();
    }

    @Override
    public void addWidget(IsWidget widget) {
        mainPanel.add(widget);
    }

}
