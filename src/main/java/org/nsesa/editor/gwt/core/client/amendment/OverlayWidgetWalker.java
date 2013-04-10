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
package org.nsesa.editor.gwt.core.client.amendment;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * Interface to support the Visitor Pattern. Allows walking the tree that is made up of {@link OverlayWidget}s.
 * Date: 07/07/12 23:21
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface OverlayWidgetWalker {

    /**
     * Walks a tree created by traversing this {@link OverlayWidget} with a given visitor <tt>visitor</tt>.
     * @param visitor the visitor
     */
    void walk(OverlayWidgetVisitor visitor);

    /**
     * AmendableVisitor interface (see Visitor Pattern). Used in combination with the {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget#walk(org.nsesa.editor.gwt.core.client.amendment.OverlayWidgetWalker.OverlayWidgetVisitor)}
     * method to visit each node in the tree of {@link OverlayWidget}s.
     */
    public static interface OverlayWidgetVisitor {
        /**
         * Callback method for each {@link OverlayWidget} that is visited.
         *
         * @param visited the visited {@link OverlayWidget}
         * @return true if the {@link OverlayWidgetVisitor} should go deeper (aka. visit the children of this node).
         */
        boolean visit(OverlayWidget visited);
    }
}
