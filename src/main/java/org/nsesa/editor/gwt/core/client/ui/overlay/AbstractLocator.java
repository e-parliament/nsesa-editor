package org.nsesa.editor.gwt.core.client.ui.overlay;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Date: 24/09/12 17:19
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public abstract class AbstractLocator implements Locator {

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
            return Integer.toString(amendableWidget.getParentAmendableWidget().getChildAmendableWidgets().indexOf(amendableWidget) + 1);
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
