package org.nsesa.editor.gwt.dialog.client.ui.handler.create;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerSaveEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.dialog.client.event.CloseDialogEvent;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandler;

/**
 * Main amendment dialog. Allows for the creation and editing of amendments. Typically consists of a two
 * column layout (with the original proposed text on the left, and a rich text editor on the right).
 * <p/>
 * Requires an {@link org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO} and {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget} to be set before it can be displayed.
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogCreateController extends Composite implements ProvidesResize, AmendmentUIHandler {

    private final ClientFactory clientFactory;

    private final AmendmentDialogCreateView view;

    private final OverlayFactory overlayFactory;

    private AmendmentContainerDTO amendment;

    private AmendableWidget amendableWidget;

    @Inject
    public AmendmentDialogCreateController(final ClientFactory clientFactory, final AmendmentDialogCreateView view, final OverlayFactory overlayFactory) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.overlayFactory = overlayFactory;
        registerListeners();
    }

    private void registerListeners() {
        view.getSaveButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                amendment.setSourceReference(new AmendableWidgetReference(amendableWidget.getId()));
                clientFactory.getEventBus().fireEvent(new AmendmentContainerSaveEvent(amendment));
                clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
            }
        });

        view.getCancelButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
            }
        });
    }

    @Override
    public AmendmentDialogCreateView getView() {
        return view;
    }

    @Override
    public void setAmendmentAndWidget(AmendmentContainerDTO amendment, AmendableWidget amendableWidget) {
        assert amendment != null : "Amendment should not be null.";
        assert amendableWidget != null : "Amendment Widget should not be null.";
        this.amendment = amendment;
        this.amendableWidget = amendableWidget;
        view.setTitle("Create new " + amendableWidget.getType());
    }
}
