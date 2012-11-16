package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerInjectedEvent extends GwtEvent<AmendmentContainerInjectedEventHandler> {

    public static final Type<AmendmentContainerInjectedEventHandler> TYPE = new Type<AmendmentContainerInjectedEventHandler>();

    private final AmendmentController amendmentController;

    public AmendmentContainerInjectedEvent(AmendmentController amendmentController) {
        this.amendmentController = amendmentController;
    }

    @Override
    public Type<AmendmentContainerInjectedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerInjectedEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentController getAmendmentController() {
        return amendmentController;
    }
}
