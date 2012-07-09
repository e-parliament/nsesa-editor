package org.nsesa.editor.gwt.dialog.client.ui.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEventHandler;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerEditEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerEditEventHandler;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogController extends Composite implements ProvidesResize {

    private final PopupPanel popupPanel = new DecoratedPopupPanel(false, true);

    private final AmendmentDialogView view;
    private final ClientFactory clientFactory;

    private AmendmentContainerDTO amendment;

    @Inject
    public AmendmentDialogController(final ClientFactory clientFactory, final AmendmentDialogView view) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.popupPanel.setWidget(view);
        this.popupPanel.setGlassEnabled(true);

        registerListeners();
    }

    private void registerListeners() {
        clientFactory.getEventBus().addHandler(AmendmentContainerCreateEvent.TYPE, new AmendmentContainerCreateEventHandler() {
            @Override
            public void onEvent(AmendmentContainerCreateEvent event) {
                center();
            }
        });
        clientFactory.getEventBus().addHandler(AmendmentContainerEditEvent.TYPE, new AmendmentContainerEditEventHandler() {
            @Override
            public void onEvent(AmendmentContainerEditEvent event) {
                center();
            }
        });
        this.view.getCancelButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                AmendmentDialogController.this.hide();
            }
        });

        this.view.getSaveButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                AmendmentDialogController.this.hide();
            }
        });
    }

    public void center() {
        resize();
        popupPanel.center();
        popupPanel.show();
    }

    private void resize() {
        view.asWidget().setHeight((Window.getClientHeight() - 100) + "px");
        view.asWidget().setWidth((Window.getClientWidth() - 100) + "px");
    }

    public void hide() {
        popupPanel.hide(true);
    }

    public AmendmentContainerDTO getAmendment() {
        return amendment;
    }

    public void setAmendment(AmendmentContainerDTO amendment) {
        this.amendment = amendment;
    }

    public AmendmentDialogView getView() {
        return view;
    }
}
