package org.nsesa.editor.gwt.core.client.ui.amendment.action;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerStatusUpdatedEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

import java.util.ArrayList;
import java.util.Arrays;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.AMENDMENT;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(AMENDMENT)
public class AmendmentActionPanelController {

    protected final AmendmentActionPanelView view;

    protected final ClientFactory clientFactory;
    protected final ServiceFactory serviceFactory;

    // parent amendment controller
    protected AmendmentController amendmentController;

    protected final Anchor anchorTable = new Anchor("Table");

    protected final Anchor anchorWithdraw = new Anchor("Withdraw");

    protected final PopupPanel popupPanel = new PopupPanel(true, false);

    @Inject
    public AmendmentActionPanelController(final ClientFactory clientFactory,
                                          final ServiceFactory serviceFactory,
                                          final AmendmentActionPanelView amendmentActionPanelView) {
        this.clientFactory = clientFactory;
        this.serviceFactory = serviceFactory;
        this.view = amendmentActionPanelView;
        this.popupPanel.setWidget(amendmentActionPanelView);

        // create operations on the amendment
        addWidget(anchorTable);
        addSeparator();
        addWidget(anchorWithdraw);

        registerListeners();
    }

    private void registerListeners() {
        anchorTable.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String oldStatus = amendmentController.getModel().getAmendmentContainerStatus();
                popupPanel.hide();
                serviceFactory.getGwtAmendmentService().tableAmendmentContainers(clientFactory.getClientContext(),
                        new ArrayList<AmendmentContainerDTO>(Arrays.asList(amendmentController.getModel())),
                        new AsyncCallback<AmendmentContainerDTO[]>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not table amendment.", caught));
                            }

                            @Override
                            public void onSuccess(AmendmentContainerDTO[] result) {
                                amendmentController.setAmendment(result[0]);
                                final AmendmentContainerStatusUpdatedEvent updatedEvent = new AmendmentContainerStatusUpdatedEvent(amendmentController, oldStatus);
                                amendmentController.getDocumentController().getDocumentEventBus().fireEvent(updatedEvent);
                            }
                        });
            }
        });
        anchorWithdraw.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String oldStatus = amendmentController.getModel().getAmendmentContainerStatus();
                popupPanel.hide();
                serviceFactory.getGwtAmendmentService().withdrawAmendmentContainers(clientFactory.getClientContext(),
                        new ArrayList<AmendmentContainerDTO>(Arrays.asList(amendmentController.getModel())),
                        new AsyncCallback<AmendmentContainerDTO[]>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not withdraw amendment.", caught));
                            }

                            @Override
                            public void onSuccess(AmendmentContainerDTO[] result) {
                                amendmentController.setAmendment(result[0]);
                                final AmendmentContainerStatusUpdatedEvent updatedEvent = new AmendmentContainerStatusUpdatedEvent(amendmentController, oldStatus);
                                amendmentController.getDocumentController().getDocumentEventBus().fireEvent(updatedEvent);
                            }
                        });
            }
        });
    }

    public void addWidget(IsWidget widget) {
        view.getMainPanel().add(widget);
    }

    public void addWidget(Widget widget) {
        view.getMainPanel().add(widget);
    }

    public void addSeparator() {
        addWidget(new HTML("<hr class='separator'/>"));
    }

    public void show(int x, int y) {
        popupPanel.setPopupPosition(x, y);
        popupPanel.show();
    }

    public void hide() {
        popupPanel.hide();
    }

    public void setAmendmentController(AmendmentController amendmentController) {
        this.amendmentController = amendmentController;
    }
}
