package org.nsesa.editor.gwt.core.client.event.widget;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendableWidgetSelectEvent extends GwtEvent<AmendableWidgetSelectEventHandler> {

    public static final Type<AmendableWidgetSelectEventHandler> TYPE = new Type<AmendableWidgetSelectEventHandler>();

    private final AmendableWidget amendableWidget;
    private final DocumentController documentController;

    public AmendableWidgetSelectEvent(AmendableWidget amendableWidget, DocumentController documentController) {
        this.amendableWidget = amendableWidget;
        this.documentController = documentController;
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

    public DocumentController getDocumentController() {
        return documentController;
    }
}
