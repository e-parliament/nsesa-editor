package org.nsesa.editor.gwt.dialog.client.ui.handler.modify;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerSaveEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.dialog.client.event.CloseDialogEvent;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandler;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.List;

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
public class AmendmentDialogModifyController extends Composite implements ProvidesResize, AmendmentUIHandler {

    protected final ClientFactory clientFactory;

    protected final AmendmentDialogModifyView view;

    protected final List<AmendmentModifyAwareController> childControllers;

    protected final OverlayFactory overlayFactory;

    protected final Locator locator;

    protected AmendmentContainerDTO amendment;

    protected AmendmentAction amendmentAction;

    protected AmendableWidget amendableWidget;

    protected DocumentController documentController;

    protected AmendableWidget parentAmendableWidget;
    private int index;

    @Inject
    public AmendmentDialogModifyController(final ClientFactory clientFactory, final AmendmentDialogModifyView view,
                                           final Locator locator,
                                           final OverlayFactory overlayFactory,
                                           final List<AmendmentModifyAwareController> childControllers) {
        this.clientFactory = clientFactory;
        this.overlayFactory = overlayFactory;
        this.view = view;
        this.locator = locator;
        this.childControllers = childControllers;

        for (final AmendmentModifyAwareController amendmentModifyAwareController : this.childControllers) {
            view.addView(amendmentModifyAwareController.getView(), amendmentModifyAwareController.getTitle());
        }
        registerListeners();
    }

    private void registerListeners() {
        view.getSaveButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handleSave();
            }
        });

        view.getCancelLink().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handleClose();
            }
        });
    }

    public void handleSave() {
        amendment.setSourceReference(new AmendableWidgetReference(amendableWidget.getId()));
        documentController.getDocumentEventBus().fireEvent(new AmendmentContainerSaveEvent(amendment));
        clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
    }

    public void handleClose() {
        clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
    }

    @Override
    public AmendmentDialogModifyView getView() {
        return view;
    }

    @Override
    public void setAmendmentAndWidget(final AmendmentContainerDTO amendment, final AmendableWidget amendableWidget) {
        this.amendment = amendment;
        this.amendableWidget = amendableWidget;

        if (amendableWidget == null && amendment == null) {
            throw new NullPointerException("Neither amendment nor amendable widget are set.");
        }

        if (amendableWidget != null) {
            view.setTitle(locator.getLocation(amendableWidget, clientFactory.getClientContext().getDocumentIso(), false));
            view.setAmendmentContent(amendableWidget.getContent());
        }

        if (amendment != null) {
            // TODO edit the amendment
        }

        for (final AmendmentModifyAwareController childController : childControllers) {
            childController.setAmendmentAndAmendableWidget(amendment, amendableWidget);
        }
    }

    @Override
    public void setParentAmendableWidget(AmendableWidget parentAmendableWidget) {
        this.parentAmendableWidget = parentAmendableWidget;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }

    @Override
    public void setAmendmentAction(AmendmentAction amendmentAction) {
        this.amendmentAction = amendmentAction;
    }
}
