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
package org.nsesa.editor.gwt.core.client.event.visualstructure;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * An event occurred when the user wants to add a new overlay widget into editor area.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 17/01/13 15:24
 */
public class VisualStructureInsertionEvent extends GwtEvent<VisualStructureInsertionEventHandler> {

    public static final Type<VisualStructureInsertionEventHandler> TYPE = new Type<VisualStructureInsertionEventHandler>();

    private OverlayWidget overlayWidget;

    public VisualStructureInsertionEvent(OverlayWidget overlayWidget) {
        this.overlayWidget = overlayWidget;
    }

    @Override
    public Type<VisualStructureInsertionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VisualStructureInsertionEventHandler handler) {
        handler.onEvent(this);
    }

    /**
     * The insertion element
     * @return The insertion element as <code>OverlayWidget</code>
     */
    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }
}
