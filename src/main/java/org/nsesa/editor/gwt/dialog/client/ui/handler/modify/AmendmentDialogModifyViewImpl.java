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
package org.nsesa.editor.gwt.dialog.client.ui.handler.modify;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor;

/**
 * Default implementation of the {@link AmendmentDialogModifyView} using UIBinder.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogModifyViewImpl extends Composite implements AmendmentDialogModifyView {

    interface MyUiBinder extends UiBinder<Widget, AmendmentDialogModifyViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    Anchor cancelLink;
    @UiField
    Button saveButton;
    @UiField
    DockLayoutPanel dockPanel;
    @UiField
    HTML title;

    @UiField(provided = true)
    final RichTextEditor amendmentText;

    @UiField
    TabLayoutPanel tabLayoutPanel;


    @Inject
    public AmendmentDialogModifyViewImpl(@Named("amendmentText") final RichTextEditor amendmentText) {

        this.amendmentText = amendmentText;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        dockPanel.setHeight("100%");
        dockPanel.setWidth("100%");

    }

    @Override
    protected void onAttach() {
        super.onAttach();
        //hide the toolbar
        amendmentText.toggleDraftingTool(false);
        amendmentText.toggleDraftingAttributes(false);
        selectTab(0);
    }

    private void selectTab(final int i) {
        tabLayoutPanel.selectTab(i);
    }

    @Override
    public void addBodyClass(String className) {
        amendmentText.addBodyClass(className);
    }

    @Override
    public void resetBodyClass() {
        amendmentText.resetBodyClass();
    }

    @Override
    public void setTitle(String title) {
        this.title.setHTML(title);
    }

    @Override
    public void setAmendmentContent(final String amendmentContent) {
        this.amendmentText.setHTML(amendmentContent);
    }

    @Override
    public String getAmendmentContent() {
        return amendmentText.getHTML();
    }

    @Override
    public void addView(IsWidget view, String title) {
        tabLayoutPanel.add(view, title);
    }

    @Override
    public RichTextEditor getRichTextEditor() {
        return amendmentText;
    }

    @Override
    public HasClickHandlers getSaveButton() {
        return saveButton;
    }

    @Override
    public HasClickHandlers getCancelLink() {
        return cancelLink;
    }
}
