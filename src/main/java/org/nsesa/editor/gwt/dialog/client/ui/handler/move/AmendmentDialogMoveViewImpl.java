/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.dialog.client.ui.handler.move;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.content.ContentController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.content.ContentView;
import org.nsesa.editor.gwt.dialog.client.ui.handler.move.action.BeforeAfterActionBarController;
import org.nsesa.editor.gwt.dialog.client.ui.handler.move.action.BeforeAfterActionBarView;

/**
 * Default implementation for the {@link AmendmentDialogMoveView} using UIBinder.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogMoveViewImpl extends Composite implements AmendmentDialogMoveView {

    interface MyUiBinder extends UiBinder<Widget, AmendmentDialogMoveViewImpl> {
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
    @UiField
    TabLayoutPanel tabLayoutPanel;
    @UiField(provided = true)
    ContentView contentView;
    @UiField
    ScrollPanel scrollPanel;
    @UiField(provided = true)
    BeforeAfterActionBarView beforeActionBarView;


    @Inject
    public AmendmentDialogMoveViewImpl(final ContentController contentController,
                                       final BeforeAfterActionBarController beforeAfterActionBarController) {

        this.contentView = contentController.getView();
        this.beforeActionBarView = beforeAfterActionBarController.getView();

        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        if (!GWT.isScript())
            widget.setTitle(this.getClass().getName());

        dockPanel.setHeight("100%");
        dockPanel.setWidth("100%");
        scrollPanel.setHeight("100%");
    }

    @Override
    public HasClickHandlers getSaveButton() {
        return saveButton;
    }

    @Override
    public HasClickHandlers getCancelLink() {
        return cancelLink;
    }

    @Override
    public ContentView getContentView() {
        return contentView;
    }
}
