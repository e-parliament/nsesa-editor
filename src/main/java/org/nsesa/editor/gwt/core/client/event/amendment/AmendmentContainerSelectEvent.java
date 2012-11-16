package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerSelectEvent extends GwtEvent<AmendmentContainerSelectEventHandler> {

    public static final Type<AmendmentContainerSelectEventHandler> TYPE = new Type<AmendmentContainerSelectEventHandler>();

    private final AmendableWidget amendableWidget;

    public AmendmentContainerSelectEvent(AmendableWidget amendableWidget) {
        this.amendableWidget = amendableWidget;
    }

    @Override
    public Type<AmendmentContainerSelectEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerSelectEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendableWidget getAmendableWidget() {
        return amendableWidget;
    }
}
