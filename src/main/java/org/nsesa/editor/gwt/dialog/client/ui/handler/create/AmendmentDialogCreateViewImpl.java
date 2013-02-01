package org.nsesa.editor.gwt.dialog.client.ui.handler.create;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.author.AuthorPanelController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.author.AuthorPanelView;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditor;

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
