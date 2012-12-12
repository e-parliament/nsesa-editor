package org.nsesa.editor.gwt.core.client.util;

import org.nsesa.editor.gwt.core.client.amendment.AmendableWidgetWalker;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 12/12/12 13:06
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class OverlayUtil {
    public static List<AmendableWidget> xpath(final String path, final AmendableWidget root) {
        final List<AmendableWidget> injectionPoints = new ArrayList<AmendableWidget>();
        final AmendableWidgetWalker.AmendableVisitor visitor;
        if (path.startsWith("#")) {
            visitor = new AmendableWidgetWalker.AmendableVisitor() {
                @Override
                public boolean visit(final AmendableWidget visited) {
                    if (visited != null) {
                        if (path.substring(1).equalsIgnoreCase(visited.getId())) {
                            injectionPoints.add(visited);
                        }
                    }
                    return true;
                }
            };
        } else if (path.startsWith("//")) {
            // xpath-like expression ...
            final String[] parts = path.substring(2).split("/"); // strip off '//' at the beginning
            final Counter tracker = new Counter(0);
            visitor = new AmendableWidgetWalker.AmendableVisitor() {
                @Override
                public boolean visit(final AmendableWidget visited) {
                    if (visited != null && tracker.get() < parts.length) {
                        final String part = parts[tracker.get()];
                        final String partType = part.substring(0, part.indexOf("["));
                        if (visited.getType().equalsIgnoreCase(partType)) {
                            // ok, we have a match on the type .. now check our count
                            final int typeIndex = Integer.parseInt(part.substring(part.indexOf("[") + 1, part.length() - 1));
                            if (typeIndex == visited.getTypeIndex()) {
                                // ok, match!
                                tracker.increment();
                            }
                            if (tracker.get() == parts.length) {
                                // found end node!
                                injectionPoints.add(visited);
                                return false;
                            }
                        }
                    }
                    return true;
                }
            };
        } else {
            visitor = new AmendableWidgetWalker.AmendableVisitor() {
                @Override
                public boolean visit(final AmendableWidget visited) {
                    if (visited != null) {
                        if (path.equalsIgnoreCase(visited.getId())) {
                            injectionPoints.add(visited);
                        }
                    }
                    return true;
                }
            };
        }
        root.walk(visitor);
        return injectionPoints;
    }

    public static List<AmendableWidget> find(final String expression, final AmendableWidget root) {
        final List<AmendableWidget> matches = new ArrayList<AmendableWidget>();
        root.walk(new AmendableWidgetWalker.AmendableVisitor() {
            @Override
            public boolean visit(final AmendableWidget visited) {
                // only simple tag names atm
                if (visited.getType().equalsIgnoreCase(expression)) {
                    matches.add(visited);
                }
                return true;
            }
        });
        return matches;
    }
}
