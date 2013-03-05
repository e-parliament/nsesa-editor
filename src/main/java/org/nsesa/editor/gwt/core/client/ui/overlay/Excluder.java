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
