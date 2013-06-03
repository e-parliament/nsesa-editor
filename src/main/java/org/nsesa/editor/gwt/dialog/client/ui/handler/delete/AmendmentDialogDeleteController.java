/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.dialog.client.ui.handler.delete;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.amendment.client.amendment.AmendmentInjectionPointFinder;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerSaveEvent;
import org.nsesa.editor.gwt.core.client.ClientFactory;
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
 * Dialog controller to handle the creation and editing of deletion amendments (amendments suggesting the removal of
 * a (complex) structure from the document).
 * <p/>
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogDeleteController extends AmendmentUIHandlerImpl implements ProvidesResize, AmendmentUIHandler {

    /**
     * The client factory.
     */
    protected final ClientFactory clientFactory;

    /**
     * The associated view.
     */
    protected final AmendmentDialogDeleteView view;

    /**
     * The list of {@link AmendmentDialogAwareController} child controllers to be added to the tabs.
     */
    protected final List<AmendmentDialogAwareController> childControllers = new ArrayList<AmendmentDialogAwareController>();

    /**
     * The overlay factory.
     */
    protected final OverlayFactory overlayFactory;

    /**
     * The locator.
     */
    protected final Locator locator;

    protected final AmendmentInjectionPointFinder amendmentInjectionPointFinder;

    private HandlerRegistration saveClickHandlerRegistration;
    private HandlerRegistration cancelClickHandlerRegistration;

    @Inject
    public AmendmentDialogDeleteController(final ClientFactory clientFactory, final AmendmentDialogDeleteView view,
                                           final Locator locator,
                                           final OverlayFactory overlayFactory,
                                           final AmendmentInjectionPointFinder amendmentInjectionPointFinder) {
        this.clientFactory = clientFactory;
        this.overlayFactory = overlayFactory;
        this.view = view;
        this.locator = locator;
        this.amendmentInjectionPointFinder = amendmentInjectionPointFinder;
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
        saveClickHandlerRegistration = view.getSaveButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handleSave();
            }
        });

        cancelClickHandlerRegistration = view.getCancelLink().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handleClose();
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        saveClickHandlerRegistration.removeHandler();
        cancelClickHandlerRegistration.removeHandler();
    }

    /**
     * Handle the request for saving of the amendment & the closing of the parent dialog.
     */
    public void handleSave() {
        dialogContext.getAmendment().setDocumentID(dialogContext.getDocumentController().getDocument().getDocumentID());
        dialogContext.getAmendment().setLanguageISO(dialogContext.getDocumentController().getDocument().getLanguageIso());
        dialogContext.getAmendment().setAmendmentAction(dialogContext.getAmendmentAction());
        if (dialogContext.getReferenceOverlayWidget() != null) {
            final AmendableWidgetReference injectionPoint = amendmentInjectionPointFinder.getInjectionPoint(
                    dialogContext.getParentOverlayWidget(),
                    dialogContext.getReferenceOverlayWidget(),
                    dialogContext.getOverlayWidget());
            dialogContext.getAmendment().setSourceReference(injectionPoint);
        }
        dialogContext.getDocumentController().getDocumentEventBus().fireEvent(new AmendmentContainerSaveEvent(dialogContext.getAmendment()));
        clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
    }

    /**
     * Request for closing of the dialog.
     */
    public void handleClose() {
        clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
    }

    /**
     * Return the associated view.
     *
     * @return the view.
     */
    @Override
    public AmendmentDialogDeleteView getView() {
        return view;
    }

    /**
     * Handle the passing of the dialog context to the child {@link AmendmentDialogAwareController}s.
     */
    @Override
    public void handle() {
        // make sure to pass the context to the children
        for (final AmendmentDialogAwareController childController : childControllers) {
            childController.setContext(dialogContext);
        }
        setProperties();
    }

    /**
     * Validate the properties set in the {@link org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext}.
     */
    public void setProperties() {
        if (dialogContext.getOverlayWidget() == null && dialogContext.getAmendment() == null) {
            throw new NullPointerException("Neither amendment nor amendable widget are set.");
        }
    }
}
