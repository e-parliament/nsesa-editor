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
package org.nsesa.editor.gwt.core.client.event.widget;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * An event indicating a request to modify a given overlay widget.
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 */
public class OverlayWidgetModifyEvent extends GwtEvent<OverlayWidgetModifyEventHandler> {

    public static final Type<OverlayWidgetModifyEventHandler> TYPE = new Type<OverlayWidgetModifyEventHandler>();

    private final OverlayWidget overlayWidget;

    public OverlayWidgetModifyEvent(OverlayWidget overlayWidget) {
        this.overlayWidget = overlayWidget;
    }

    @Override
    public Type<OverlayWidgetModifyEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(OverlayWidgetModifyEventHandler handler) {
        handler.onEvent(this);
    }

    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }
}
