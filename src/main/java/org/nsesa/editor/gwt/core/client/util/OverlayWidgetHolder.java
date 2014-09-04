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
