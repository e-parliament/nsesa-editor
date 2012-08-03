package org.nsesa.editor.gwt.editor.client.ui.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.editor.client.event.main.ShowAmendmentsTabEvent;
import org.nsesa.editor.gwt.editor.client.event.main.ShowDocumentTabEvent;
import org.nsesa.editor.gwt.editor.client.event.main.ShowInfoTabEvent;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class HeaderViewImpl extends Composite implements HeaderView {
    interface MyUiBinder extends UiBinder<Widget, HeaderViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private final ClientFactory clientFactory;

    @UiField
    Image viewDocument;
    @UiField
    Image viewAmendments;
    @UiField
    Image viewInfo;

    @Inject
    public HeaderViewImpl(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @UiHandler("viewDocument")
    void handleDocumentClick(ClickEvent event) {
        clientFactory.getEventBus().fireEvent(new ShowDocumentTabEvent());
    }

    @UiHandler("viewAmendments")
    void handleAmendmentsClick(ClickEvent event) {
        clientFactory.getEventBus().fireEvent(new ShowAmendmentsTabEvent());
    }

    @UiHandler("viewInfo")
    void handleInfoClick(ClickEvent event) {
        clientFactory.getEventBus().fireEvent(new ShowInfoTabEvent());
    }
}
