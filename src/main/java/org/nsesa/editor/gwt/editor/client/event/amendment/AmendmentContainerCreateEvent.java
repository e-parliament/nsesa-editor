package org.nsesa.editor.gwt.editor.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerCreateEvent extends GwtEvent<AmendmentContainerCreateEventHandler> {

    public static Type<AmendmentContainerCreateEventHandler> TYPE = new Type<AmendmentContainerCreateEventHandler>();

    private final AmendableWidget amendableWidget;

    public AmendmentContainerCreateEvent(AmendableWidget amendableWidget) {
        this.amendableWidget = amendableWidget;
    }

    @Override
    public Type<AmendmentContainerCreateEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerCreateEventHandler handler) {
        handler.onEvent(this);
    }


}
