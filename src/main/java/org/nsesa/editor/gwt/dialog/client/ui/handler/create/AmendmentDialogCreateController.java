package org.nsesa.editor.gwt.dialog.client.ui.handler.create;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentInjectionPointFinder;
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

    protected final ClientFactory clientFactory;

    protected final AmendmentDialogCreateView view;

    protected final Locator locator;
    protected final AmendmentInjectionPointFinder amendmentInjectionPointFinder;
    protected final OverlayFactory overlayFactory;

    protected AmendmentContainerDTO amendment;
    protected AmendmentAction amendmentAction;
    protected AmendableWidget amendableWidget;
    protected AmendableWidget parentAmendableWidget;
    protected int index;

    protected DocumentController documentController;

    @Inject
    public AmendmentDialogCreateController(final ClientFactory clientFactory, final AmendmentDialogCreateView view,
                                           final Locator locator, final OverlayFactory overlayFactory,
                                           final AmendmentInjectionPointFinder amendmentInjectionPointFinder) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.locator = locator;
        this.overlayFactory = overlayFactory;
        this.amendmentInjectionPointFinder = amendmentInjectionPointFinder;
        registerListeners();
    }

    private void registerListeners() {
        view.getSaveButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // set the assigned number on the amendable widget
                amendableWidget.setAssignedNumber(index);
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
        if (parentAmendableWidget == null) {
            throw new NullPointerException("No parent amendable widget set.");
        }
        amendment.setSourceReference(new AmendableWidgetReference(true,
                amendmentAction == AmendmentAction.CREATION,
                amendmentInjectionPointFinder.getInjectionPoint(parentAmendableWidget),
                amendableWidget.getType(),
                index));


        documentController.getDocumentEventBus().fireEvent(new AmendmentContainerSaveEvent(amendment));
        clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
    }

    public void handleClose() {
        clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
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

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }

    @Override
    public void setAmendmentAction(AmendmentAction amendmentAction) {
        this.amendmentAction = amendmentAction;
    }

    @Override
    public void setParentAmendableWidget(AmendableWidget parentAmendableWidget) {
        this.parentAmendableWidget = parentAmendableWidget;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }
}
