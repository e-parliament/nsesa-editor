package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerSaveEvent extends GwtEvent<AmendmentContainerSaveEventHandler> {

    public static final Type<AmendmentContainerSaveEventHandler> TYPE = new Type<AmendmentContainerSaveEventHandler>();

    private final AmendmentContainerDTO[] amendments;

    public AmendmentContainerSaveEvent(AmendmentContainerDTO amendment) {
        this.amendments = new AmendmentContainerDTO[]{amendment};
    }

    public AmendmentContainerSaveEvent(AmendmentContainerDTO[] amendments) {
        this.amendments = amendments;
    }

    @Override
    public Type<AmendmentContainerSaveEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerSaveEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentContainerDTO[] getAmendments() {
        return amendments;
    }
}
