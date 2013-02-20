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
package org.nsesa.editor.gwt.dialog.client.ui.handler.create;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.author.AuthorPanelController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.author.AuthorPanelView;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogCreateViewImpl extends Composite implements AmendmentDialogCreateView {

    interface MyUiBinder extends UiBinder<Widget, AmendmentDialogCreateViewImpl> {
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
    final RichTextEditor newText;

    @UiField
    TabLayoutPanel tabLayoutPanel;

    @UiField(provided = true)
    AuthorPanelView authorPanelView;

    @Inject
    public AmendmentDialogCreateViewImpl(@Named("newText") final RichTextEditor newText,
                                         final AuthorPanelController authorPanelController) {
        this.newText = newText;
        this.authorPanelView = authorPanelController.getView();
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        dockPanel.setHeight("100%");
        dockPanel.setWidth("100%");
    }

    public void setTitle(final String title) {
        this.title.setHTML(title);
    }

    @Override
    public RichTextEditor getRichTextEditor() {
        return newText;
    }

    @Override
    public void addBodyClass(String className) {
        newText.addBodyClass(className);
    }

    @Override
    public void resetBodyClass() {
        newText.resetBodyClass();
    }

    @Override
    public String getAmendmentContent() {
        return newText.getHTML();
    }

    @Override
    public void setAmendmentContent(String content) {
        this.newText.setHTML(content);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        //newText.toggleDraftingTool(true);
        newText.executeCommand("NsesaToggle", 500);
        newText.executeCommand("NsesaToggleAttributes", 500);
        selectTab(0);
    }

    private void selectTab(final int i) {
        tabLayoutPanel.selectTab(i);
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
