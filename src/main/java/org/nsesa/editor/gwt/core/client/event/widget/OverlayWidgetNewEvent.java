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
 * An event indicating a request to delete a given overlay widget.
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 */
public class OverlayWidgetNewEvent extends GwtEvent<OverlayWidgetNewEventHandler> {

    public static final Type<OverlayWidgetNewEventHandler> TYPE = new Type<OverlayWidgetNewEventHandler>();

    private final OverlayWidget overlayWidget;
    private final OverlayWidget reference;
    private final OverlayWidget child;
    private final int position;

    public OverlayWidgetNewEvent(final OverlayWidget overlayWidget, final OverlayWidget reference, final OverlayWidget child, int position) {
        this.overlayWidget = overlayWidget;
        this.child = child;
        this.reference = reference;
        this.position = position;
    }

    @Override
    public Type<OverlayWidgetNewEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(OverlayWidgetNewEventHandler handler) {
        handler.onEvent(this);
    }

    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }

    public OverlayWidget getChild() {
        return child;
    }

    public int getPosition() {
        return position;
    }

    public OverlayWidget getReference() {
        return reference;
    }
}
