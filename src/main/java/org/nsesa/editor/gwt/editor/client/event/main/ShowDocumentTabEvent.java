package org.nsesa.editor.gwt.editor.client.event.main;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ShowDocumentTabEvent extends GwtEvent<ShowDocumentTabEventHandler> {

    public static Type<ShowDocumentTabEventHandler> TYPE = new Type<ShowDocumentTabEventHandler>();

    @Override
    public Type<ShowDocumentTabEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowDocumentTabEventHandler handler) {
        handler.onEvent(this);
    }
}
