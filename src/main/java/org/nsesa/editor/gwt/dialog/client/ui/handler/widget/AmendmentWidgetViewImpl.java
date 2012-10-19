package org.nsesa.editor.gwt.dialog.client.ui.handler.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditor;
import org.nsesa.editor.gwt.dialog.client.ui.tab.author.AuthorPanelController;
import org.nsesa.editor.gwt.dialog.client.ui.tab.author.AuthorPanelView;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentWidgetViewImpl extends Composite implements AmendmentWidgetView {

    interface MyUiBinder extends UiBinder<Widget, AmendmentWidgetViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    Button cancelButton;
    @UiField
    Button saveButton;
    @UiField
    DockLayoutPanel dockPanel;
    @UiField
    HTML title;

    @UiField(provided = true)
    final RichTextEditor originalText;

    @UiField(provided = true)
    final RichTextEditor amendmentText;

    @UiField
    TabLayoutPanel tabLayoutPanel;

    @UiField(provided = true)
    AuthorPanelView authorPanelView;


    @Inject
    public AmendmentWidgetViewImpl(
            @Named("originalText") final RichTextEditor originalText,
            @Named("amendmentText") final RichTextEditor amendmentText,
            final AuthorPanelController authorPanelController) {

        this.originalText = originalText;
        this.amendmentText = amendmentText;
        this.authorPanelView = authorPanelController.getView();

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
    public void setOriginalContent(final String originalContent) {
        this.originalText.setHTML(originalContent);
    }

    @Override
    public void setAmendmentContent(final String amendmentContent) {
        this.amendmentText.setHTML(amendmentContent);
    }

    @Override
    public HasClickHandlers getSaveButton() {
        return saveButton;
    }

    @Override
    public HasClickHandlers getCancelButton() {
        return cancelButton;
    }
}
