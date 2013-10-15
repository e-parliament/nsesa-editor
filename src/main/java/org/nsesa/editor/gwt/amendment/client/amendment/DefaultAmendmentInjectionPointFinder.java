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

import com.google.inject.Inject;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetInjectionStrategy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetSelector;
import org.nsesa.editor.gwt.core.client.util.OverlayUtil;
import org.nsesa.editor.gwt.core.client.util.UUID;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;

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

    protected final OverlayWidgetInjectionStrategy overlayWidgetInjectionStrategy;

    @Inject
    public DefaultAmendmentInjectionPointFinder(OverlayWidgetInjectionStrategy overlayWidgetInjectionStrategy) {
        this.overlayWidgetInjectionStrategy = overlayWidgetInjectionStrategy;
    }

    /**
     * Finds injection points for amendments based on the {@link org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO#getSourceReference()}.
     *
     * @param amendmentController the controller to find the injection points for
     * @param root                the root overlay widget node
     * @param documentController  the containing document controller
     * @return the list of injection points (that is, overlay widgets which should get the amendment controller)
     */
    @Override
    public List<OverlayWidget> findInjectionPoints(final AmendmentController amendmentController, final OverlayWidget root, final DocumentController documentController) {
        final String path = amendmentController.getModel().getSourceReference().getPath();
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
     *
     * @param reference the overlay widget to find the position expression for
     * @return the injection point expression (xpath like).
     */
    @Override
    public AmendableWidgetReference getInjectionPoint(final OverlayWidget parent, final OverlayWidget reference, final OverlayWidget overlayWidget) {
        AmendableWidgetReference injectionPoint;
        if (overlayWidget.isIntroducedByAnAmendment()) {
            if (overlayWidget.getParentOverlayWidget() != null) {
                // find previous or next
                final OverlayWidget previousSibling = overlayWidget.getPreviousSibling(new OverlayWidgetSelector() {
                    @Override
                    public boolean select(OverlayWidget toSelect) {
                        return !toSelect.isIntroducedByAnAmendment();
                    }
                });
                if (previousSibling != null) {
                    final int i = overlayWidget.getParentOverlayWidget().getChildOverlayWidgets().indexOf(overlayWidget) - previousSibling.getParentOverlayWidget().getChildOverlayWidgets().indexOf(previousSibling);
                    injectionPoint = new AmendableWidgetReference(true, true, findXPathExpressionToOverlayWidget(previousSibling), overlayWidget.getNamespaceURI(), overlayWidget.getType(), i);
                } else {
                    final OverlayWidget nextSibling = overlayWidget.getNextSibling(new OverlayWidgetSelector() {
                        @Override
                        public boolean select(OverlayWidget toSelect) {
                            return !toSelect.isIntroducedByAnAmendment();
                        }
                    });
                    if (nextSibling != null) {
                        final int i = overlayWidget.getParentOverlayWidget().getChildOverlayWidgets().indexOf(overlayWidget) - nextSibling.getParentOverlayWidget().getChildOverlayWidgets().indexOf(nextSibling);
                        injectionPoint = new AmendableWidgetReference(true, true, findXPathExpressionToOverlayWidget(nextSibling), overlayWidget.getNamespaceURI(), overlayWidget.getType(), i);
                    } else {
                        // all new collection
                        final int i = overlayWidget.getParentOverlayWidget().getChildOverlayWidgets().indexOf(overlayWidget);
                        injectionPoint = new AmendableWidgetReference(true, false, findXPathExpressionToOverlayWidget(parent), overlayWidget.getNamespaceURI(), overlayWidget.getType(), i);
                    }
                }

            } else {
                // we're not yet attached to the overlay tree
                if (reference != null) {
                    final String xPath = findXPathExpressionToOverlayWidget(reference);
                    // creation as child or sibling
                    final boolean sibling = parent != reference;
                    final int injectionPosition = overlayWidgetInjectionStrategy.getProposedInjectionPosition(sibling ? reference.getParentOverlayWidget() : reference, reference, overlayWidget);
                    // if we're dealing with a sibling, then we store the offset instead
                    injectionPoint = new AmendableWidgetReference(true, sibling, xPath, overlayWidget.getNamespaceURI(), overlayWidget.getType(),
                            sibling ? (injectionPosition - reference.getParentOverlayWidget().getChildOverlayWidgets().indexOf(reference)) : injectionPosition);
                } else {
                    // no reference? Just add to the last place
                    LOG.warning("Watch out - requesting the injection point for an amendment without a given reference implies adding it as the last overlay widget!");
                    injectionPoint = new AmendableWidgetReference(true, false, findXPathExpressionToOverlayWidget(parent), overlayWidget.getNamespaceURI(), overlayWidget.getType(),
                            parent.getChildOverlayWidgets().size());
                }
            }
        } else {
            // modification or deletion
            // TODO make offset nullable
            final String xPath = findXPathExpressionToOverlayWidget(overlayWidget);
            injectionPoint = new AmendableWidgetReference(false, false, xPath, overlayWidget.getNamespaceURI(), overlayWidget.getType(), -1 /* offset doesn't matter */);
        }

        injectionPoint.setReferenceID(UUID.uuid());

        LOG.info("->> Injection point: " + injectionPoint);
        return injectionPoint;
    }

    protected String findXPathExpressionToOverlayWidget(final OverlayWidget overlayWidget) {
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
                final boolean notLast = parentOverlayWidgets.indexOf(parent) < parentOverlayWidgets.size() - 1;
                if (notLast) {
                    sb.append("/");
                }
            }
        }
        return sb.toString();
    }
}
