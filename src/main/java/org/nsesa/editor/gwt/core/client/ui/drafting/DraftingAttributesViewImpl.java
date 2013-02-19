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
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingModificationEvent;

import java.util.HashMap;
import java.util.Map;

public class DraftingAttributesViewImpl extends ResizeComposite implements DraftingAttributesView {
    private EventBus eventBus;

    interface MyUiBinder extends UiBinder<Widget, DraftingAttributesViewImpl> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    Grid attributesPanel;

    private Map<Label, TextBox> attributesHolder = new HashMap<Label, TextBox>();

    @Inject
    public DraftingAttributesViewImpl(EventBus eventBus) {
        this.eventBus = eventBus;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        if (!GWT.isScript())
            this.setTitle(this.getClass().getName());
    }

    @Override
    public void clearAll() {
        attributesPanel.clear();
    }

    @Override
    public void setAttributes(Map<String, String> attributes) {
        attributesHolder.clear();
        attributesPanel.resize(attributes.size(), 2);
        int i = 0;
        for (Map.Entry<String, String> attr : attributes.entrySet()) {
            final Label lbl = new Label(attr.getKey());
            final TextBox txtBox = new TextBox();
            txtBox.setText(attr.getValue());
            // add change handler
            txtBox.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent event) {
                    final Map<String, String> attrs = new HashMap<String, String>();
                    attrs.put(lbl.getText(), txtBox.getText());
                    eventBus.fireEvent(new DraftingModificationEvent(attrs));
                };
            });

            attributesPanel.setWidget(i, 0, lbl);
            attributesPanel.setWidget(i, 1, txtBox);
            i++;
            attributesHolder.put(lbl, txtBox);
        }
    }

    @Override
    public Map<String, String> getAttributes() {
        Map<String, String> result = new HashMap<String, String>();
        for (Map.Entry<Label, TextBox> attr : attributesHolder.entrySet()) {
            result.put(attr.getKey().getText(), attr.getValue().getText());
        }
        return result;
    }

}
