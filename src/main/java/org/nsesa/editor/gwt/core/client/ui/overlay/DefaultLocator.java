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
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetSelector;
import org.nsesa.editor.gwt.core.client.util.ClassUtils;
import org.nsesa.editor.gwt.core.client.util.Counter;

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

        if (overlayWidget == null) return null;

        final List<OverlayWidget> path = overlayWidget.getParentOverlayWidgets();
        // add the current widget as well (since only the path is retrieved)
        path.add(overlayWidget);
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

                    final String num = getNum(aw, languageIso, false);
                    if (num != null && !("".equals(num.trim()))) {
                        location.append(" ").append(num);
                        // in the location we always add the new notation if necessary
                        if (aw.isIntroducedByAnAmendment()) location.append(" ").append(getNewNotation(languageIso));
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
    public String getNum(final OverlayWidget overlayWidget, final String languageIso, boolean format) {
        String index;
        if (overlayWidget.isIntroducedByAnAmendment()) {
            final OverlayWidget previous = overlayWidget.getPreviousSibling(new OverlayWidgetSelector() {
                @Override
                public boolean select(OverlayWidget toSelect) {
                    return !toSelect.isIntroducedByAnAmendment() && overlayWidget.getType().equalsIgnoreCase(toSelect.getType());
                }
            });
            if (previous == null) {
                // no previous amendable widget ... check if we're perhaps moved before any existing ones?
                OverlayWidget next = overlayWidget.getNextSibling(new OverlayWidgetSelector() {
                    @Override
                    public boolean select(OverlayWidget toSelect) {
                        return !toSelect.isIntroducedByAnAmendment() && overlayWidget.getType().equalsIgnoreCase(toSelect.getType());
                    }
                });
                if (next == null) {
                    // we're in an all new collection (meaning all sibling amendable widgets are introduced by amendments)
                    index = Integer.toString(overlayWidget.getTypeIndex(true) + 1);
                    if (format) {
                        if (overlayWidget.getFormat() != null) {
                            if (overlayWidget.getNumberingType() == NumberingType.NONE) {
                                index = "";
                            } else {
                                index = overlayWidget.getFormat().format(index);
                            }
                        }
                    }
                } else {
                    // we have an amendable widget that has not been introduced by an amendment
                    // this means our offset will be negative (-1)
                    // and the additional index will be defined on the place of the amendment (eg. a, b, c, ...)
                    index = "-1" + NumberingType.LETTER.get(overlayWidget.getTypeIndex(true));

                    if (format) {
                        if (next.getFormat() != null) {
                            if (next.getNumberingType() == NumberingType.NONE) {
                                index = "";
                            } else {
                                index = next.getFormat().format(index);
                            }
                        }
                    }
                }
            } else {
                // we have a previous amendable widget that has not been introduced by an amendment.
                // this means we'll take the same index
                // and the additional index will be defined on the place of the amendment (eg. a, b, c, ...)
                String previousIndex = previous.getUnformattedIndex() != null ? previous.getUnformattedIndex() : Integer.toString(previous.getTypeIndex() + 1);

                Counter counter = null;
                for (OverlayWidget child : previous.getParentOverlayWidget().getChildOverlayWidgets()) {
                    if (child == previous) {
                        // start
                        counter = new Counter();
                    }
                    if (child == overlayWidget) {
                        break;
                    }
                    if (counter != null) {
                        if (child.getType().equalsIgnoreCase(overlayWidget.getType())) {
                            counter.increment();
                        }
                    }
                }

                assert counter != null;
                int offset = counter.get();
                previousIndex += NumberingType.LETTER.get(offset - 1);
                index = previousIndex;
                if (format) {
                    if (previous.getFormat() != null) {
                        if (previous.getNumberingType() == NumberingType.NONE) {
                            index = "";
                        } else {
                            index = previous.getFormat().format(index);
                        }
                    }
                }
            }
            return index;
        } else {
            // see if we can extract the index
            final NumberingType numberingType = overlayWidget.getNumberingType();
            if (numberingType != null) {
                if (!numberingType.isConstant()) {
                    index = format ? overlayWidget.getFormattedIndex() : overlayWidget.getUnformattedIndex();
                    if (index != null) {
                        return index;
                    }
                }
            }
            return Integer.toString(overlayWidget.getTypeIndex() + 1);
        }
    }

    @Override
    public Location[] parse(OverlayWidget root, String location, String languageIso) {
        List<Location> locations = new ArrayList<Location>();
        // assume that the first character in our location string might be capitalized, so normalize it
        location = location.substring(0, 1).toLowerCase() + location.substring(1);

        // split the location
        final String[] locationParts = location.split(getSplitter(languageIso));
        for (String part : locationParts) {
            DefaultLocation defaultLocation = new DefaultLocation();

            // we assume it'll look something like this {type} {index} {suffix} {(new)}
            final String newNotation = getNewNotation(languageIso);
            final boolean isNew = part.endsWith(newNotation);
            defaultLocation.setNew(isNew);
            if (isNew) {
                // strip off the 'new' suffix
                part = part.substring(0, part.length() - newNotation.length()).trim();
            }

            // TODO This is massively simplified ...
            // we're going to assume the new notation will never have spaces (I know, I know ...)
            // split on spaces
            final String[] splitBySpaces = part.split(" ");

            if (splitBySpaces.length == 1) {
                // no spaces, assume we only have a type
                defaultLocation.setType(splitBySpaces[0]);
            } else if (splitBySpaces.length == 2) {
                // must be type
                defaultLocation.setType(splitBySpaces[0]);
                defaultLocation.setIndex(splitBySpaces[1]);
            } else if (splitBySpaces.length == 3) {
                // must be type
                defaultLocation.setType(splitBySpaces[0]);
                defaultLocation.setIndex(splitBySpaces[1] + " " + splitBySpaces[2]);
            } else {
                throw new UnsupportedOperationException("Sorry, not yet implemented");
            }

            locations.add(defaultLocation);
        }
        return locations.toArray(new Location[locations.size()]);
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
