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
package org.nsesa.editor.gwt.core.client.ui.drafting;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * GWT implementation for drafting view
 * User: groza
 * Date: 16/01/13
 * Time: 13:40
 */
@Scope(DOCUMENT)
public class DraftingViewImpl extends ResizeComposite implements DraftingView {
    interface MyUiBinder extends UiBinder<Widget, DraftingViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    Label draftTitle;

    @UiField
    VerticalPanel mainPanel;

    @Inject
    public DraftingViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        mainPanel.getElement().addClassName("drafting");
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

    @Override
    public void setDraftTitle(String title) {
        draftTitle.setText(title);
    }

}
