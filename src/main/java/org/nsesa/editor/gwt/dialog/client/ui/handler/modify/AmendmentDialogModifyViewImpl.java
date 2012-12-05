package org.nsesa.editor.gwt.dialog.client.ui.handler.modify;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditor;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
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
        selectTab(0);
    }

    private void selectTab(final int i) {
        tabLayoutPanel.selectTab(i);
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
    public HasClickHandlers getSaveButton() {
        return saveButton;
    }

    @Override
    public HasClickHandlers getCancelLink() {
        return cancelLink;
    }
}
