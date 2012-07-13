package org.nsesa.editor.gwt.dialog.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class CloseDialogEvent extends GwtEvent<CloseDialogEventHandler> {

    public static Type<CloseDialogEventHandler> TYPE = new Type<CloseDialogEventHandler>();

    public CloseDialogEvent() {
    }

    @Override
    public Type<CloseDialogEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CloseDialogEventHandler handler) {
        handler.onEvent(this);
    }
}
