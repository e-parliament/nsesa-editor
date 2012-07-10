package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerEditEvent extends GwtEvent<AmendmentContainerEditEventHandler> {

    public static Type<AmendmentContainerEditEventHandler> TYPE = new Type<AmendmentContainerEditEventHandler>();

    private final AmendmentContainerDTO amendment;

    public AmendmentContainerEditEvent(AmendmentContainerDTO amendment) {
        this.amendment = amendment;
    }

    @Override
    public Type<AmendmentContainerEditEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerEditEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentContainerDTO getAmendment() {
        return amendment;
    }
}