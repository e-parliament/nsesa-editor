package org.nsesa.editor.gwt.editor.client.event.amendments;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: groza
 * Date: 29/11/12
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class AmendmentsActionEvent extends GwtEvent<AmendmentsActionEventHandler> {
    public static final Type<AmendmentsActionEventHandler> TYPE = new Type<AmendmentsActionEventHandler>();
    private AmendmentsAction action;

    public AmendmentsActionEvent(AmendmentsAction action) {
        this.action = action;
    }

    @Override
    public Type<AmendmentsActionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentsActionEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentsAction getAction() {
        return action;
    }
}
