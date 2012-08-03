package org.nsesa.editor.gwt.editor.client.ui.document.marker;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Date: 24/06/12 16:39
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class MarkerViewImpl extends Composite implements MarkerView {

    interface MyUiBinder extends UiBinder<Widget, MarkerViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private final EventBus eventBus;
    @UiField
    HTMLPanel mainPanel;

    @Inject
    public MarkerViewImpl(final EventBus eventBus) {

        this.eventBus = eventBus;

        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public void addMarker(double top) {

        int height = getOffsetHeight();

        final double v = height / top;
        Log.info("Drawing marker at " + v);
        final Element div = DOM.createDiv();
        mainPanel.getElement().appendChild(div);
        div.getStyle().setPosition(Style.Position.RELATIVE);
        div.getStyle().setTop(v, Style.Unit.PX);
        div.getStyle().setWidth(100, Style.Unit.PCT);
        div.getStyle().setHeight(5, Style.Unit.PX);
        div.getStyle().setBorderWidth(1.0, Style.Unit.PX);
        div.getStyle().setBackgroundColor("blue");
    }

    @Override
    public void clearMarkers() {
        mainPanel.getElement().setInnerHTML("");
    }
}
