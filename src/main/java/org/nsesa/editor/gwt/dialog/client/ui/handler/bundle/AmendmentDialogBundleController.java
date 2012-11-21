package org.nsesa.editor.gwt.dialog.client.ui.handler.bundle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
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
public class AmendmentDialogBundleController extends Composite implements ProvidesResize, AmendmentUIHandler {

    private final ClientFactory clientFactory;

    private final AmendmentDialogBundleView view;

    private AmendmentContainerDTO amendment;

    private AmendableWidget amendableWidget;

    @Inject
    public AmendmentDialogBundleController(final ClientFactory clientFactory, final AmendmentDialogBundleView view) {
        this.clientFactory = clientFactory;
        this.view = view;
        registerListeners();
    }

    private void registerListeners() {
        view.getCancelButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
            }
        });
    }

    @Override
    public AmendmentDialogBundleView getView() {
        return view;
    }

    @Override
    public void setAmendmentAndWidget(AmendmentContainerDTO amendment, AmendableWidget amendableWidget) {
        assert amendment != null : "Amendment should not be null.";
        assert amendableWidget != null : "Amendment Widget should not be null.";
        this.amendment = amendment;
        this.amendableWidget = amendableWidget;
    }

    @Override
    public void onShow() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onClose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
