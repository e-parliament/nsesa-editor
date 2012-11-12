package org.nsesa.editor.gwt.editor.client.ui.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.editor.client.ui.amendments.AmendmentsPanelView;
import org.nsesa.editor.gwt.editor.client.ui.footer.FooterView;
import org.nsesa.editor.gwt.editor.client.ui.header.HeaderView;
import org.nsesa.editor.gwt.editor.client.ui.info.InfoPanelView;

/**
 * Date: 24/06/12 16:39
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EditorViewImpl extends Composite implements EditorView {

    interface MyUiBinder extends UiBinder<Widget, EditorViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private final ClientFactory clientFactory;

    @UiField
    HorizontalPanel documentsPanel;
    @UiField(provided = true)
    HeaderView headerView;
    @UiField(provided = true)
    FooterView footerView;
    @UiField
    TabLayoutPanel tabPanel;
    @UiField(provided = true)
    AmendmentsPanelView amendmentsPanelView;
    @UiField(provided = true)
    InfoPanelView infoPanelView;

    @Inject
    public EditorViewImpl(final ClientFactory clientFactory, final HeaderView headerView,
                          final FooterView footerView,
                          final AmendmentsPanelView amendmentsPanelView,
                          final InfoPanelView infoPanelView) {
        this.clientFactory = clientFactory;
        this.headerView = headerView;
        this.footerView = footerView;
        this.amendmentsPanelView = amendmentsPanelView;
        this.infoPanelView = infoPanelView;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public CellPanel getDocumentsPanel() {
        return documentsPanel;
    }

    public void switchToTab(int index) {
        tabPanel.selectTab(index);
    }
}
