package org.nsesa.editor.gwt.core.client.ui.overlay.document.akomantoso;

import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * Date: 06/07/12 17:24
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AkomaNtoso20Locator implements Locator {
    @Override
    public String getLocation(AmendableWidget amendableWidget, String languageIso, boolean childrenIncluded) {
        StringBuilder location = new StringBuilder();

        final AmendableWidget[] parents = amendableWidget.getParentAmendableWidgets();
        for (AmendableWidget parent : parents) {
            location.append(parent.getType()).append(" - ");
        }
        return location.toString();
    }
}
