package org.nsesa.editor.gwt.core.client.amendment;

import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
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
        // not in our cache? Can happen if we inject a single amendment
        final List<AmendableWidget> injectionPoints = new ArrayList<AmendableWidget>();
        final AmendableWidgetWalker.AmendableVisitor visitor = new AmendableWidgetWalker.AmendableVisitor() {
            public AmendableWidget found = null;

            @Override
            public boolean visit(final AmendableWidget visited) {
                if (visited != null && amendmentController.getAmendment().getSourceReference().getElement().equalsIgnoreCase(visited.getId())) {
                    injectionPoints.add(visited);
                }
                return true;
            }

            public AmendableWidget getFound() {
                return found;
            }
        };
        documentController.walk(root, visitor);
        return injectionPoints;
    }
}
