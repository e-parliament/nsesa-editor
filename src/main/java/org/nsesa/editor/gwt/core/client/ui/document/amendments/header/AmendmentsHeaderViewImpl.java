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
package org.nsesa.editor.gwt.core.client.ui.document.amendments.header;

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

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Default implementation of <code>AmendmentsHeaderView</code> interface based on {@link UiBinder} GWT mechanism.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 26/11/12 11:51
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentsHeaderViewImpl extends Composite implements AmendmentsHeaderView {

    interface MyUiBinder extends UiBinder<Widget, AmendmentsHeaderViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    /**
     *  Holder for selections
     */
    @UiField
    HTMLPanel selections;
    /**
     *  Holder for actions
     */
    @UiField
    HTMLPanel actions;

    /**
     * Create an empty AmendmentsHeaderViewImpl object
     */
    @Inject
    public AmendmentsHeaderViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    /**
     * Returns AmendmentsHeaderViewImpl as widget
     * @return
     */
    @Override
    public Widget asWidget() {
        return this;
    }

    /**
     * Add a widget in the selection panel holder
     * @param selectionWidget The widget to be added
     */
    @Override
    public void addSelection(IsWidget selectionWidget) {
        this.selections.add(selectionWidget);
    }

    /**
     * Add a widget in the actions holder panel
     * @param actionWidget The widget to be added
     */
    @Override
    public void addAction(IsWidget actionWidget) {
        this.actions.add(actionWidget);
    }
}
