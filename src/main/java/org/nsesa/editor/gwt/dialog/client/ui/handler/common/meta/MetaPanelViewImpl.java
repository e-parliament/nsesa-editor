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
package org.nsesa.editor.gwt.dialog.client.ui.handler.common.meta;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DIALOG;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(DIALOG)
public class MetaPanelViewImpl extends Composite implements MetaPanelView {

    interface MyUiBinder extends UiBinder<Widget, MetaPanelViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private final ServiceFactory serviceFactory;

    @UiField
    TextArea justification;
    @UiField
    TextArea notes;

    @Inject
    public MetaPanelViewImpl(final ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public void setJustification(String justification) {
        this.justification.setText(justification);
    }

    @Override
    public void setNotes(String notes) {
        this.notes.setText(notes);
    }

    @Override
    public String getJustification() {
        return this.justification.getText();
    }

    @Override
    public String getNotes() {
        return this.notes.getText();
    }
}
