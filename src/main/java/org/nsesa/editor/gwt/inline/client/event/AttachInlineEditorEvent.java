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
package org.nsesa.editor.gwt.inline.client.event;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

/**
 * An event requesting the attaching of the inline editor to a given {@link OverlayWidget}.
 *
 * Date: 24/06/12 20:14
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AttachInlineEditorEvent extends GwtEvent<AttachInlineEditorEventHandler> {

    public static final Type<AttachInlineEditorEventHandler> TYPE = new Type<AttachInlineEditorEventHandler>();

    private final OverlayWidget overlayWidget;
    private final DocumentController documentController;

    public AttachInlineEditorEvent(OverlayWidget overlayWidget, DocumentController documentController) {
        this.overlayWidget = overlayWidget;
        this.documentController = documentController;
    }

    @Override
    public Type<AttachInlineEditorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AttachInlineEditorEventHandler handler) {
        handler.onEvent(this);
    }

    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }

    public DocumentController getDocumentController() {
        return documentController;
    }
}
