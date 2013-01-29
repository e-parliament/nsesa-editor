package org.nsesa.editor.gwt.editor.client.ui.document.sourcefile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineController;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineView;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.editor.client.ui.document.amendments.AmendmentsPanelController;
import org.nsesa.editor.gwt.editor.client.ui.document.info.InfoPanelController;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.actionbar.ActionBarController;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.actionbar.ActionBarView;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.content.ContentController;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.content.ContentView;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.header.SourceFileHeaderController;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.header.SourceFileHeaderView;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.marker.MarkerController;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.marker.MarkerView;

/**
 * Date: 28/01/13 15:27
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class SourceFileViewImpl extends Composite implements SourceFileView {

    interface MyUiBinder extends UiBinder<Widget, SourceFileViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private final DocumentEventBus documentEventBus;

    private final ClientFactory clientFactory;

    @UiField
    HorizontalPanel horizontalPanel;

    @UiField
    HTMLPanel contentHolder;
    @UiField(provided = true)
    final ContentView contentView;
    @UiField(provided = true)
    final MarkerView markerView;
    @UiField(provided = true)
    final SourceFileHeaderView sourceFileHeaderView;
    @UiField(provided = true)
    DeadlineView deadlineView;

    @UiField(provided = true)
    ActionBarView actionBarView;

    @Inject
    public SourceFileViewImpl(final DocumentEventBus documentEventBus,
                              final ClientFactory clientFactory,
                              final AmendmentsPanelController amendmentsPanelController,
                              final InfoPanelController infoPanelController,
                              final ContentController contentController,
                              final MarkerController markerController,
                              final SourceFileHeaderController sourceFileHeaderController,
                              final DeadlineController deadlineController,
                              final ActionBarController actionBarController,
                              final org.nsesa.editor.gwt.editor.client.ui.header.resources.Resources resources
    ) {

        this.documentEventBus = documentEventBus;
        this.clientFactory = clientFactory;
        this.contentView = contentController.getView();
        this.markerView = markerController.getView();
        this.sourceFileHeaderView = sourceFileHeaderController.getView();
        this.deadlineView = deadlineController.getView();
        this.actionBarView = actionBarController.getView();
        this.actionBarView.asWidget().setVisible(false);

        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);

        horizontalPanel.setCellWidth(markerView, "18px");
        horizontalPanel.getElement().getStyle().setTableLayout(Style.TableLayout.FIXED);
    }

}
