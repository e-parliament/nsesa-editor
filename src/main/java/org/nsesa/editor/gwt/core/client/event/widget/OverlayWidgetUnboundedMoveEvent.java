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
 * An event indicating a movement of an {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget}
 * freely inside the entire document.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 10/04/13 15:10
 */
public class OverlayWidgetUnboundedMoveEvent extends GwtEvent<OverlayWidgetUnboundedMoveEventHandler> {


    public static final Type<OverlayWidgetUnboundedMoveEventHandler> TYPE = new Type<OverlayWidgetUnboundedMoveEventHandler>();
    private final OverlayWidget overlayWidget;

    public OverlayWidgetUnboundedMoveEvent(OverlayWidget overlayWidget) {
        this.overlayWidget = overlayWidget;
    }

    @Override
    public Type<OverlayWidgetUnboundedMoveEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(OverlayWidgetUnboundedMoveEventHandler handler) {
        handler.onEvent(this);
    }

    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }

}
