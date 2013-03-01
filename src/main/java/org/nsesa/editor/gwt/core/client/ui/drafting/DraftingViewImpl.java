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
 * Default implementation of {@link DraftingView} based on {@link UiBinder} GWT mechanism.
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 16/01/13 13:37
 */
@Scope(DOCUMENT)
public class DraftingViewImpl extends ResizeComposite implements DraftingView {
    interface MyUiBinder extends UiBinder<Widget, DraftingViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    /**
     * stores draft title
     */
    @UiField
    Label draftTitle;
    /**
     * holder for allowed children
     */
    @UiField
    VerticalPanel allowedPanel;

    /**
     * holder for mandatory children
     */
    @UiField
    VerticalPanel mandatoryPanel;

    /**
     * Create <code>DraftingViewImpl</code> object and initialize widgets
     */
    @Inject
    public DraftingViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        allowedPanel.getElement().addClassName("drafting");
        mandatoryPanel.getElement().addClassName("drafting");
        if (!GWT.isScript())
            this.setTitle(this.getClass().getName());
    }

    /**
     * Clean up the content of holder panels
     */
    @Override
    public void clearAll() {
        allowedPanel.clear();
        mandatoryPanel.clear();
    }

    /**
     * Add a widget in the holder panel for allowed children
     * @param widget The widget that will be added in the allowed children area of the view.
     */
    @Override
    public void addAllowedChild(IsWidget widget) {
        allowedPanel.add(widget);
    }

    /**
     * Add a widget in the holder panel for mandatory children
     * @param widget The widget that will be added in the  mandatory children area of the view.
     */
    public void addMandatoryChild(IsWidget widget) {
        mandatoryPanel.add(widget);
    }

    /**
     * Set the title of this view
     * @param title Title as String
     */
    @Override
    public void setDraftTitle(String title) {
        draftTitle.setText(title);
    }

}
