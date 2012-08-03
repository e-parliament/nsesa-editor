package org.nsesa.editor.gwt.editor.client.event.main;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ShowAmendmentsTabEvent extends GwtEvent<ShowAmendmentsTabEventHandler> {

    public static Type<ShowAmendmentsTabEventHandler> TYPE = new Type<ShowAmendmentsTabEventHandler>();

    @Override
    public Type<ShowAmendmentsTabEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowAmendmentsTabEventHandler handler) {
        handler.onEvent(this);
    }
}
