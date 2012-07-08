package org.nsesa.editor.gwt.core.client.ui.overlay.document.ep;

import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * Date: 06/07/12 17:24
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EPLocator implements Locator {
    @Override
    public String getLocation(AmendableWidget amendableWidget, String languageIso, boolean childrenIncluded) {
        StringBuilder location = new StringBuilder();

        final AmendableWidget[] parents = amendableWidget.getParents();
        for (AmendableWidget parent : parents) {
            location.append(parent.getType()).append(" - ");
        }
        return location.toString();
    }
}
