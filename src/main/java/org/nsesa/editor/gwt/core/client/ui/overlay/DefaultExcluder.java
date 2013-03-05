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

import org.nsesa.editor.gwt.core.client.ui.overlay.document.Occurrence;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * This class is the default implementation of <code>Excluder</code> and checks to see whether or not the widgets
 * candidates can be added as children/siblings in the given widget structure.
 *
 * @author <a href="mailto: stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 5/03/13 11:43
 */
public class DefaultExcluder implements Excluder {
    /**
     * Can be the candidate a child of a given parent? A candidate can be added as a child if the given occurrence
     * constraint is not broken, ie there is no possible to add a candidate as a child if the parent already has
     * a child of the same type and namespace and the <code>occurrence</code> allows only one child of that type
     * @param candidate {@link OverlayWidget} candidate
     * @param occurrence {@link Occurrence} of the candidate
     * @param parent {@link OverlayWidget} the parent of the candidate
     * @return True when the candidate is not accepted as a child of the given parent
     */
    @Override
    public boolean excludeChildCandidate(OverlayWidget candidate, Occurrence occurrence, OverlayWidget parent) {
        // strange situation, it should never occur
        if (occurrence.getMaxOccurs() == 0) {
            return true;
        }
        //allows an infinity
        if (occurrence.isUnbounded()) {
            return false;
        }

        int count = 0;
        boolean result = false;
        for (OverlayWidget child : parent.getChildOverlayWidgets()) {
            if (child.getType().equalsIgnoreCase(candidate.getType()) &&
                    child.getNamespaceURI().equalsIgnoreCase(candidate.getNamespaceURI())) {
                //increase the number of childs with the same type and namespace
                count++;
                if (count >= occurrence.getMaxOccurs()) {
                    result = true; // the maximum occurs has been reached
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Can be the candidate a sibling of a given widget? A candidate can be added as a sibling if the given occurrence
     * constraint is not broken, ie there is no possible to add a candidate as a sibling if the parent of the given
     * widget already has a child of the same type and namespace and the <code>occurrence</code> allows only one
     * widget of that type
     * @param candidate {@link OverlayWidget} candidate
     * @param occurrence {@link Occurrence} of the candidate
     * @param widget {@link OverlayWidget} the sibling of the candidate
     * @return True when the candidate is not accepted as a sibling for the existing widget
     */
    @Override
    public boolean excludeSiblingCandidate(OverlayWidget candidate, Occurrence occurrence, OverlayWidget widget) {
        return excludeChildCandidate(candidate, occurrence, widget.getParentOverlayWidget());
    }
}
