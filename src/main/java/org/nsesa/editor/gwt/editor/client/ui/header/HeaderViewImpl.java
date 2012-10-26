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
import com.google.inject.Provider;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.editor.client.activity.EditorTabbedPlace;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class HeaderViewImpl extends Composite implements HeaderView {
    interface MyUiBinder extends UiBinder<Widget, HeaderViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private final ClientFactory clientFactory;

    private Provider<EditorTabbedPlace> editorTabbedPlaceProvider;

    @UiField
    Image viewDocument;
    @UiField
    Image viewAmendments;
    @UiField
    Image viewInfo;

    @Inject
    public HeaderViewImpl(final ClientFactory clientFactory, Provider<EditorTabbedPlace> editorTabbedPlaceProvider) {
        this.clientFactory = clientFactory;
        this.editorTabbedPlaceProvider = editorTabbedPlaceProvider;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @UiHandler("viewDocument")
    void handleDocumentClick(ClickEvent event) {
        EditorTabbedPlace editorTabbedPlace = editorTabbedPlaceProvider.get();
        editorTabbedPlace.setPlaceName("0");
        clientFactory.getPlaceController().goTo(editorTabbedPlace);
    }

    @UiHandler("viewAmendments")
    void handleAmendmentsClick(ClickEvent event) {
        EditorTabbedPlace editorTabbedPlace = editorTabbedPlaceProvider.get();
        editorTabbedPlace.setPlaceName("1");
        clientFactory.getPlaceController().goTo(editorTabbedPlace);
    }

    @UiHandler("viewInfo")
    void handleInfoClick(ClickEvent event) {
        EditorTabbedPlace editorTabbedPlace = editorTabbedPlaceProvider.get();
        editorTabbedPlace.setPlaceName("2");
        clientFactory.getPlaceController().goTo(editorTabbedPlace);
    }
}
