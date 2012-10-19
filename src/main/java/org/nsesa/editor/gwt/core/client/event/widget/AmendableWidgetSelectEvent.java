package org.nsesa.editor.gwt.core.client.event.widget;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendableWidgetSelectEvent extends GwtEvent<AmendableWidgetSelectEventHandler> {

    public static Type<AmendableWidgetSelectEventHandler> TYPE = new Type<AmendableWidgetSelectEventHandler>();

    private final AmendableWidget amendableWidget;

    public AmendableWidgetSelectEvent(AmendableWidget amendableWidget) {
        this.amendableWidget = amendableWidget;
    }

    @Override
    public Type<AmendableWidgetSelectEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendableWidgetSelectEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendableWidget getAmendableWidget() {
        return amendableWidget;
    }
}
