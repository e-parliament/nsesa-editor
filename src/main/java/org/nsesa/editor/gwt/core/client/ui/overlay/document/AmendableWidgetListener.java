package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

/**
 * Date: 23/10/12 14:15
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendableWidgetListener {
    // amendment operation callbacks

    boolean beforeAmendmentControllerAdded(AmendableWidget amendableWidget, AmendmentController amendmentController);

    void afterAmendmentControllerAdded(AmendableWidget amendableWidget, AmendmentController amendmentController);

    boolean beforeAmendmentControllerRemoved(AmendableWidget amendableWidget, AmendmentController amendmentController);

    void afterAmendmentControllerRemoved(AmendableWidget amendableWidget, AmendmentController amendmentController);

    boolean beforeAmendableWidgetAdded(AmendableWidget amendableWidget, AmendableWidget child);

    void afterAmendableWidgetAdded(AmendableWidget amendableWidget, AmendableWidget child);

    boolean beforeAmendableWidgetRemoved(AmendableWidget amendableWidget, AmendableWidget child);

    void afterAmendableWidgetRemoved(AmendableWidget amendableWidget, AmendableWidget child);
}
