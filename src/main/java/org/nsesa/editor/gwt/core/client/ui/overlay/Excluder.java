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

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Occurrence;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * This interface is used to check if a candidate overlay widget can be added in the structure of an existing widget
 * as a child or as a sibling.
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 11/01/13 16:24
 */
@ImplementedBy(DefaultExcluder.class)
public interface Excluder {
    /**
     * child candidate widget
     * @param candidate {@link OverlayWidget} candidate
     * @param occurrence {@link Occurrence} of the candidate
     * @param parent {@link OverlayWidget} the parent of the candidate
     * @return True when the candidate is excluded because the structure of the parent widget does not allow
     *              it to be added.
     */
    abstract boolean excludeChildCandidate(OverlayWidget candidate, Occurrence occurrence, OverlayWidget parent);

    /**
     * child candidate widget
     * @param candidate {@link OverlayWidget} candidate
     * @param occurrence {@link Occurrence} of the candidate
     * @param widget {@link OverlayWidget} the sibling of the candidate
     * @return True when the candidate is excluded because the structure of the parent widget does not allow
     *              it to be added.
     */
    abstract boolean excludeSiblingCandidate(OverlayWidget candidate, Occurrence occurrence, OverlayWidget widget);

    /**
     * A special <code>Excluder</code> implementation to not exclude any candidate
     */
    public static class NoneExcluder implements Excluder {
        @Override
        public boolean excludeChildCandidate(OverlayWidget candidate, Occurrence occurrence, OverlayWidget parent) {
            return false;
        }
        @Override
        public boolean excludeSiblingCandidate(OverlayWidget candidate, Occurrence occurrence, OverlayWidget widget) {
            return false;
        }
    }
    /**
     * A special <code>Excluder</code> implementation to exclude all candidates
     */
    public static class AllExcluder implements Excluder {
        @Override
        public boolean excludeChildCandidate(OverlayWidget candidate, Occurrence occurrence, OverlayWidget parent) {
            return true;
        }
        @Override
        public boolean excludeSiblingCandidate(OverlayWidget candidate, Occurrence occurrence, OverlayWidget widget) {
            return true;
        }
    }
}
