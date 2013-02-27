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
 * TODO internationalize and correct the signatures of the various helper methods
 *
 * Default implementation for {@link Locator}.
 * Date: 24/09/12 17:19
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultLocator implements Locator {

    /**
     * Splitter string that will be added in between each {@link OverlayWidget} in the path.
     */
    protected static final String SPLITTER = " – ";

    /**
     * Set of classes that should not be taken into account when calculating the location. Subclass-aware.
     */
    protected Set<Class<? extends OverlayWidget>> hiddenAmendableWidgets = new HashSet<Class<? extends OverlayWidget>>();

    /**
     * Set of classes that should stop the path calculation on the current level by hiding their children. Subclass-aware.
     */
    protected Set<Class<? extends OverlayWidget>> hideUnderLayingAmendableWidgets = new HashSet<Class<? extends OverlayWidget>>();

    /**
     * Opposite of the {@link #hiddenAmendableWidgets}. All classes in here will definitely be shown. Subclass-aware.
     */
    protected Set<Class<? extends OverlayWidget>> showAmendableWidgets = new HashSet<Class<? extends OverlayWidget>>();

    @Override
    public String getLocation(final OverlayWidget overlayWidget, final String languageIso, final boolean childrenIncluded) {
        return getLocation(overlayWidget, null, languageIso, childrenIncluded);
    }

    @Override
    public String getLocation(final OverlayWidget parentOverlayWidget, final OverlayWidget newChild, final String languageIso, final boolean childrenIncluded) {

        if (parentOverlayWidget == null) return null;

        final List<OverlayWidget> path = parentOverlayWidget.getParentOverlayWidgets();
        // add the current widget as well (since only the path is retrieved)
        path.add(parentOverlayWidget);
        if (newChild != null) {
            path.add(newChild);
        }

        final StringBuilder location = new StringBuilder();

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
        // drop the splitter at the end
        final String locationString = location.toString().endsWith(getSplitter()) ? location.substring(0, location.length() - getSplitter().length()) : location.toString();
        return locationString.trim();
    }

    /**
     * Get the number for a given <tt>overlayWidget</tt>. The reported number depends various cases, but can be thought
     * of in general that if the {@link NumberingType} is constant, the {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget#getTypeIndex()}
     * will instead be used to remove confusion about the path.
     *
     * @param overlayWidget the overlay widget to get the number for
     * @return the number, should never return <tt>null</tt>
     */
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

    /**
     * Returns the used splitter that is injected between overlay widgets in the path calculation.
     * @return the splitter, defaults to {@link #SPLITTER}
     */
    public String getSplitter() {
        return SPLITTER;
    }

    /**
     * Get the notation for a root overlay widget (meaning {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget#getParentOverlayWidget()}
     * returns <tt>null</tt>. By default, this returns the type without any number, since we only have one root widget in our path.
     * @param overlayWidget the root overlay widget
     * @return the notation for this root widget
     */
    public String getRootNotation(final OverlayWidget overlayWidget) {
        // skip the num for the root
        return getNotation(overlayWidget);
    }

    /**
     * Default notation for a given {@link OverlayWidget} <tt>overlayWidget</tt>, using the type.
     * @param overlayWidget the overlay widget
     * @return  the location string for a single {@link OverlayWidget}
     */
    public String getNotation(final OverlayWidget overlayWidget) {
        return overlayWidget.getType() != null ? overlayWidget.getType() : "?";
    }

    /**
     * Default 'sub' notation when an {@link OverlayWidget} has a parent of the same type.
     * @param overlayWidget the overlay widget
     * @return the sub notation for this widget
     */
    public String getSubNotation(final OverlayWidget overlayWidget) {
        return "sub" + getNotation(overlayWidget);
    }

    /**
     * Default 'new' notation for an {@link OverlayWidget} that has been introduced by an
     * {@link org.nsesa.editor.gwt.core.client.amendment.AmendmentInjectionPointProvider}. Returns '(new)'.
     * @return the new notation
     */
    public String getNewNotation() {
        return "(new)";
    }

    /**
     * Helper method to ease the adding of overlay widget classes to hide themselves.
     * @param overlayWidgetClasses the overlay widget classes and subclasses to hide
     */
    public void hide(final Class<? extends OverlayWidget>... overlayWidgetClasses) {
        hiddenAmendableWidgets.addAll(Arrays.asList(overlayWidgetClasses));
    }

    /**
     * Helper method to ease the adding of overlay widget classes to hide their children.
     * @param overlayWidgetClasses the overlay widget classes and subclasses to hide their children
     */
    public void hideUnder(final Class<? extends OverlayWidget>... overlayWidgetClasses) {
        hideUnderLayingAmendableWidgets.addAll(Arrays.asList(overlayWidgetClasses));
    }

    /**
     * Helper method to ease the adding of overlay widget classes to always show if
     * for example their superclass has been added in the {@link #hiddenAmendableWidgets} list.
     * @param overlayWidgetClasses the overlay widget classes and subclasses to show.
     */
    public void show(final Class<? extends OverlayWidget>... overlayWidgetClasses) {
        showAmendableWidgets.addAll(Arrays.asList(overlayWidgetClasses));
    }
}
