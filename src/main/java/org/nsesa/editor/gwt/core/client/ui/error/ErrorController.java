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
package org.nsesa.editor.gwt.core.client.ui.error;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ErrorController {

    private final PopupPanel popupPanel = new DecoratedPopupPanel(false, true);

    private final ErrorView view;

    @Inject
    public ErrorController(final ErrorView view) {
        this.view = view;
        this.popupPanel.setWidget(view);
        this.popupPanel.setGlassEnabled(true);
        this.view.asWidget().setWidth("400px");
        registerListeners();
    }

    private void registerListeners() {
        this.view.getOkButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ErrorController.this.hide();
            }
        });
    }

    public void show(int left, int top) {
        popupPanel.setPopupPosition(left, top);
        popupPanel.show();
    }

    public void center() {
        popupPanel.center();
        popupPanel.show();
    }

    public void hide() {
        popupPanel.hide(true);
    }

    /**
     * Set the title and error message to display.
     *
     * @param errorTitle   the title of the error
     * @param errorMessage the error message
     */
    public void setError(String errorTitle, String errorMessage) {
        view.setErrorTitle(errorTitle);
        view.setErrorMessage(errorMessage);
    }

    public ErrorView getView() {
        return view;
    }
}
