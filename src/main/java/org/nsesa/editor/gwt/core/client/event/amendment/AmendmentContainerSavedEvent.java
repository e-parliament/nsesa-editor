package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerSavedEvent extends GwtEvent<AmendmentContainerSavedEventHandler> {

    public static final Type<AmendmentContainerSavedEventHandler> TYPE = new Type<AmendmentContainerSavedEventHandler>();

    private final AmendmentController amendmentController;

    public AmendmentContainerSavedEvent(AmendmentController amendmentController) {
        this.amendmentController = amendmentController;
    }


    @Override
    public Type<AmendmentContainerSavedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerSavedEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentController getAmendmentController() {
        return amendmentController;
    }
}
