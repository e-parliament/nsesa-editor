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

    protected static final String SPLITTER = " – ";
    protected Set<Class<? extends AmendableWidget>> hiddenAmendableWidgets = new HashSet<Class<? extends AmendableWidget>>();
    protected Set<Class<? extends AmendableWidget>> hideUnderLayingAmendableWidgets = new HashSet<Class<? extends AmendableWidget>>();
    protected Set<Class<? extends AmendableWidget>> showAmendableWidgets = new HashSet<Class<? extends AmendableWidget>>();

    @Override
    public String getLocation(final AmendableWidget amendableWidget, final String languageIso, final boolean childrenIncluded) {
        return getLocation(amendableWidget, null, languageIso, childrenIncluded);
    }

    @Override
    public String getLocation(final AmendableWidget amendableWidget, final AmendableWidget newChild, final String languageIso, final boolean childrenIncluded) {

        if (amendableWidget == null) return null;

        final StringBuilder location = new StringBuilder();

        final List<AmendableWidget> path = amendableWidget.getParentAmendableWidgets();
        // add the current widget as well (since only the path are retrieved)
        path.add(amendableWidget);
        if (newChild != null)
            path.add(newChild);
        for (final AmendableWidget aw : path) {
            // filter our not just the same classes, but also any parent classes or interfaces
            final Collection<Class<? extends AmendableWidget>> filtered = Collections2.filter(hiddenAmendableWidgets, new Predicate<Class<? extends AmendableWidget>>() {
                @Override
                public boolean apply(Class<? extends AmendableWidget> input) {
                    return !ClassUtils.isAssignableFrom(input.getClass(), aw.getClass());
                }
            });

            if (!filtered.contains(aw.getClass()) || showAmendableWidgets.contains(aw.getClass())) {
                final StringBuilder sb = location;

                if (aw.getParentAmendableWidget() == null) {
                    sb.append(getRootNotation(aw));
                } else {

                    // check if there is a sub notation going on (point - point would become point - subpoint)
                    if (aw.getParentAmendableWidget().getType().equalsIgnoreCase(aw.getType())) {
                        // we've got a sub notation!
                        sb.append(getSubNotation(aw));
                    } else {
                        // default notation
                        sb.append(getNotation(aw));
                    }

                    final String num = getNum(aw);
                    if (num != null && !("".equals(num.trim()))) {
                        sb.append(" ");
                        sb.append(num);
                    }
                }
                sb.append(getSplitter());
            }
            if (hideUnderLayingAmendableWidgets.contains(aw.getClass())) {
                break;
            }
        }
        final String locationString = location.toString().endsWith(getSplitter()) ? location.substring(0, location.length() - getSplitter().length()) : location.toString();
        return locationString.trim();
    }

    public String getNum(final AmendableWidget amendableWidget) {
        String index;
        if (amendableWidget.isIntroducedByAnAmendment()) {
            AmendableWidget previous = amendableWidget.getPreviousNonIntroducedAmendableWidget(true);
            if (previous == null) {
                // no previous amendable widget ... check if we're perhaps moved before any existing ones?
                AmendableWidget next = amendableWidget.getNextNonIntroducedAmendableWidget(true);
                if (next == null) {
                    // we're in an all new collection (meaning all sibling amendable widgets are introduced by amendments)
                    index = Integer.toString(amendableWidget.getTypeIndex(true) + 1);
                } else {
                    // we have an amendable widget that has not been introduced by an amendment
                    // this means our offset will be negative (-1)
                    // and the additional index will be defined on the place of the amendment (eg. a, b, c, ...)
                    index = "-1" + NumberingType.LETTER.get(amendableWidget.getTypeIndex(true));
                }
            } else {
                // we have a previous amendable widget that has not been introduced by an amendment.
                // this means we'll take the same index
                // and the additional index will be defined on the place of the amendment (eg. a, b, c, ...)
                String previousIndex = previous.getUnformattedIndex() != null ? previous.getUnformattedIndex() : Integer.toString(previous.getTypeIndex() + 1);
                int offset = amendableWidget.getTypeIndex(true) - previous.getTypeIndex();
                previousIndex += NumberingType.LETTER.get(offset - 1);
                index = previousIndex;
            }
            return index + " " + getNewNotation();
        } else {
            // see if we can extract the index
            final NumberingType numberingType = amendableWidget.getNumberingType();
            if (numberingType != null) {
                if (!numberingType.isConstant()) {
                    return amendableWidget.getUnformattedIndex();
                }
            }
            return Integer.toString(amendableWidget.getTypeIndex() + 1);
        }
    }

    public String getSplitter() {
        return SPLITTER;
    }

    public String getRootNotation(final AmendableWidget amendableWidget) {
        // skip the num for the root
        return getNotation(amendableWidget);
    }

    public String getNotation(final AmendableWidget amendableWidget) {
        return amendableWidget.getType() != null ? amendableWidget.getType() : "?";
    }

    public String getSubNotation(final AmendableWidget amendableWidget) {
        return "sub" + getNotation(amendableWidget);
    }

    public String getNewNotation() {
        return "(new)";
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
