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

import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.StructureIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Default implementation of the {@link Creator}.
 * Date: 08/11/12 11:23
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date 12/01/2013 09:45
 */
public class DefaultCreator implements Creator {

    /**
     * Returns a list of the allowed siblings with their occurrence for a given {@link OverlayWidget} <tt>overlayWidget</tt>.
     * This implementation returns all allowed siblings according to this overlay widget's parent's
     * {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget#getStructureIndicator()}.
     *
     * @param documentController the document controller
     * @param overlayWidget      the overlay widget to get the allowed siblings for
     * @return the allowed siblings
     */
    @Override
    public List<OverlayWidget> getAllowedSiblings(final DocumentController documentController, final OverlayWidget overlayWidget) {
        if (overlayWidget.getParentOverlayWidget() != null) {
            return getAllowedChildren(documentController, overlayWidget.getParentOverlayWidget());
        }
        else {
            // probably dealing with a root node
            return new ArrayList<OverlayWidget>();
        }
    }

    /**
     * Returns a list of allowed child types  for a given {@link OverlayWidget} <tt>overlayWidget</tt>.
     * This implementation returns all allowed child types according to
     * {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget#getStructureIndicator()}.
     *
     * @param documentController the document controller
     * @param overlayWidget      the overlay widget to get the children for
     * @return the allowed children, sorted in alphabetical order
     */
    @Override
    public List<OverlayWidget> getAllowedChildren(final DocumentController documentController, final OverlayWidget overlayWidget) {
        final List<OverlayWidget> allowedChildren = getAllowedChildTypes(overlayWidget);
        Collections.sort(allowedChildren, new Comparator<OverlayWidget>() {
            @Override
            public int compare(OverlayWidget o1, OverlayWidget o2) {
                if (o1 == null) return -1;
                if (o2 == null) return 1;
                return o1.getType().compareTo(o2.getType());
            }
        });
        return allowedChildren;
    }

    /**
     * Returns the list of the allowed child types as they are coming from {@link StructureIndicator} structure
     * @return the list of the allowed child types
     */
    private List<OverlayWidget> getAllowedChildTypes(OverlayWidget overlayWidget) {
        List<OverlayWidget> allowedChildren = new ArrayList<OverlayWidget>();
        List<StructureIndicator> stack = new ArrayList<StructureIndicator>();
        stack.add(overlayWidget.getStructureIndicator());
        while (!stack.isEmpty()) {
            StructureIndicator structureIndicator = stack.remove(0);
            if (structureIndicator instanceof StructureIndicator.Element) {
                StructureIndicator.Element elemIndicator = (StructureIndicator.Element) structureIndicator;
                OverlayWidget candidate = elemIndicator.asWidget();
                allowedChildren.add(candidate);
            } else {
                if (structureIndicator.getIndicators() != null ) {
                    stack.addAll(structureIndicator.getIndicators());
                }
            }
        }
        return allowedChildren;
    }
}

