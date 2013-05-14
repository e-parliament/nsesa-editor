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
package org.nsesa.editor.gwt.compare.client.ui.compare;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor;
import org.nsesa.editor.gwt.core.shared.RevisionDTO;

import java.util.List;

/**
 * Default implementation of the {@link CompareView} using UIBinder.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class CompareViewImpl extends Composite implements CompareView {

    interface MyUiBinder extends UiBinder<Widget, CompareViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    Button rollbackButton;
    @UiField
    Anchor cancelAnchor;

    @UiField(provided = true)
    RichTextEditor richTextEditorA;

    @UiField(provided = true)
    RichTextEditor richTextEditorB;
    @UiField
    HorizontalPanel revisionsPanel;

    @Inject
    public CompareViewImpl(@Named("revisionText") RichTextEditor richTextEditorA,
                           @Named("revisionText") RichTextEditor richTextEditorB) {
        this.richTextEditorA = richTextEditorA;
        this.richTextEditorB = richTextEditorB;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        revisionsPanel.setCellWidth(richTextEditorA, "50%");
        revisionsPanel.setCellHeight(richTextEditorA, "100%");
        revisionsPanel.setCellWidth(richTextEditorB, "50%");
        revisionsPanel.setCellHeight(richTextEditorB, "100%");
    }

    @Override
    public Button getRollbackButton() {
        return rollbackButton;
    }

    @Override
    public Anchor getCancelAnchor() {
        return cancelAnchor;
    }

    @Override
    public void destroy() {
        richTextEditorA.destroy();
        richTextEditorB.destroy();
    }

    @Override
    public void setAvailableRevisions(List<RevisionDTO> revisions) {
        // TODO
    }

    @Override
    public void setRevisionA(String revisionContent) {
        richTextEditorA.setHTML(revisionContent);
    }

    @Override
    public void setRevisionB(String revisionContent) {
        richTextEditorB.setHTML(revisionContent);
    }
}
