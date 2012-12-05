package org.nsesa.editor.gwt.core.client.ui.overlay;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * Date: 08/11/12 15:31
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class LocatorUtil {
    public static Integer getAssignedNumber(final AmendableWidget amendableWidget) {
        if (amendableWidget.getParentAmendableWidget() != null) {
            return amendableWidget.getTypeIndex() + 1; // assigned numbers are 1-based!
        }
        return null;
    }
}
