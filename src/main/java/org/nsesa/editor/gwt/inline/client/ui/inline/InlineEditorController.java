package org.nsesa.editor.gwt.inline.client.ui.inline;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.util.UUID;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditor;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
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
    private AmendableWidget amendableWidget;

    /**
     * The logical parent amendable widget (only relevant in case of new elements).
     */
    private AmendableWidget parentAmendableWidget;


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

                if (amendableWidget != null) {
                    richTextEditor.destroy();
                    // we're already editing this one ..
                    amendableWidget.asWidget().setVisible(true);
                }
                amendableWidget = event.getAmendableWidget();
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
        amendableWidget.getParentAmendableWidget().asWidget().getElement().insertBefore(richTextEditor.asWidget().getElement(), amendableWidget.getAmendableElement());
        richTextEditor.setHTML(DOM.toString(amendableWidget.asWidget().getElement()));
        richTextEditor.asWidget().setWidth(amendableWidget.asWidget().getOffsetWidth() + "px");
        richTextEditor.asWidget().setHeight(amendableWidget.asWidget().getOffsetHeight() + 50 + "px");
        richTextEditor.init();
        richTextEditor.asWidget().setVisible(true);
        richTextEditor.setAmendableWidget(amendableWidget);
        amendableWidget.asWidget().setVisible(false);
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
        if (amendableWidget != null)
            amendableWidget.asWidget().setVisible(true);
    }

    public AmendmentContainerDTO getAmendment() {
        return amendment;
    }

    public void setAmendment(AmendmentContainerDTO amendment) {
        this.amendment = amendment;
    }


    public AmendableWidget getAmendableWidget() {
        return amendableWidget;
    }

    public void setAmendableWidget(AmendableWidget amendableWidget) {
        this.amendableWidget = amendableWidget;
    }

    public void setDocumentController(final DocumentController documentController) {
        this.documentController = documentController;
    }
}
