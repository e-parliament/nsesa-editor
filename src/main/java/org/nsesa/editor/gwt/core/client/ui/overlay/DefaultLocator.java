/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
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
 * <p/>
 * Default implementation for {@link Locator}.
 * Date: 24/09/12 17:19
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
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
        // our location string
        final StringBuilder location = new StringBuilder();

        // splitter for each part of the location string
        final String splitter = getSplitter(languageIso);

        // loop over the widgets - sorted by the root, down to the grandparent, parent, and actual widget
        for (final OverlayWidget aw : path) {
            // filter out not just the same classes, but also any parent classes (no interfaces - not supported in GWT)
            final Collection<Class<? extends OverlayWidget>> filtered = Collections2.filter(hiddenAmendableWidgets, new Predicate<Class<? extends OverlayWidget>>() {
                @Override
                public boolean apply(Class<? extends OverlayWidget> input) {
                    return !ClassUtils.isAssignableFrom(input.getClass(), aw.getClass());
                }
            });

            if (!filtered.contains(aw.getClass()) || showAmendableWidgets.contains(aw.getClass())) {

                if (aw.getParentOverlayWidget() == null) {
                    location.append(getRootNotation(aw, languageIso));
                } else {

                    // check if there is a sub notation going on (point - point would become point - subpoint)
                    if (aw.getParentOverlayWidget().getType().equalsIgnoreCase(aw.getType())) {
                        // we've got a sub notation!
                        location.append(getSubNotation(aw, languageIso));
                    } else {
                        // default notation
                        location.append(getNotation(aw, languageIso));
                    }

                    final String num = getNum(aw, languageIso);
                    if (num != null && !("".equals(num.trim()))) {
                        location.append(" ").append(num);
                    }
                }
                location.append(splitter);
            }
            if (hideUnderLayingAmendableWidgets.contains(aw.getClass())) {
                break;
            }
        }
        // drop the splitter at the end
        final String locationString = location.toString().endsWith(splitter) ? location.substring(0, location.length() - splitter.length()) : location.toString();
        return locationString.trim();
    }

    /**
     * Get the number for a given <tt>overlayWidget</tt>. The reported number depends various cases, but can be thought
     * of in general that if the {@link NumberingType} is constant, the {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget#getTypeIndex()}
     * will instead be used to remove confusion about the path.
     *
     * @param overlayWidget the overlay widget to get the number for
     * @param languageIso   the ISO code of the language
     * @return the number, should never return <tt>null</tt>
     */
    @Override
    public String getNum(final OverlayWidget overlayWidget, final String languageIso) {
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
            return index + " " + getNewNotation(languageIso);
        } else {
            // see if we can extract the index
            final NumberingType numberingType = overlayWidget.getNumberingType();
            if (numberingType != null) {
                if (!numberingType.isConstant()) {
                    final String unformattedIndex = overlayWidget.getUnformattedIndex();
                    if (unformattedIndex != null) {
                        return unformattedIndex;
                    }
                }
            }
            return Integer.toString(overlayWidget.getTypeIndex() + 1);
        }
    }

    /**
     * Returns the used splitter that is injected between overlay widgets in the path calculation.
     *
     * @param languageIso the ISO code of the language
     * @return the splitter, defaults to {@link #SPLITTER}
     */
    public String getSplitter(final String languageIso) {
        return SPLITTER;
    }

    /**
     * Get the notation for a root overlay widget (meaning {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget#getParentOverlayWidget()}
     * returns <tt>null</tt>. By default, this returns the type without any number, since we only have one root widget in our path.
     *
     * @param overlayWidget the root overlay widget
     * @param languageIso   the ISO code of the language
     * @return the notation for this root widget
     */
    public String getRootNotation(final OverlayWidget overlayWidget, final String languageIso) {
        // skip the num for the root
        return getNotation(overlayWidget, languageIso);
    }

    /**
     * Default notation for a given {@link OverlayWidget} <tt>overlayWidget</tt>, using the type.
     *
     * @param overlayWidget the overlay widget
     * @param languageIso   the ISO code of the language
     * @return the location string for a single {@link OverlayWidget}
     */
    public String getNotation(final OverlayWidget overlayWidget, final String languageIso) {
        return overlayWidget.getType() != null ? overlayWidget.getType() : "?";
    }

    /**
     * Default 'sub' notation when an {@link OverlayWidget} has a parent of the same type.
     *
     * @param overlayWidget the overlay widget
     * @param languageIso   the ISO code of the language
     * @return the sub notation for this widget
     */
    public String getSubNotation(final OverlayWidget overlayWidget, final String languageIso) {
        return "sub" + getNotation(overlayWidget, languageIso);
    }

    /**
     * Default 'new' notation for an {@link OverlayWidget} that has been introduced by an
     * {@link org.nsesa.editor.gwt.amendment.client.amendment.AmendmentInjectionPointProvider}. Returns '(new)'.
     *
     * @param languageIso the ISO code of the language
     * @return the new notation
     */
    public String getNewNotation(final String languageIso) {
        return "(new)";
    }

    /**
     * Helper method to ease the adding of overlay widget classes to hide themselves.
     *
     * @param overlayWidgetClasses the overlay widget classes and subclasses to hide
     */
    public void hide(final Class<? extends OverlayWidget>... overlayWidgetClasses) {
        hiddenAmendableWidgets.addAll(Arrays.asList(overlayWidgetClasses));
    }

    /**
     * Helper method to ease the adding of overlay widget classes to hide their children.
     *
     * @param overlayWidgetClasses the overlay widget classes and subclasses to hide their children
     */
    public void hideUnder(final Class<? extends OverlayWidget>... overlayWidgetClasses) {
        hideUnderLayingAmendableWidgets.addAll(Arrays.asList(overlayWidgetClasses));
    }

    /**
     * Helper method to ease the adding of overlay widget classes to always show if
     * for example their superclass has been added in the {@link #hiddenAmendableWidgets} list.
     *
     * @param overlayWidgetClasses the overlay widget classes and subclasses to show.
     */
    public void show(final Class<? extends OverlayWidget>... overlayWidgetClasses) {
        showAmendableWidgets.addAll(Arrays.asList(overlayWidgetClasses));
    }
}
