package org.nsesa.editor.gwt.editor.client.ui.amendments.filter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Implementation for AmendmentsFilterView interface
 * User: groza
 * Date: 26/11/12
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentsFilterViewImpl extends Composite implements AmendmentsFilterView {

    private DocumentEventBus documentEventBus;

    interface MyUiBinder extends UiBinder<Widget, AmendmentsFilterViewImpl> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    ListBox menuFilter;

    @Inject
    public AmendmentsFilterViewImpl(DocumentEventBus documentEventBus) {
        this.documentEventBus = documentEventBus;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public Widget asWidget() {
        return this;
    }
    @Override
    public void setFilters(List<String> filterList) {
        for (final String filter : filterList) {
            this.menuFilter.addItem(filter, filter);
        }

    }

    @Override
    public HasChangeHandlers getFilter() {
        return menuFilter;
    }

    @Override
    public String getSelectedFilter() {
        return menuFilter.getValue(menuFilter.getSelectedIndex());
    }
}
