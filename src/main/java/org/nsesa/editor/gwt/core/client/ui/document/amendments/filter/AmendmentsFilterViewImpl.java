/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.ui.document.amendments.filter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;

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
