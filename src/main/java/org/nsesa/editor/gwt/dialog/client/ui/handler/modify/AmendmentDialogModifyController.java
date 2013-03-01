/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.dialog.client.ui.handler.modify;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerSaveEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingAttributesToggleEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingAttributesToggleEventHandler;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingToggleEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingToggleEventHandler;
import org.nsesa.editor.gwt.core.client.ui.drafting.DraftingController;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.dialog.client.event.CloseDialogEvent;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandler;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandlerImpl;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.AmendmentDialogAwareController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main amendment dialog. Allows for the creation and editing of modification amendments. Typically consists of a two
 * column layout (with the original proposed text on the left, and a rich text editor on the right).
 * <p/>
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogModifyController extends AmendmentUIHandlerImpl implements ProvidesResize, AmendmentUIHandler {

    /**
     * The client factory.
     */
    protected final ClientFactory clientFactory;

    /**
     * The associated view.
     */
    protected final AmendmentDialogModifyView view;

    /**
     * The overlay factory.
     */
    protected final OverlayFactory overlayFactory;

    /**
     * The locator.
     */
    protected final Locator locator;

    /**
     * The drafting controller.
     */
    protected final DraftingController draftingController;

    /**
     * The list of child {@link AmendmentDialogAwareController} controllers.
     */
    protected List<AmendmentDialogAwareController> childControllers = new ArrayList<AmendmentDialogAwareController>();

    @Inject
    public AmendmentDialogModifyController(final ClientFactory clientFactory, final AmendmentDialogModifyView view,
                                           final Locator locator,
                                           final OverlayFactory overlayFactory,
                                           final DraftingController draftingController) {
        this.clientFactory = clientFactory;
        this.overlayFactory = overlayFactory;
        this.view = view;
        this.locator = locator;
        this.draftingController = draftingController;


        view.getRichTextEditor().setDraftingTool(draftingController.getView());
        view.getRichTextEditor().setDraftingAttributes(draftingController.getAttributesView());

        registerListeners();
    }

    /**
     * Add one or more {@link AmendmentDialogAwareController}s. Each one will be added to the view.
     *
     * @param amendmentDialogAwareControllers
     *         the child controllers to add
     */
    public void addChildControllers(AmendmentDialogAwareController... amendmentDialogAwareControllers) {
        this.childControllers.addAll(Arrays.asList(amendmentDialogAwareControllers));
        for (final AmendmentDialogAwareController amendmentModifyAwareController : this.childControllers) {
            view.addView(amendmentModifyAwareController.getView(), amendmentModifyAwareController.getTitle());
        }
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

        clientFactory.getEventBus().addHandler(DraftingToggleEvent.TYPE, new DraftingToggleEventHandler() {
            @Override
            public void onEvent(DraftingToggleEvent event) {
                view.getRichTextEditor().toggleDraftingTool(event.isShown());
            }
        });
        clientFactory.getEventBus().addHandler(DraftingAttributesToggleEvent.TYPE, new DraftingAttributesToggleEventHandler() {
            @Override
            public void onEvent(DraftingAttributesToggleEvent event) {
                view.getRichTextEditor().toggleDraftingAttributes(event.isShown());
            }
        });
    }

    /**
     * Request the saving of the amendment.
     */
    public void handleSave() {
        dialogContext.getAmendment().setLanguageISO(dialogContext.getDocumentController().getDocument().getLanguageIso());
        dialogContext.getAmendment().setAmendmentAction(dialogContext.getAmendmentAction());
        dialogContext.getAmendment().setSourceReference(new AmendableWidgetReference(dialogContext.getOverlayWidget().getId()));
        dialogContext.getDocumentController().getDocumentEventBus().fireEvent(new AmendmentContainerSaveEvent(dialogContext.getAmendment()));
        clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
    }

    /**
     * Request the closing of the parent dialog.
     */
    public void handleClose() {
        clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
    }

    /**
     * Return the view associated with this controller.
     * @return the view
     */
    @Override
    public AmendmentDialogModifyView getView() {
        return view;
    }

    /**
     * Pass the dialog context to the child {@link AmendmentDialogAwareController}s.
     */
    @Override
    public void handle() {
        // set the amendable widget in the drafting controller
        draftingController.setOverlayWidgetWidget(dialogContext.getOverlayWidget());
        // make sure to pass the context to the children
        for (final AmendmentDialogAwareController childController : childControllers) {
            childController.setContext(dialogContext);
        }
        setProperties();
    }

    /**
     * Set and validate the properties that are set in the dialog context.
     */
    public void setProperties() {
        if (dialogContext.getOverlayWidget() == null && dialogContext.getAmendment() == null) {
            throw new NullPointerException("Neither amendment nor amendable widget are set.");
        }
    }
}
