package org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.marker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.logging.Logger;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 24/06/12 16:39
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class MarkerViewImpl extends Composite implements MarkerView {

    interface MyUiBinder extends UiBinder<Widget, MarkerViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    private static final Logger LOG = Logger.getLogger(MarkerViewImpl.class.getName());

    private final DocumentEventBus documentEventBus;
    @UiField
    HTMLPanel mainPanel;

    @Inject
    public MarkerViewImpl(final DocumentEventBus documentEventBus) {

        this.documentEventBus = documentEventBus;

        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public FocusWidget addMarker(final double top, final String color) {

        int height = getOffsetHeight();

        final double v = height / top;
        LOG.info("Drawing marker at " + (int) v);

        Anchor marker = new Anchor("<div></div>", true);
        mainPanel.add(marker);
        final Style style = marker.getElement().getFirstChildElement().getStyle();
        style.setPosition(Style.Position.RELATIVE);
        style.setTop((int) v, Style.Unit.PX);
        style.setWidth(100, Style.Unit.PCT);
        style.setHeight(5, Style.Unit.PX);
        style.setBorderWidth(1.0, Style.Unit.PX);
        style.setBackgroundColor(color);
        return marker;

    }

    @Override
    public void clearMarkers() {
        mainPanel.getElement().setInnerHTML("");
    }
}
