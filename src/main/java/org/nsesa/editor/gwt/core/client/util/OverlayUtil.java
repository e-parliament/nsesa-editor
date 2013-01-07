package org.nsesa.editor.gwt.core.client.util;

import org.nsesa.editor.gwt.core.client.amendment.AmendableWidgetWalker;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.select.Selector;

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
        if (path.startsWith("//")) {
            // xpath-like expression ...
            Selector selector = new Selector();
            injectionPoints.addAll(selector.select(path.substring(2), root));
        } else {
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
        }
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
