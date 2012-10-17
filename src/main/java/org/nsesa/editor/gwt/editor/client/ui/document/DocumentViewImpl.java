package org.nsesa.editor.gwt.editor.client.ui.document;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineController;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineView;
import org.nsesa.editor.gwt.editor.client.ui.document.content.ContentController;
import org.nsesa.editor.gwt.editor.client.ui.document.content.ContentView;
import org.nsesa.editor.gwt.editor.client.ui.document.header.DocumentHeaderController;
import org.nsesa.editor.gwt.editor.client.ui.document.header.DocumentHeaderView;
import org.nsesa.editor.gwt.editor.client.ui.document.marker.MarkerController;
import org.nsesa.editor.gwt.editor.client.ui.document.marker.MarkerView;

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

    private final ClientFactory clientFactory;

    @UiField(provided = true)
    final ContentView contentView;
    @UiField(provided = true)
    final MarkerView markerView;
    @UiField(provided = true)
    final DocumentHeaderView documentHeaderView;
    @UiField(provided = true)
    DeadlineView deadlineView;

    @Inject
    public DocumentViewImpl(final ClientFactory clientFactory, final ContentController contentView,
                            final MarkerController markerController, final DocumentHeaderController documentHeaderController,
                            final DeadlineController deadlineController) {

        this.clientFactory = clientFactory;
        this.contentView = contentView.getView();
        this.markerView = markerController.getView();
        this.documentHeaderView = documentHeaderController.getView();
        this.deadlineView = deadlineController.getView();

        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        Log.info("Attach: DocumentViewImpl");
    }
}
