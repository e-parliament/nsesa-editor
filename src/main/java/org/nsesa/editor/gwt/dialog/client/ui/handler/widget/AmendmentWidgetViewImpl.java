package org.nsesa.editor.gwt.dialog.client.ui.handler.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;

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
    @UiField
    HTMLPanel originalContent;
    @UiField
    HTMLPanel amendmentContent;

    @Inject
    public AmendmentWidgetViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        dockPanel.setHeight("100%");
        dockPanel.setWidth("100%");
    }

    @Override
    public void setTitle(String title) {
        this.title.setHTML(title);
    }

    @Override
    public void setOriginalContent(final String originalContent) {
        this.originalContent.getElement().setInnerHTML(originalContent);
    }

    @Override
    public void setAmendmentContent(final String amendmentContent) {
        this.amendmentContent.getElement().setInnerHTML(amendmentContent);
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