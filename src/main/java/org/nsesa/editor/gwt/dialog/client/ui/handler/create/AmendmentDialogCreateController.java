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
package org.nsesa.editor.gwt.dialog.client.ui.handler.create;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentInjectionPointFinder;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerSaveEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingAttributesToggleEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingAttributesToggleEventHandler;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingToggleEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingToggleEventHandler;
import org.nsesa.editor.gwt.core.client.ui.drafting.DraftingController;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.core.shared.AmendmentAction;
import org.nsesa.editor.gwt.dialog.client.event.CloseDialogEvent;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandler;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandlerImpl;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.AmendmentDialogAwareController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main {@link AmendmentUIHandler} for the creation and editing of amendments that introduce new elements, such
 * as articles, recitals, ...).
 * <p/>
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogCreateController extends AmendmentUIHandlerImpl implements ProvidesResize, AmendmentUIHandler {

    /**
     * The client factory.
     */
    protected final ClientFactory clientFactory;

    /**
     * The associated view.
     */
    protected final AmendmentDialogCreateView view;

    /**
     * The list of child controllers - rendered via tabs.
     */
    protected final List<AmendmentDialogAwareController> childControllers = new ArrayList<AmendmentDialogAwareController>();

    /**
     * A Locator for the new element to introduce.
     */
    protected final Locator locator;

    /**
     * Drafting controller to assist with the markup of the HTML.
     */
    protected final DraftingController draftingController;

    /**
     * An injection point finder to find the path of the parent
     * {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget} so we can save & re-inject this
     * amendment later on.
     */
    protected final AmendmentInjectionPointFinder amendmentInjectionPointFinder;

    /**
     * The overlay factory that allows you to instantiate the new overlay structure from the new element.
     */
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
        this.amendmentInjectionPointFinder = amendmentInjectionPointFinder;

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
        // calls the handleSave() method afterwards
        // TODO validate
        view.getSaveButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // set the assigned number on the amendable widget
                dialogContext.getOverlayWidget().setAssignedNumber(dialogContext.getIndex());
                handleSave();
            }
        });

        // hide the dialog when the cancel link is clicked
        view.getCancelLink().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handleClose();
            }
        });

        // toggle the RTE's drafting view
        clientFactory.getEventBus().addHandler(DraftingToggleEvent.TYPE, new DraftingToggleEventHandler() {
            @Override
            public void onEvent(DraftingToggleEvent event) {
                view.getRichTextEditor().toggleDraftingTool(event.isShown());
            }
        });

        // toggle the RTE's attributes view
        clientFactory.getEventBus().addHandler(DraftingAttributesToggleEvent.TYPE, new DraftingAttributesToggleEventHandler() {
            @Override
            public void onEvent(DraftingAttributesToggleEvent event) {
                view.getRichTextEditor().toggleDraftingAttributes(event.isShown());
            }
        });

    }

    /**
     * Handle the saving of the amendment.
     */
    public void handleSave() {
        if (dialogContext.getParentOverlayWidget() == null) {
            throw new NullPointerException("No parent amendable widget set. Cannot continue.");
        }

        // set up the source reference so we can re-inject this amendment later.
        dialogContext.getAmendment().setSourceReference(new AmendableWidgetReference(true,
                dialogContext.getAmendmentAction() == AmendmentAction.CREATION,
                amendmentInjectionPointFinder.getInjectionPoint(dialogContext.getParentOverlayWidget()),
                dialogContext.getOverlayWidget().getType(),
                dialogContext.getIndex()));

        // the language is always the one from the document
        dialogContext.getAmendment().setLanguageISO(dialogContext.getDocumentController().getDocument().getLanguageIso());

        // store the action as well
        dialogContext.getAmendment().setAmendmentAction(dialogContext.getAmendmentAction());

        // request a saving of the document
        dialogContext.getDocumentController().getDocumentEventBus().fireEvent(new AmendmentContainerSaveEvent(dialogContext.getAmendment()));

        // finally, close the parent dialog at this point.
        clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
    }

    /**
     * Handle the request for closing of the parent dialog.
     */
    public void handleClose() {
        clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
    }

    /**
     * Return the view.
     *
     * @return the view
     */
    @Override
    public AmendmentDialogCreateView getView() {
        return view;
    }

    /**
     * Handle the set up and passing of the dialog context to the child {@link AmendmentDialogAwareController}s.
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
     * Used to indicate the the controller can set and validate the context to ensure all dependencies are set.
     */
    public void setProperties() {
        if (dialogContext.getOverlayWidget() == null && dialogContext.getAmendment() == null) {
            throw new NullPointerException("Neither amendment nor amendable widget are set.");
        }
    }
}
