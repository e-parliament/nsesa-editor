package org.nsesa.editor.gwt.core.client.amendment;

import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.Counter;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 30/11/12 11:31
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultAmendmentInjectionPointFinder implements AmendmentInjectionPointFinder {
    @Override
    public List<AmendableWidget> findInjectionPoints(final AmendmentController amendmentController, final AmendableWidget root, final DocumentController documentController) {
        final List<AmendableWidget> injectionPoints = new ArrayList<AmendableWidget>();
        final String path = amendmentController.getAmendment().getSourceReference().getPath();
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
        documentController.walk(root, visitor);
        return injectionPoints;
    }

    @Override
    public String getInjectionPoint(final AmendableWidget amendableWidget) {
        if (amendableWidget.getId() != null && !"".equals(amendableWidget.getId())) {
            // easy!
            return "#" + amendableWidget.getId();
        }

        // damn, no id - we need an xpath-like expression ...
        final StringBuilder sb = new StringBuilder("//");
        final List<AmendableWidget> parentAmendableWidgets = amendableWidget.getParentAmendableWidgets();
        parentAmendableWidgets.add(amendableWidget);
        for (final AmendableWidget parent : parentAmendableWidgets) {
            if (!parent.isIntroducedByAnAmendment()) {
                sb.append(parent.getType());
                final int typeIndex = parent.getTypeIndex();
                // note: type index will be -1 for the root node
                sb.append("[").append(typeIndex != -1 ? typeIndex : 0).append("]");
                if (parentAmendableWidgets.indexOf(parent) < parentAmendableWidgets.size() - 1) {
                    sb.append("/");
                }
            }
        }
        return sb.toString();
    }
}
