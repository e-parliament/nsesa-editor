package org.nsesa.editor.gwt.core.client.amendment;

import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.OverlayUtil;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.List;
import java.util.logging.Logger;

/**
 * Date: 30/11/12 11:31
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultAmendmentInjectionPointFinder implements AmendmentInjectionPointFinder {

    private static final Logger LOG = Logger.getLogger(DefaultAmendmentInjectionPointFinder.class.getName());

    @Override
    public List<AmendableWidget> findInjectionPoints(final AmendmentController amendmentController, final AmendableWidget root, final DocumentController documentController) {
        final String path = amendmentController.getModel().getSourceReference().getPath();
        LOG.info("Trying to find nodes matching " + path);
        final List<AmendableWidget> amendableWidgets = OverlayUtil.xpath(path, root);
        LOG.info("Found nodes " + amendableWidgets);
        return amendableWidgets;
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
