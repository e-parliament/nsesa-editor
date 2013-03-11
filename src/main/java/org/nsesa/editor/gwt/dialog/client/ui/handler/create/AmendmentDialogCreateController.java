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
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentInjectionPointFinder;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerSaveEvent;
import org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureAttributesToggleEvent;
import org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureAttributesToggleEventHandler;
import org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureToggleEvent;
import org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureToggleEventHandler;
import org.nsesa.editor.gwt.core.client.ui.visualstructure.VisualStructureController;
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
    protected final VisualStructureController visualStructureController;

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
    private HandlerRegistration saveClickHandlerRegistration;
    private HandlerRegistration cancelClickHandlerRegistration;
    private com.google.web.bindery.event.shared.HandlerRegistration draftingToggleEventHandlerRegistration;
    private com.google.web.bindery.event.shared.HandlerRegistration draftingAttributesToggleEventHandlerRegistration;

    @Inject
    public AmendmentDialogCreateController(final ClientFactory clientFactory, final AmendmentDialogCreateView view,
                                           final Locator locator, final OverlayFactory overlayFactory,
                                           final VisualStructureController visualStructureController,
                                           final AmendmentInjectionPointFinder amendmentInjectionPointFinder) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.locator = locator;
        this.overlayFactory = overlayFactory;
        this.visualStructureController = visualStructureController;
        this.amendmentInjectionPointFinder = amendmentInjectionPointFinder;

        view.getRichTextEditor().setVisualStructureWidget(visualStructureController.getView());
        view.getRichTextEditor().setVisualStructureAttributesWidget(visualStructureController.getAttributesView());

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
        saveClickHandlerRegistration = view.getSaveButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // set the assigned number on the amendable widget
                dialogContext.getOverlayWidget().setAssignedNumber(dialogContext.getIndex());
                handleSave();
            }
        });

        // hide the dialog when the cancel link is clicked
        cancelClickHandlerRegistration = view.getCancelLink().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handleClose();
            }
        });

        // toggle the RTE's drafting view
        draftingToggleEventHandlerRegistration = clientFactory.getEventBus().addHandler(VisualStructureToggleEvent.TYPE, new VisualStructureToggleEventHandler() {
            @Override
            public void onEvent(VisualStructureToggleEvent event) {
                view.getRichTextEditor().toggleVisualStructure(event.isShown());
            }
        });

        // toggle the RTE's attributes view
        draftingAttributesToggleEventHandlerRegistration = clientFactory.getEventBus().addHandler(VisualStructureAttributesToggleEvent.TYPE, new VisualStructureAttributesToggleEventHandler() {
            @Override
            public void onEvent(VisualStructureAttributesToggleEvent event) {
                view.getRichTextEditor().toggleVisualStructureAttributes(event.isShown());
            }
        });

    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        saveClickHandlerRegistration.removeHandler();
        cancelClickHandlerRegistration.removeHandler();
        draftingAttributesToggleEventHandlerRegistration.removeHandler();
        draftingToggleEventHandlerRegistration.removeHandler();
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
                dialogContext.getParentOverlayWidget().getNamespaceURI(),
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
        visualStructureController.setOverlayWidgetWidget(dialogContext.getOverlayWidget());
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
