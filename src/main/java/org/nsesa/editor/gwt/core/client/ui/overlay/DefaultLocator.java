package org.nsesa.editor.gwt.core.client.ui.overlay;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Date: 24/09/12 17:19
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultLocator implements Locator {

    protected static final String SPLITTER = " - ";
    protected HashSet<Class<? extends AmendableWidget>> hiddenAmendableWidgets = new HashSet<Class<? extends AmendableWidget>>();
    protected HashSet<Class<? extends AmendableWidget>> hideUnderLayingAmendableWidgets = new HashSet<Class<? extends AmendableWidget>>();

    @Override
    public String getLocation(AmendableWidget amendableWidget, String languageIso, boolean childrenIncluded) {
        StringBuilder location = new StringBuilder();

        final ArrayList<AmendableWidget> parents = amendableWidget.getParentAmendableWidgets();
        for (AmendableWidget parent : parents) {
            if (!hiddenAmendableWidgets.contains(parent.getClass()))
                location.append(parent.getType()).append(" ").append(getNum(parent)).append(SPLITTER);
            if (hideUnderLayingAmendableWidgets.contains(parent.getClass())) {
                break;
            }
        }
        return location.toString().endsWith(SPLITTER) ? location.substring(0, location.length() - SPLITTER.length()) : location.toString();
    }

    public String getNum(AmendableWidget amendableWidget) {
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
            return Integer.toString(count);
        }
        return "";
    }

    public void hide(Class<? extends AmendableWidget>... amendableWidgetClasses) {
        hiddenAmendableWidgets.addAll(Arrays.asList(amendableWidgetClasses));
    }

    public void hideUnder(Class<? extends AmendableWidget>... amendableWidgetClasses) {
        hideUnderLayingAmendableWidgets.addAll(Arrays.asList(amendableWidgetClasses));
    }
}
