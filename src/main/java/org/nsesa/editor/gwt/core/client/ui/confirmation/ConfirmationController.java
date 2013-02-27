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
package org.nsesa.editor.gwt.core.client.ui.confirmation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;

/**
 * Controller for the confirmation popup panel, which should be triggered via the
 * {@link org.nsesa.editor.gwt.core.client.event.ConfirmationEvent}.
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ConfirmationController {

    private final PopupPanel popupPanel = new DecoratedPopupPanel(false, true);

    private final ConfirmationView view;

    @Inject
    public ConfirmationController(final ConfirmationView view) {
        this.view = view;
        this.popupPanel.setWidget(view);
        this.popupPanel.setGlassEnabled(true);
        registerListeners();
    }

    private void registerListeners() {

    }

    /**
     * Shows the popup at the given coordinates.
     *
     * @param left the left position, in pixels, relative to the browser window
     * @param top the top position, in pixels, relative to the browser window
     */
    public void show(int left, int top) {
        popupPanel.setPopupPosition(left, top);
        popupPanel.show();
    }

    /**
     * Center and show the popup.
     */
    public void center() {
        popupPanel.center();
        popupPanel.show();
    }

    /**
     * Hide the popup.
     */
    public void hide() {
        popupPanel.hide(true);
    }

    /**
     * Set the title and confirmation message to display.
     *
     * @param confirmationTitle   the title of the confirmation
     * @param confirmationMessage the confirmation message
     * @param confirmationHandler the confirmation handler
     * @param cancelHandler       the cancel handler
     */
    public void setConfirmation(final String confirmationTitle, final String confirmationMessage,
                                final String confirmationButtonText, final ClickHandler confirmationHandler,
                                final String cancelButtonText, final ClickHandler cancelHandler) {
        view.setConfirmationTitle(confirmationTitle);
        view.setConfirmationMessage(confirmationMessage);
        view.setConfirmationButtonText(confirmationButtonText);
        // ensure the click handlers cannot alter the behaviour of the confirmation panel
        view.getConfirmationButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                confirmationHandler.onClick(event);
                hide();
            }
        });
        view.setCancelButtonText(cancelButtonText);
        view.getCancelButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                cancelHandler.onClick(event);
                hide();
            }
        });
    }

    /**
     * Get the associated view.
     *
     * @return the view
     */
    public ConfirmationView getView() {
        return view;
    }
}
