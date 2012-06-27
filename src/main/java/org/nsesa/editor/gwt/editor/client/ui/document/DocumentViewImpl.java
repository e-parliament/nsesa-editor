package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Date: 24/06/12 16:39
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentViewImpl extends Composite implements DocumentView {

    interface MyUiBinder extends UiBinder<Widget, DocumentViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private final EventBus eventBus;
    @UiField
    HTMLPanel contentPanel;
    @UiField
    HTMLPanel markerPanel;

    @Inject
    public DocumentViewImpl(final EventBus eventBus) {

        this.eventBus = eventBus;

        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public Panel getContentPanel() {
        return contentPanel;
    }

    @Override
    public Panel getMarkerPanel() {
        return markerPanel;
    }
}
