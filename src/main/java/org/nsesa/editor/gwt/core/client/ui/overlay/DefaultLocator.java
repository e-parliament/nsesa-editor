package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.ClassUtils;

import java.util.*;

/**
 * Date: 24/09/12 17:19
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultLocator implements Locator {

    protected static final String SPLITTER = " - ";
    protected Set<Class<? extends AmendableWidget>> hiddenAmendableWidgets = new HashSet<Class<? extends AmendableWidget>>();
    protected Set<Class<? extends AmendableWidget>> hideUnderLayingAmendableWidgets = new HashSet<Class<? extends AmendableWidget>>();
    protected Set<Class<? extends AmendableWidget>> showAmendableWidgets = new HashSet<Class<? extends AmendableWidget>>();

    @Override
    public String getLocation(AmendableWidget amendableWidget, String languageIso, boolean childrenIncluded) {
        return getLocation(amendableWidget, null, languageIso, childrenIncluded);
    }

    @Override
    public String getLocation(AmendableWidget amendableWidget, AmendableWidget newChild, String languageIso, boolean childrenIncluded) {
        final StringBuilder location = new StringBuilder();

        final List<AmendableWidget> path = amendableWidget.getParentAmendableWidgets();
        // add the current widget as well (since only the path are retrieved)
        path.add(amendableWidget);
        if (newChild != null)
            path.add(newChild);
        for (final AmendableWidget parent : path) {
            // filter our not just the same classes, but also any parent classes or interfaces
            final Collection<Class<? extends AmendableWidget>> filtered = Collections2.filter(hiddenAmendableWidgets, new Predicate<Class<? extends AmendableWidget>>() {
                @Override
                public boolean apply(Class<? extends AmendableWidget> input) {
                    return !ClassUtils.isAssignableFrom(input.getClass(), parent.getClass());
                }
            });

            if (!filtered.contains(parent.getClass()) || showAmendableWidgets.contains(parent.getClass()))
                location.append(parent.getType()).append(" ").append(getNum(parent)).append(SPLITTER);
            if (hideUnderLayingAmendableWidgets.contains(parent.getClass())) {
                break;
            }
        }
        return location.toString().endsWith(SPLITTER) ? location.substring(0, location.length() - SPLITTER.length()) : location.toString();
    }

    public String getNum(AmendableWidget amendableWidget) {
        final Integer assignedNumber = amendableWidget.getAssignedNumber();
        return assignedNumber != null ? Integer.toString(assignedNumber) : "";
    }

    public void hide(Class<? extends AmendableWidget>... amendableWidgetClasses) {
        hiddenAmendableWidgets.addAll(Arrays.asList(amendableWidgetClasses));
    }

    public void hideUnder(Class<? extends AmendableWidget>... amendableWidgetClasses) {
        hideUnderLayingAmendableWidgets.addAll(Arrays.asList(amendableWidgetClasses));
    }

    public void show(Class<? extends AmendableWidget>... amendableWidgetClasses) {
        showAmendableWidgets.addAll(Arrays.asList(amendableWidgetClasses));
    }
}
