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
package org.nsesa.editor.gwt.inline.client.ui.inline;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor;

/**
 * Default implementation of the {@link InlineEditorView} using UIBinder.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class InlineEditorViewImpl extends Composite implements InlineEditorView {

    interface MyUiBinder extends UiBinder<Widget, InlineEditorViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField(provided = true)
    RichTextEditor richTextEditor;
    @UiField
    FocusPanel dummyFocusPanelAbove;
    @UiField
    FocusPanel dummyFocusPanelBelow;
    @UiField
    Button saveButton;
    @UiField
    Anchor cancelAnchor;

    @Inject
    public InlineEditorViewImpl(@Named("inlineRichTextEditor") RichTextEditor richTextEditor) {
        this.richTextEditor = richTextEditor;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public RichTextEditor getRichTextEditor() {
        return richTextEditor;
    }

    @Override
    public void destroy() {
        richTextEditor.destroy();
    }

    @Override
    public void init() {
        if (super.isAttached()) {
            super.onDetach();
        }
        super.onAttach();
        richTextEditor.init();
    }

    @Override
    public Button getSaveButton() {
        return saveButton;
    }

    @Override
    public Anchor getCancelAnchor() {
        return cancelAnchor;
    }

}
