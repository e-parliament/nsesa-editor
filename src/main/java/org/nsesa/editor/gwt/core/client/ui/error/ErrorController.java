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
