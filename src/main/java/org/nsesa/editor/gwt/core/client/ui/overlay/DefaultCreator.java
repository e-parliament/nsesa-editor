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

import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Occurrence;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

import java.util.*;

/**
 * Default implementation of the {@link Creator}.
 * Date: 08/11/12 11:23
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultCreator implements Creator {

    protected Excluder excluder;

    /**
     * Create <code>DefaultCreator</code> with a {@link Excluder} setup
     */
    @Inject
    public DefaultCreator(Excluder excluder) {
        this.excluder = excluder;
    }
    /**
     * Returns a map of the allowed siblings with their occurrence for a given {@link OverlayWidget} <tt>overlayWidget</tt>.
     * This implementation returns all allowed siblings according to this overlay widget's parent's
     * {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget#getAllowedChildTypes()}.
     *
     * @param documentController the document controller
     * @param overlayWidget      the overlay widget to get the allowed siblings for
     * @return the allowed siblings
     */
    @Override
    public LinkedHashMap<OverlayWidget, Occurrence> getAllowedSiblings(final DocumentController documentController, final OverlayWidget overlayWidget) {
        return getAllowedChildren(documentController, overlayWidget.getParentOverlayWidget());
    }

    /**
     * Returns a map of allowed child types and their occurrences for a given {@link OverlayWidget} <tt>overlayWidget</tt>.
     * This implementation returns all allowed child types according to
     * {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget#getAllowedChildTypes()}.
     *
     * @param documentController the document controller
     * @param overlayWidget      the overlay widget to get the children for
     * @return the allowed children, sorted in alphabetical order
     */
    @Override
    public LinkedHashMap<OverlayWidget, Occurrence> getAllowedChildren(final DocumentController documentController, final OverlayWidget overlayWidget) {
        final LinkedHashMap<OverlayWidget, Occurrence> allowedChildren = new LinkedHashMap<OverlayWidget, Occurrence>();
        final Map<OverlayWidget, Occurrence> allowedTypes = overlayWidget.getAllowedChildTypes();
        List<Map.Entry<OverlayWidget, Occurrence>> list = new ArrayList<Map.Entry<OverlayWidget, Occurrence>>(allowedTypes.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<OverlayWidget, Occurrence>>() {
            @Override
            public int compare(Map.Entry<OverlayWidget, Occurrence> o1, Map.Entry<OverlayWidget, Occurrence> o2) {
                return o1.getKey().getType().compareTo(o2.getKey().getType());
            }
        });
        for (final Map.Entry<OverlayWidget, Occurrence> allowedType : list) {
            // check the exclusion
            if (!excluder.excludeChildCandidate(allowedType.getKey(), allowedType.getValue(), overlayWidget)) {
                allowedChildren.put(allowedType.getKey(), allowedType.getValue());
            }
        }
        return allowedChildren;
    }
}
