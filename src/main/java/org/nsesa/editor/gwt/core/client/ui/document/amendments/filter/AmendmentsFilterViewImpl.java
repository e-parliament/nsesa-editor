/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
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

import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Default implementation of {@link AmendmentsFilterView} interface based on {@link UiBinder} GWT mechanism.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 26/11/12 14:44
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentsFilterViewImpl extends Composite implements AmendmentsFilterView {

    interface MyUiBinder extends UiBinder<Widget, AmendmentsFilterViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    /**
     * A list box with the available filters
     */
    @UiField
    ListBox menuFilter;

    /**
     * Create <code>AmendmentsFilterViewImpl</code> and initiale it
     */
    @Inject
    public AmendmentsFilterViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    /**
     * Return <code>AmendmentsFilterViewImpl</code> as Widget
     * @return <code>AmendmentsFilterViewImpl</code> as Widget
     */
    @Override
    public Widget asWidget() {
        return this;
    }

    /**
     * Add given list of string in the <code>menufilter</code> list box
     * @param filterList The list to be added
     */
    @Override
    public void setFilters(List<String> filterList) {
        for (final String filter : filterList) {
            this.menuFilter.addItem(filter, filter);
        }

    }

    /**
     * Return the <code>menufilter</code> list box as <code>HasChangeHandlers</code> in order to
     * attach handlers to it
     * @return the <code>menufilter</code>list box
     */
    @Override
    public HasChangeHandlers getFilter() {
        return menuFilter;
    }

    /**
     * Return the selected value of <code>menufilter</code> list box
     * @return selected value of <code>menufilter</code> list box
     */
    @Override
    public String getSelectedFilter() {
        return menuFilter.getValue(menuFilter.getSelectedIndex());
    }
}
