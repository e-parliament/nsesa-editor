package org.nsesa.editor.gwt.editor.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerEditEvent extends GwtEvent<AmendmentContainerEditEventHandler> {

    public static Type<AmendmentContainerEditEventHandler> TYPE = new Type<AmendmentContainerEditEventHandler>();

    private final AmendmentController amendmentController;

    public AmendmentContainerEditEvent(AmendmentController amendmentController) {
        this.amendmentController = amendmentController;
    }

    @Override
    public Type<AmendmentContainerEditEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerEditEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentController getAmendmentController() {
        return amendmentController;
    }
}
