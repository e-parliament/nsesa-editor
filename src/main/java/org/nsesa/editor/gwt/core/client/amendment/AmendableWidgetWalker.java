package org.nsesa.editor.gwt.core.client.amendment;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * Interface to support the Visitor Pattern. Allows walking the tree that is made up of <tt>AmendableWidget</tt>s.
 * Date: 07/07/12 23:21
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendableWidgetWalker {

    /**
     * Walks a tree created by traversing this <tt>AmendableWidget</tt> with a given visitor <tt>visitor</tt>. This will use a
     * breath-first search using {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget#getChildAmendableWidgets()}.
     * </P>
     * Depending on the visitor's return value from {@link AmendableVisitor#visit(org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget)},
     * we will continue going deeper into the tree's leaves.
     * <p/>
     * Note that when a search is stopped short by the visitor, this will <strong>NOT</strong> prevent the search from
     * visiting the sibling of a node that has already been visited.
     *
     * @param visitor the visitor
     */
    void walk(AmendableVisitor visitor);

    /**
     * AmendableVisitor interface (see Visitor Pattern). Used in combination with the {@link AmendableWidget#walk(org.nsesa.editor.gwt.core.client.amendment.AmendableWidgetWalker.AmendableVisitor)}
     * method to visit each node in the tree of <tt>AmendableWidget</tt>s.
     */
    public static interface AmendableVisitor {
        /**
         * Callback method for each <tt>AmendableWidget</tt> that is visited.
         *
         * @param visited the visited <tt>AmendableWidget</tt>
         * @return true if the <tt>AmendableVisitor</tt> should go deeper (aka. visit the children of this node).
         */
        boolean visit(AmendableWidget visited);
    }
}
