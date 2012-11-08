package org.nsesa.editor.gwt.core.client.ui.overlay;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

import java.util.Iterator;

/**
 * Date: 08/11/12 15:31
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class LocatorUtil {
    public static Integer getAssignedNumber(final AmendableWidget amendableWidget) {
        if (amendableWidget.getParentAmendableWidget() != null) {
            final Iterator<AmendableWidget> iterator = amendableWidget.getParentAmendableWidget().getChildAmendableWidgets().iterator();
            int count = 1;
            while (iterator.hasNext()) {
                AmendableWidget aw = iterator.next();
                if (aw != null) {
                    if (aw.getType().equalsIgnoreCase(amendableWidget.getType())) {
                        count++;
                    }
                    if (aw == amendableWidget) {
                        break;
                    }
                }
            }
            return count;
        }
        return null;
    }
}
