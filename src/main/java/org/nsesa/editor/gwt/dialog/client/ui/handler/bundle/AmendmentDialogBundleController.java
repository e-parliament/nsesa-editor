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
package org.nsesa.editor.gwt.dialog.client.ui.handler.bundle;

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
 * Dialog controller to handle the bundling of amendments into a single consolidated amendment.
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogBundleController extends AmendmentUIHandlerImpl implements ProvidesResize, AmendmentUIHandler {

    /**
     * The client factory giving access to client side dependencies and user information.
     */
    protected final ClientFactory clientFactory;

    /**
     * The associated view.
     */
    protected final AmendmentDialogBundleView view;
    private HandlerRegistration clickHandlerRegistration;

    @Inject
    public AmendmentDialogBundleController(final ClientFactory clientFactory, final AmendmentDialogBundleView view) {
        this.clientFactory = clientFactory;
        this.view = view;
        registerListeners();
    }

    private void registerListeners() {
        clickHandlerRegistration = view.getCancelButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
            }
        });
    }

    public void removeListeners() {
        clickHandlerRegistration.removeHandler();
    }

    /**
     * Return the view.
     * @return the view
     */
    @Override
    public AmendmentDialogBundleView getView() {
        return view;
    }

    /**
     * Handle the call, currently does nothing.
     */
    @Override
    public void handle() {

    }
}
