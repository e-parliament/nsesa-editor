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
package org.nsesa.editor.gwt.inline.client.ui.inline;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.shared.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor;
import org.nsesa.editor.gwt.core.client.util.UUID;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.inline.client.event.AttachInlineEditorEvent;
import org.nsesa.editor.gwt.inline.client.event.AttachInlineEditorEventHandler;
import org.nsesa.editor.gwt.inline.client.event.DetachInlineEditorEvent;
import org.nsesa.editor.gwt.inline.client.event.DetachInlineEditorEventHandler;

import java.util.logging.Logger;

/**
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class InlineEditorController implements ProvidesResize {

    private static final Logger LOG = Logger.getLogger(InlineEditorController.class.getName());

    /**
     * The client factory, with access to the {@link com.google.web.bindery.event.shared.EventBus},
     * {@link org.nsesa.editor.gwt.core.shared.ClientContext}, ..
     */
    private final ClientFactory clientFactory;

    private final OverlayFactory overlayFactory;

    private final RichTextEditor richTextEditor;

    /**
     * The amendment to add or edit.
     */
    private AmendmentContainerDTO amendment;

    /**
     * The amendment action (modification, deletion, ..). Can be retrieved via the amendment in case of an edit.
     */
    private AmendmentAction amendmentAction;

    /**
     * The amendable widget.
     */
    private OverlayWidget overlayWidget;

    /**
     * The logical parent amendable widget (only relevant in case of new elements).
     */
    private OverlayWidget parentOverlayWidget;


    /**
     * The document controller.
     */
    private DocumentController documentController;


    @Inject
    public InlineEditorController(final ClientFactory clientFactory,
                                  final OverlayFactory overlayFactory,
                                  @Named("inlineRichTextEditor") final RichTextEditor richTextEditor) {
        this.clientFactory = clientFactory;
        this.overlayFactory = overlayFactory;
        this.richTextEditor = richTextEditor;

        registerListeners();
    }

    private void registerListeners() {
        clientFactory.getEventBus().addHandler(AttachInlineEditorEvent.TYPE, new AttachInlineEditorEventHandler() {
            @Override
            public void onEvent(AttachInlineEditorEvent event) {

                if (overlayWidget != null) {
                    richTextEditor.destroy();
                    // we're already editing this one ..
                    overlayWidget.asWidget().setVisible(true);
                }
                overlayWidget = event.getOverlayWidget();
                amendment = createAmendment();
                show();

            }
        });

        clientFactory.getEventBus().addHandler(DetachInlineEditorEvent.TYPE, new DetachInlineEditorEventHandler() {
            @Override
            public void onEvent(DetachInlineEditorEvent event) {
                hide();
            }
        });
    }

    protected AmendmentContainerDTO createAmendment() {
        AmendmentContainerDTO dto = new AmendmentContainerDTO();
        dto.setId(UUID.uuid());
        return dto;
    }


    /**
     * Resizes the dialog, centers and shows the popup.
     */
    public void show() {
        // attach to the parent
        overlayWidget.getParentOverlayWidget().asWidget().getElement().insertBefore(richTextEditor.asWidget().getElement(), overlayWidget.getOverlayElement());
        richTextEditor.setHTML(DOM.toString(overlayWidget.asWidget().getElement()));
        richTextEditor.asWidget().setWidth(overlayWidget.asWidget().getOffsetWidth() + "px");
        richTextEditor.asWidget().setHeight(overlayWidget.asWidget().getOffsetHeight() + 50 + "px");
        richTextEditor.init();
        richTextEditor.asWidget().setVisible(true);
        richTextEditor.setAmendableWidget(overlayWidget);
        overlayWidget.asWidget().setVisible(false);
        adaptSize();
    }

    /**
     * Changes the size of the dialog to become the size of the window - 100 pixels.
     */
    public void adaptSize() {

    }

    public void hide() {
        richTextEditor.destroy();
        richTextEditor.asWidget().setVisible(false);
        if (overlayWidget != null)
            overlayWidget.asWidget().setVisible(true);
    }

    public AmendmentContainerDTO getAmendment() {
        return amendment;
    }

    public void setAmendment(AmendmentContainerDTO amendment) {
        this.amendment = amendment;
    }


    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }

    public void setOverlayWidget(OverlayWidget overlayWidget) {
        this.overlayWidget = overlayWidget;
    }

    public void setDocumentController(final DocumentController documentController) {
        this.documentController = documentController;
    }
}
