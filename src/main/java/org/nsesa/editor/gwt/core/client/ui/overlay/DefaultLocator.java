/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
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
    protected Set<Class<? extends OverlayWidget>> hiddenAmendableWidgets = new HashSet<Class<? extends OverlayWidget>>();
    protected Set<Class<? extends OverlayWidget>> hideUnderLayingAmendableWidgets = new HashSet<Class<? extends OverlayWidget>>();
    protected Set<Class<? extends OverlayWidget>> showAmendableWidgets = new HashSet<Class<? extends OverlayWidget>>();

    @Override
    public String getLocation(final OverlayWidget overlayWidget, final String languageIso, final boolean childrenIncluded) {
        return getLocation(overlayWidget, null, languageIso, childrenIncluded);
    }

    @Override
    public String getLocation(final OverlayWidget overlayWidget, final OverlayWidget newChild, final String languageIso, final boolean childrenIncluded) {

        if (overlayWidget == null) return null;

        final StringBuilder location = new StringBuilder();

        final List<OverlayWidget> path = overlayWidget.getParentOverlayWidgets();
        // add the current widget as well (since only the path are retrieved)
        path.add(overlayWidget);
        if (newChild != null)
            path.add(newChild);
        for (final OverlayWidget aw : path) {
            // filter our not just the same classes, but also any parent classes or interfaces
            final Collection<Class<? extends OverlayWidget>> filtered = Collections2.filter(hiddenAmendableWidgets, new Predicate<Class<? extends OverlayWidget>>() {
                @Override
                public boolean apply(Class<? extends OverlayWidget> input) {
                    return !ClassUtils.isAssignableFrom(input.getClass(), aw.getClass());
                }
            });

            if (!filtered.contains(aw.getClass()) || showAmendableWidgets.contains(aw.getClass())) {
                final StringBuilder sb = location;

                if (aw.getParentOverlayWidget() == null) {
                    sb.append(getRootNotation(aw));
                } else {

                    // check if there is a sub notation going on (point - point would become point - subpoint)
                    if (aw.getParentOverlayWidget().getType().equalsIgnoreCase(aw.getType())) {
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

    public String getNum(final OverlayWidget overlayWidget) {
        String index;
        if (overlayWidget.isIntroducedByAnAmendment()) {
            OverlayWidget previous = overlayWidget.getPreviousNonIntroducedOverlayWidget(true);
            if (previous == null) {
                // no previous amendable widget ... check if we're perhaps moved before any existing ones?
                OverlayWidget next = overlayWidget.getNextNonIntroducedOverlayWidget(true);
                if (next == null) {
                    // we're in an all new collection (meaning all sibling amendable widgets are introduced by amendments)
                    index = Integer.toString(overlayWidget.getTypeIndex(true) + 1);
                } else {
                    // we have an amendable widget that has not been introduced by an amendment
                    // this means our offset will be negative (-1)
                    // and the additional index will be defined on the place of the amendment (eg. a, b, c, ...)
                    index = "-1" + NumberingType.LETTER.get(overlayWidget.getTypeIndex(true));
                }
            } else {
                // we have a previous amendable widget that has not been introduced by an amendment.
                // this means we'll take the same index
                // and the additional index will be defined on the place of the amendment (eg. a, b, c, ...)
                String previousIndex = previous.getUnformattedIndex() != null ? previous.getUnformattedIndex() : Integer.toString(previous.getTypeIndex() + 1);
                int offset = overlayWidget.getTypeIndex(true) - previous.getTypeIndex();
                previousIndex += NumberingType.LETTER.get(offset - 1);
                index = previousIndex;
            }
            return index + " " + getNewNotation();
        } else {
            // see if we can extract the index
            final NumberingType numberingType = overlayWidget.getNumberingType();
            if (numberingType != null) {
                if (!numberingType.isConstant()) {
                    return overlayWidget.getUnformattedIndex();
                }
            }
            return Integer.toString(overlayWidget.getTypeIndex() + 1);
        }
    }

    public String getSplitter() {
        return SPLITTER;
    }

    public String getRootNotation(final OverlayWidget overlayWidget) {
        // skip the num for the root
        return getNotation(overlayWidget);
    }

    public String getNotation(final OverlayWidget overlayWidget) {
        return overlayWidget.getType() != null ? overlayWidget.getType() : "?";
    }

    public String getSubNotation(final OverlayWidget overlayWidget) {
        return "sub" + getNotation(overlayWidget);
    }

    public String getNewNotation() {
        return "(new)";
    }

    public void hide(Class<? extends OverlayWidget>... amendableWidgetClasses) {
        hiddenAmendableWidgets.addAll(Arrays.asList(amendableWidgetClasses));
    }

    public void hideUnder(Class<? extends OverlayWidget>... amendableWidgetClasses) {
        hideUnderLayingAmendableWidgets.addAll(Arrays.asList(amendableWidgetClasses));
    }

    public void show(Class<? extends OverlayWidget>... amendableWidgetClasses) {
        showAmendableWidgets.addAll(Arrays.asList(amendableWidgetClasses));
    }
}
