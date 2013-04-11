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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor;
import org.nsesa.editor.gwt.core.client.util.UUID;
import org.nsesa.editor.gwt.core.shared.AmendmentAction;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.inline.client.event.AttachInlineEditorEvent;
import org.nsesa.editor.gwt.inline.client.event.AttachInlineEditorEventHandler;
import org.nsesa.editor.gwt.inline.client.event.DetachInlineEditorEvent;
import org.nsesa.editor.gwt.inline.client.event.DetachInlineEditorEventHandler;

import java.util.logging.Logger;

/**
 * A controller for inline editing. Can be attached to an {@link OverlayWidget}.
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class InlineEditorController implements ProvidesResize {

    private static final Logger LOG = Logger.getLogger(InlineEditorController.class.getName());

    /**
     * The client factory, with access to the {@link com.google.web.bindery.event.shared.EventBus},
     * {@link org.nsesa.editor.gwt.core.shared.ClientContext}, ..
     */
    private final ClientFactory clientFactory;

    /**
     * The overlay factory.
     */
    private final OverlayFactory overlayFactory;

    private final InlineEditorView view;

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

    private boolean showing;

    /**
     * The document controller.
     */
    private DocumentController documentController;
    private HandlerRegistration attachInlineEditorEventHandlerRegistration;
    private HandlerRegistration detachInlineEditorEventHandlerRegistration;


    @Inject
    public InlineEditorController(final ClientFactory clientFactory,
                                  final OverlayFactory overlayFactory,
                                  final InlineEditorView view) {
        this.clientFactory = clientFactory;
        this.overlayFactory = overlayFactory;
        this.view = view;

        registerListeners();
    }

    private void registerListeners() {
        attachInlineEditorEventHandlerRegistration = clientFactory.getEventBus().addHandler(AttachInlineEditorEvent.TYPE, new AttachInlineEditorEventHandler() {
            @Override
            public void onEvent(AttachInlineEditorEvent event) {

                if (overlayWidget != null) {
                    view.destroy();
                    // we're already editing this one ..
                    overlayWidget.asWidget().setVisible(true);
                }
                overlayWidget = event.getOverlayWidget();
                amendment = createAmendment();
                show();

            }
        });

        detachInlineEditorEventHandlerRegistration = clientFactory.getEventBus().addHandler(DetachInlineEditorEvent.TYPE, new DetachInlineEditorEventHandler() {
            @Override
            public void onEvent(DetachInlineEditorEvent event) {
                hide();
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        attachInlineEditorEventHandlerRegistration.removeHandler();
        detachInlineEditorEventHandlerRegistration.removeHandler();
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
        overlayWidget.getParentOverlayWidget().asWidget().getElement().insertBefore(view.asWidget().getElement(), overlayWidget.getOverlayElement());
        view.getRichTextEditor().setHTML(overlayWidget.asWidget().getElement().getInnerHTML());
        adaptSize();

        view.asWidget().setVisible(true);
        view.init();
        view.getRichTextEditor().setOverlayWidget(overlayWidget);
        overlayWidget.asWidget().setVisible(false);

        showing = true;
    }

    public boolean isShowing() {
        return showing;
    }

    /**
     * Changes the size of the dialog to become the size of the window - 100 pixels.
     */
    public void adaptSize() {
        view.asWidget().setWidth(overlayWidget.asWidget().getOffsetWidth() + "px");
        final int offsetHeight = overlayWidget.asWidget().getOffsetHeight();
        int editorHeight = ((offsetHeight) + 80);
        if (editorHeight < 200) editorHeight = 200;
        LOG.info("Setting inline editor height to " + editorHeight);
        view.asWidget().setHeight(editorHeight + "px");

        final int finalEditorHeight = editorHeight;
        clientFactory.getScheduler().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                view.getRichTextEditor().resize("100%", finalEditorHeight + "px");
            }
        });

    }

    /**
     * Call to hide the inline editor - effectively destroys the inline RTE.
     */
    public void hide() {
        view.destroy();
        view.asWidget().setVisible(false);
        if (overlayWidget != null) {
            overlayWidget.asWidget().setVisible(true);
        }
        showing = false;
    }

    /**
     * Return the underlying amendment dto.
     *
     * @return the amendment dto
     */
    public AmendmentContainerDTO getAmendment() {
        return amendment;
    }

    /**
     * Set the amendment dto.
     *
     * @param amendment the amendment dto
     */
    public void setAmendment(AmendmentContainerDTO amendment) {
        this.amendment = amendment;
    }

    /**
     * Get the underlying overlay widget.
     *
     * @return the overlay widget
     */
    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }

    /**
     * Set the underlying overlay widget.
     *
     * @param overlayWidget the overlay widget
     */
    public void setOverlayWidget(OverlayWidget overlayWidget) {
        this.overlayWidget = overlayWidget;
    }

    /**
     * Set the parent document controller.
     *
     * @param documentController the document controller
     */
    public void setDocumentController(final DocumentController documentController) {
        this.documentController = documentController;
    }

    public RichTextEditor getRichTextEditor() {
        return view.getRichTextEditor();
    }

    public InlineEditorView getView() {
        return view;
    }
}
