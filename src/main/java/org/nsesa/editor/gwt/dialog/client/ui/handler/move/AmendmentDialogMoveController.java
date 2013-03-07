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
package org.nsesa.editor.gwt.dialog.client.ui.handler.move;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.dialog.client.event.CloseDialogEvent;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandler;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandlerImpl;

/**
 * Dialog controller to handle the creation and editing of a movement amendments (amendments suggesting the move of
 * pre-existing a (complex) structure from the document).
 * <p/>
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogMoveController extends AmendmentUIHandlerImpl implements ProvidesResize, AmendmentUIHandler {

    /**
     * The client factory.
     */
    protected final ClientFactory clientFactory;

    /**
     * The associated view.
     */
    protected final AmendmentDialogMoveView view;
    private HandlerRegistration cancelClickHandlerRegistration;

    @Inject
    public AmendmentDialogMoveController(final ClientFactory clientFactory, final AmendmentDialogMoveView view) {
        this.clientFactory = clientFactory;
        this.view = view;
        registerListeners();
    }

    private void registerListeners() {
        cancelClickHandlerRegistration = view.getCancelLink().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        cancelClickHandlerRegistration.removeHandler();
    }

    /**
     * Get the associated view.
     *
     * @return the view
     */
    @Override
    public AmendmentDialogMoveView getView() {
        return view;
    }

    /**
     * Handle the context and pass it on to child
     * {@link org.nsesa.editor.gwt.dialog.client.ui.handler.common.AmendmentDialogAwareController}s, validated it, ..
     */
    @Override
    public void handle() {

    }
}
