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

    private final OverlayWidget parentOverlayWidget;
    private final OverlayWidget reference;
    private final OverlayWidget child;

    public OverlayWidgetNewEvent(final OverlayWidget parentOverlayWidget, final OverlayWidget reference, final OverlayWidget child) {
        this.parentOverlayWidget = parentOverlayWidget;
        this.child = child;
        this.reference = reference;
    }

    @Override
    public Type<OverlayWidgetNewEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(OverlayWidgetNewEventHandler handler) {
        handler.onEvent(this);
    }

    public OverlayWidget getParentOverlayWidget() {
        return parentOverlayWidget;
    }

    public OverlayWidget getChild() {
        return child;
    }

    public OverlayWidget getReference() {
        return reference;
    }
}
