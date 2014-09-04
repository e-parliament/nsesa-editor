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
package org.nsesa.editor.gwt.core.client.util;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * Allows holding one overlay widget. Used to easily extract values from anonymous inner classes (think tree walking).
 * Created by Philip Luppens on 04/09/14 11:11.
 */
public class OverlayWidgetHolder {

    private OverlayWidget overlayWidget;

    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }

    public void setOverlayWidget(OverlayWidget overlayWidget) {
        this.overlayWidget = overlayWidget;
    }
}
