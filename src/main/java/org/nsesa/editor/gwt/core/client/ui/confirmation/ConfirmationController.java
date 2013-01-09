package org.nsesa.editor.gwt.core.client.ui.confirmation;

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
public class ConfirmationController {

    private final PopupPanel popupPanel = new DecoratedPopupPanel(false, true);

    private final ConfirmationView view;

    @Inject
    public ConfirmationController(final ConfirmationView view) {
        this.view = view;
        this.popupPanel.setWidget(view);
        this.popupPanel.setGlassEnabled(true);
        this.view.asWidget().setWidth("400px");
        registerListeners();
    }

    private void registerListeners() {

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
        // ensure the clickhandlers cannot alter the behaviour of the confirmation panel
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

    public ConfirmationView getView() {
        return view;
    }
}
