package org.nsesa.editor.gwt.editor.client.ui.document.amendments.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Implementation for AmendmentsFilterView interface
 * User: groza
 * Date: 26/11/12
 * Time: 11:51
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentsHeaderViewImpl extends Composite implements AmendmentsHeaderView {

    private DocumentEventBus documentEventBus;

    interface MyUiBinder extends UiBinder<Widget, AmendmentsHeaderViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    HTMLPanel selections;
    @UiField
    HTMLPanel actions;

    @Inject
    public AmendmentsHeaderViewImpl(DocumentEventBus documentEventBus) {
        this.documentEventBus = documentEventBus;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void addSelection(IsWidget selectionWidget) {
        this.selections.add(selectionWidget);
    }

    @Override
    public void addAction(IsWidget actionWidget) {
        this.actions.add(actionWidget);
    }
}
