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
package org.nsesa.editor.gwt.dialog.client.ui.handler.delete;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerSaveEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.dialog.client.event.CloseDialogEvent;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandler;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandlerImpl;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.AmendmentDialogAwareController;

import java.util.List;

/**
 * Main amendment dialog. Allows for the creation and editing of amendments. Typically consists of a two
 * column layout (with the original proposed text on the left, and a rich text editor on the right).
 * <p/>
 * Requires an {@link org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO} and {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget} to be set before it can be displayed.
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogDeleteController extends AmendmentUIHandlerImpl implements ProvidesResize, AmendmentUIHandler {

    protected final ClientFactory clientFactory;

    protected final AmendmentDialogDeleteView view;

    protected final List<AmendmentDialogAwareController> childControllers;

    protected final OverlayFactory overlayFactory;

    protected final Locator locator;

    @Inject
    public AmendmentDialogDeleteController(final ClientFactory clientFactory, final AmendmentDialogDeleteView view,
                                           final Locator locator,
                                           final OverlayFactory overlayFactory,
                                           final List<AmendmentDialogAwareController> childControllers) {
        this.clientFactory = clientFactory;
        this.overlayFactory = overlayFactory;
        this.view = view;
        this.locator = locator;
        this.childControllers = childControllers;

        for (final AmendmentDialogAwareController amendmentDeleteAwareController : this.childControllers) {
            view.addView(amendmentDeleteAwareController.getView(), amendmentDeleteAwareController.getTitle());
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
        dialogContext.getAmendment().setLanguageISO(dialogContext.getDocumentController().getDocument().getLanguageIso());
        dialogContext.getAmendment().setAmendmentAction(dialogContext.getAmendmentAction());
        dialogContext.getAmendment().setSourceReference(new AmendableWidgetReference(dialogContext.getOverlayWidget().getId()));
        dialogContext.getDocumentController().getDocumentEventBus().fireEvent(new AmendmentContainerSaveEvent(dialogContext.getAmendment()));
        clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
    }

    public void handleClose() {
        clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
    }

    @Override
    public AmendmentDialogDeleteView getView() {
        return view;
    }

    @Override
    public void handle() {
        // make sure to pass the context to the children
        for (final AmendmentDialogAwareController childController : childControllers) {
            childController.setContext(dialogContext);
        }
        setProperties();
    }

    public void setProperties() {
        if (dialogContext.getOverlayWidget() == null && dialogContext.getAmendment() == null) {
            throw new NullPointerException("Neither amendment nor amendable widget are set.");
        }
    }
}
