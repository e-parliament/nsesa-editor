package org.nsesa.editor.gwt.core.client.amendment;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * Date: 07/07/12 23:21
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendableWidgetWalker {

    void walk(AmendableVisitor visitor);

    void walk(AmendableWidget root, AmendableVisitor visitor);

    public static interface AmendableVisitor {
        boolean visit(AmendableWidget visited);
    }
}
