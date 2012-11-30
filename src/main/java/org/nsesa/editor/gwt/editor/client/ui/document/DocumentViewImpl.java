package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineController;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineView;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.ui.amendments.AmendmentsPanelController;
import org.nsesa.editor.gwt.editor.client.ui.amendments.AmendmentsPanelView;
import org.nsesa.editor.gwt.editor.client.ui.amendments.header.AmendmentsHeaderController;
import org.nsesa.editor.gwt.editor.client.ui.amendments.header.AmendmentsHeaderView;
import org.nsesa.editor.gwt.editor.client.ui.document.content.ContentController;
import org.nsesa.editor.gwt.editor.client.ui.document.content.ContentView;
import org.nsesa.editor.gwt.editor.client.ui.document.header.DocumentHeaderController;
import org.nsesa.editor.gwt.editor.client.ui.document.header.DocumentHeaderView;
import org.nsesa.editor.gwt.editor.client.ui.document.marker.MarkerController;
import org.nsesa.editor.gwt.editor.client.ui.document.marker.MarkerView;
import org.nsesa.editor.gwt.editor.client.ui.info.InfoPanelController;
import org.nsesa.editor.gwt.editor.client.ui.info.InfoPanelView;

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
public class DocumentViewImpl extends Composite implements DocumentView, ProvidesResize {


    interface MyUiBinder extends UiBinder<Widget, DocumentViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private static final Logger LOG = Logger.getLogger(DocumentViewImpl.class.getName());

    private final DocumentEventBus documentEventBus;
    private final ClientFactory clientFactory;
    private AmendmentsHeaderController amendmentsHeaderController;

    @UiField
    HorizontalPanel horizontalPanel;

    @UiField
    HTMLPanel contentHolder;
    @UiField(provided = true)
    final ContentView contentView;
    @UiField(provided = true)
    final MarkerView markerView;
    @UiField(provided = true)
    final DocumentHeaderView documentHeaderView;
    @UiField(provided = true)
    DeadlineView deadlineView;

    @UiField
    DockLayoutPanel mainPanel;

    @UiField
    HTML documentTitle;

    @UiField
    TabLayoutPanel tabPanel;

    @UiField(provided = true)
    AmendmentsPanelView amendmentsPanelView;

    AmendmentsHeaderView amendmentsHeaderView;

    @UiField(provided = true)
    InfoPanelView infoPanelView;

    @UiField
    Image viewDocument;
    @UiField
    Image viewAmendments;
    @UiField
    Image viewInfo;

    @Inject
    public DocumentViewImpl(final DocumentEventBus documentEventBus,
                            final ClientFactory clientFactory,
                            final AmendmentsPanelController amendmentsPanelController,
                            final InfoPanelController infoPanelController,
                            final ContentController contentController,
                            final MarkerController markerController,
                            final DocumentHeaderController documentHeaderController,
                            final DeadlineController deadlineController) {

        this.documentEventBus = documentEventBus;
        this.clientFactory = clientFactory;
        this.amendmentsPanelView = amendmentsPanelController.getView();
        this.infoPanelView = infoPanelController.getView();
        this.contentView = contentController.getView();
        this.markerView = markerController.getView();
        this.documentHeaderView = documentHeaderController.getView();
        this.deadlineView = deadlineController.getView();

        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);

        // force 100% width - no way to do it via UI-Binder
        // note that this does not impact the actual document width, only the content
        //horizontalPanel.setCellWidth(contentHolder, "90%");
        horizontalPanel.setCellWidth(markerView, "18px");
        horizontalPanel.getElement().getStyle().setTableLayout(Style.TableLayout.FIXED);

        switchToTab(0);
    }

    public void setDocumentHeight(final int height) {
        mainPanel.setHeight(height + "px");
    }

    public void setDocumentTitle(String titleHTML) {
        documentTitle.setHTML(titleHTML);
    }

    @UiHandler("viewDocument")
    void handleDocumentClick(ClickEvent event) {
//        EditorTabbedPlace editorTabbedPlace = editorTabbedPlaceProvider.get();
//        editorTabbedPlace.setPlaceName("0");
//        clientFactory.getPlaceController().goTo(editorTabbedPlace);
        switchToTab(0);
    }

    @UiHandler("viewAmendments")
    void handleAmendmentsClick(ClickEvent event) {
//        EditorTabbedPlace editorTabbedPlace = editorTabbedPlaceProvider.get();
//        editorTabbedPlace.setPlaceName("1");
//        clientFactory.getPlaceController().goTo(editorTabbedPlace);
        switchToTab(1);
    }

    @UiHandler("viewInfo")
    void handleInfoClick(ClickEvent event) {
//        EditorTabbedPlace editorTabbedPlace = editorTabbedPlaceProvider.get();
//        editorTabbedPlace.setPlaceName("2");
//        clientFactory.getPlaceController().goTo(editorTabbedPlace);
        switchToTab(2);
    }

    public void switchToTab(int index) {
        tabPanel.selectTab(index);
    }
}
