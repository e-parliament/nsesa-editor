package org.nsesa.editor.gwt.dialog.client.ui.handler.create;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentInjectionPointFinder;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerSaveEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingToggleEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingToggleEventHandler;
import org.nsesa.editor.gwt.core.client.ui.drafting.DraftingController;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.dialog.client.event.CloseDialogEvent;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandler;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandlerImpl;

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
public class AmendmentDialogCreateController extends AmendmentUIHandlerImpl implements ProvidesResize, AmendmentUIHandler {

    protected final ClientFactory clientFactory;

    protected final AmendmentDialogCreateView view;

    protected final Locator locator;
    final private DraftingController draftingController;
    protected final AmendmentInjectionPointFinder amendmentInjectionPointFinder;
    protected final OverlayFactory overlayFactory;

    @Inject
    public AmendmentDialogCreateController(final ClientFactory clientFactory, final AmendmentDialogCreateView view,
                                           final Locator locator, final OverlayFactory overlayFactory,
                                           final DraftingController draftingController,
                                           final AmendmentInjectionPointFinder amendmentInjectionPointFinder) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.locator = locator;
        this.overlayFactory = overlayFactory;
        this.draftingController = draftingController;
        view.getRichTextEditor().setDraftingTool(draftingController.getView());
        this.amendmentInjectionPointFinder = amendmentInjectionPointFinder;
        registerListeners();
    }

    private void registerListeners() {
        view.getSaveButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // set the assigned number on the amendable widget
                dialogContext.getAmendableWidget().setAssignedNumber(dialogContext.getIndex());
                handleSave();
            }
        });

        view.getCancelLink().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handleClose();
            }
        });
        clientFactory.getEventBus().addHandler(DraftingToggleEvent.TYPE, new DraftingToggleEventHandler() {
            @Override
            public void onEvent(DraftingToggleEvent event) {
                view.getRichTextEditor().toggleDraftingTool(event.isShown());
            }
        });

    }

    public void handleSave() {
        if (dialogContext.getParentAmendableWidget() == null) {
            throw new NullPointerException("No parent amendable widget set.");
        }
        dialogContext.getAmendment().setSourceReference(new AmendableWidgetReference(true,
                dialogContext.getAmendmentAction() == AmendmentAction.CREATION,
                amendmentInjectionPointFinder.getInjectionPoint(dialogContext.getParentAmendableWidget()),
                dialogContext.getAmendableWidget().getType(),
                dialogContext.getIndex()));
        dialogContext.getAmendment().setLanguageISO(dialogContext.getDocumentController().getDocument().getLanguageIso());
        dialogContext.getAmendment().setAmendmentAction(dialogContext.getAmendmentAction());


        dialogContext.getDocumentController().getDocumentEventBus().fireEvent(new AmendmentContainerSaveEvent(dialogContext.getAmendment()));
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
    public void handle() {
        view.setTitle("Create new " + dialogContext.getAmendableWidget().getType());
        // clear the rte content
        view.setAmendmentContent("");
    }
}
