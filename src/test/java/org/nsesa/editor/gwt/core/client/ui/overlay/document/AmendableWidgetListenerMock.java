package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

/**
 * Date: 18/01/13 15:13
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendableWidgetListenerMock implements AmendableWidgetListener {
    @Override
    public boolean beforeAmendmentControllerAdded(AmendableWidget amendableWidget, AmendmentController amendmentController) {
        return false;
    }

    @Override
    public void afterAmendmentControllerAdded(AmendableWidget amendableWidget, AmendmentController amendmentController) {
    }

    @Override
    public boolean beforeAmendmentControllerRemoved(AmendableWidget amendableWidget, AmendmentController amendmentController) {
        return false;
    }

    @Override
    public void afterAmendmentControllerRemoved(AmendableWidget amendableWidget, AmendmentController amendmentController) {
    }

    @Override
    public boolean beforeAmendableWidgetAdded(AmendableWidget amendableWidget, AmendableWidget child) {
        return false;
    }

    @Override
    public void afterAmendableWidgetAdded(AmendableWidget amendableWidget, AmendableWidget child) {

    }

    @Override
    public boolean beforeAmendableWidgetRemoved(AmendableWidget amendableWidget, AmendableWidget child) {
        return false;
    }

    @Override
    public void afterAmendableWidgetRemoved(AmendableWidget amendableWidget, AmendableWidget child) {
    }
}
