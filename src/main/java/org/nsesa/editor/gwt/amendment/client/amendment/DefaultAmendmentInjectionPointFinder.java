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
package org.nsesa.editor.gwt.amendment.client.amendment;

import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.util.OverlayUtil;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The default implementation of the {@link AmendmentInjectionPointFinder}. Capable of doing simple lookups based on
 * the {@link OverlayUtil} expression support.
 * Date: 30/11/12 11:31
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultAmendmentInjectionPointFinder implements AmendmentInjectionPointFinder {

    private static final Logger LOG = Logger.getLogger(DefaultAmendmentInjectionPointFinder.class.getName());

    /**
     * Finds injection points for amendments based on the {@link org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO#getSourceReference()}.
     *
     * @param path                  the path to find the injection points for
     * @param root                  the root overlay widget node
     * @param documentController    the containing document controller
     * @return  the list of injection points (that is, overlay widgets which should get the amendment controller)
     */
    @Override
    public List<OverlayWidget> findInjectionPoints(final String path, final OverlayWidget root, final DocumentController documentController) {
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Trying to find nodes matching '" + path + "'");
        }
        final List<OverlayWidget> overlayWidgets = OverlayUtil.xpath(path, root);
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Found nodes " + overlayWidgets);
        }
        return overlayWidgets;
    }

    /**
     * Get the injection point expression for a given <tt>overlayWidget</tt>. This expression uniquely identifies the
     * position within its own branch up to the root.
     * @param overlayWidget the overlay widget to find the position expression for
     * @return the injection point expression (xpath like).
     */
    @Override
    public String getInjectionPoint(final OverlayWidget overlayWidget) {
        if (overlayWidget.getId() != null && !"".equals(overlayWidget.getId())) {
            // easy!
            return "#" + overlayWidget.getId();
        }

        // damn, no id - we need an xpath-like expression ...
        final StringBuilder sb = new StringBuilder("//");
        final List<OverlayWidget> parentOverlayWidgets = overlayWidget.getParentOverlayWidgets();
        parentOverlayWidgets.add(overlayWidget);
        for (final OverlayWidget parent : parentOverlayWidgets) {
            if (!parent.isIntroducedByAnAmendment()) {
                sb.append(parent.getType());
                final int typeIndex = parent.getTypeIndex();
                // note: type index will be -1 for the root node
                sb.append("[").append(typeIndex != -1 ? typeIndex : 0).append("]");
                if (parentOverlayWidgets.indexOf(parent) < parentOverlayWidgets.size() - 1) {
                    sb.append("/");
                }
            }
        }
        return sb.toString();
    }
}
