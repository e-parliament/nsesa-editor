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
package org.nsesa.editor.gwt.core.client.ui.document;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.event.SwitchTabEvent;
import org.nsesa.editor.gwt.core.client.ui.document.info.InfoPanelController;
import org.nsesa.editor.gwt.core.client.ui.document.info.InfoPanelView;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.SourceFileController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.SourceFileView;
import org.nsesa.editor.gwt.core.client.util.Scope;

import java.util.logging.Logger;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Default implementation for the {@link DocumentView} using UIBinder.
 * Date: 24/06/12 16:39
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class DocumentViewImpl extends Composite implements DocumentView, ProvidesResize {


    interface MyUiBinder extends UiBinder<Widget, DocumentViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private static final Logger LOG = Logger.getLogger(DocumentViewImpl.class.getName());

    private final DocumentEventBus documentEventBus;
    private final org.nsesa.editor.gwt.core.client.ui.document.resources.Resources resources;

    @UiField
    DockLayoutPanel mainPanel;

    @UiField
    HTML documentTitle;

    @UiField
    TabLayoutPanel tabPanel;

    @UiField(provided = true)
    SourceFileView sourceFileView;

    @UiField(provided = true)
    InfoPanelView infoPanelView;

    @UiField
    Image viewDocument;
    @UiField
    Image viewAmendments;
    @UiField
    Image viewInfo;
    @UiField
    Image loadingIndicator;
    @UiField
    Label loadingMessage;

    @Inject
    public DocumentViewImpl(final DocumentEventBus documentEventBus,
                            final InfoPanelController infoPanelController,
                            final SourceFileController sourceFileController,
                            final org.nsesa.editor.gwt.core.client.ui.document.resources.Resources resources
    ) {

        this.documentEventBus = documentEventBus;
        this.sourceFileView = sourceFileController.getView();
        this.infoPanelView = infoPanelController.getView();
        this.resources = resources;

        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        //show class name tool tip in hosted mode
        if (!GWT.isScript())
            widget.setTitle(this.getClass().getName());

        switchToTab(0);
    }

    public void setDocumentHeight(final int height) {
        mainPanel.setHeight(height + "px");
    }

    public void setDocumentTitle(String titleHTML) {
        documentTitle.setHTML(titleHTML);
    }

    @Override
    public void showLoadingIndicator(boolean show, String message) {
        loadingIndicator.setVisible(show);
        if (show) { loadingMessage.setText(message); } else { loadingMessage.setText(""); };
    }

    @UiHandler("viewDocument")
    void handleDocumentClick(ClickEvent event) {
        documentEventBus.fireEvent(new SwitchTabEvent(0));
    }

    @UiHandler("viewAmendments")
    void handleAmendmentsClick(ClickEvent event) {
        documentEventBus.fireEvent(new SwitchTabEvent(1));
    }

    @UiHandler("viewInfo")
    void handleInfoClick(ClickEvent event) {
        documentEventBus.fireEvent(new SwitchTabEvent(2));
    }

    public void switchToTab(int index) {
        // reset tab images
        viewDocument.setResource(resources.viewDocument());
        viewAmendments.setResource(resources.viewAmendments());
        viewInfo.setResource(resources.viewInfo());

        switch (index) {
            case 0:
                viewDocument.setResource(resources.viewDocumentSelected());
                break;
            case 1:
                viewAmendments.setResource(resources.viewAmendmentsSelected());
                break;
            case 2:
                viewInfo.setResource(resources.viewInfoSelected());
                break;
            default:
                break;
        }
        tabPanel.selectTab(index);
    }
}
