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
package org.nsesa.editor.gwt.amendment.client.ui.amendment.share;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerDiffEvent;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerMergeEvent;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerSavedEvent;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerUpdatedEvent;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.amendment.client.ui.document.AmendmentDocumentController;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.NotificationEvent;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.i18n.CoreMessages;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.GroupDTO;
import org.nsesa.editor.gwt.core.shared.PersonDTO;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.AMENDMENT;

/**
 * This is the controller to show you all the groups that you can share the amendment with.
 * <p/>
 * This controller can be extended by calling the {@link #addWidget(com.google.gwt.user.client.ui.Widget)} or
 * {@link #addWidget(com.google.gwt.user.client.ui.IsWidget)} methods.
 * <p/>
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(AMENDMENT)
public class SharePanelController {

    /**
     * The view.
     */
    protected final SharePanelView view;

    /**
     * The parent amendment controller.
     */
    protected AmendmentController amendmentController;



    /**
     * The enclosing popup.
     */
    protected final PopupPanel popupPanel = new PopupPanel(true, false);


    @Inject
    public SharePanelController(final SharePanelView sharePanelView,
                                final CoreMessages coreMessages) {
        this.view = sharePanelView;
        this.popupPanel.setWidget(sharePanelView);


//        anchorMoveDown.getElement().getStyle().setCursor(Style.Cursor.POINTER);

        // set the correct anchor labels
//        anchorMoveDown.setText(coreMessages.amendmentActionMoveDown());
    }

    /**
     * Registers the event listeners on the various anchors.
     */
    public void registerListeners() {
        /*anchorTableHandlerRegistration = anchorTable.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final AmendmentManager amendmentManager = ((AmendmentDocumentController) amendmentController.getDocumentController()).getAmendmentManager();
                if (amendmentManager != null) {
                    final ClientFactory clientFactory = amendmentController.getDocumentController().getClientFactory();
                    final ConfirmationEvent confirmationEvent = new ConfirmationEvent(
                            clientFactory.getCoreMessages().confirmationAmendmentTableTitle(),
                            clientFactory.getCoreMessages().confirmationAmendmentTableMessage(),
                            clientFactory.getCoreMessages().confirmationAmendmentTableButtonConfirm(),
                            confirmTableHandler,
                            clientFactory.getCoreMessages().confirmationAmendmentTableButtonCancel(),
                            cancelHandler);

                    amendmentController.getDocumentController().getDocumentEventBus().fireEvent(confirmationEvent);
                } else {
                    // you cannot table the amendment if no document controller has been set
                }
                popupPanel.hide();
            }
        });*/

    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {


    }

    /**
     * Adds a widget to the action panel.
     *
     * @param widget the widget to add.
     */
    public void addWidget(IsWidget widget) {
        view.getMainPanel().add(widget);
    }

    /**
     * Adds a widget to the action panel.
     *
     * @param widget the widget to add.
     */
    public void addWidget(Widget widget) {
        view.getMainPanel().add(widget);
    }

    /**
     * Adds a visual separator (an hr element with the css class 'separator') to the actions listed.
     */
    public void addSeparator() {
        addWidget(getSeparator());
    }

    /**
     * Shows the popup at the given coordinates.
     *
     * @param x the x (left) position, in pixels, relative to the browser window
     * @param y the y (top) position, in pixels, relative to the browser window
     */
    public void show(int x, int y) {
        popupPanel.setPopupPosition(x, y);
        popupPanel.show();
    }

    /**
     * Hides the popup panel.
     */
    public void hide() {
        popupPanel.hide();
    }



    /**
     * Sets the parent amendment controller, and registers the event listeners.
     *
     * @param amendmentController the parent amendment controller
     */
    public void setAmendmentController(final AmendmentController amendmentController) {
        this.amendmentController = amendmentController;

        final ClientContext clientContext = amendmentController.getDocumentController().getClientFactory().getClientContext();
        PersonDTO person = clientContext.getLoggedInPerson();
        view.getMainPanel().clear();
        for (final GroupDTO group : person.getGroups()) {
            final CheckBox checkBox = new CheckBox(group.getName());
            if (amendmentController.getModel().getGroups() != null)
                checkBox.setValue(amendmentController.getModel().getGroups().contains(group), false);

            checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                @Override
                public void onValueChange(final ValueChangeEvent<Boolean> event) {
                    // TODO register this
                    amendmentController.getDocumentController().getServiceFactory().getGwtAmendmentService().shareAmendmentContainer(clientContext, amendmentController.getModel().getAmendmentContainerID(), group.getGroupID(), event.getValue(), new AsyncCallback<AmendmentContainerDTO>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            amendmentController.getDocumentController().getClientFactory().getEventBus().fireEvent(new CriticalErrorEvent("Could not share amendment with group.", caught));
                        }

                        @Override
                        public void onSuccess(AmendmentContainerDTO result) {
                            EventBus eventBus = amendmentController.getDocumentController().getClientFactory().getEventBus();
                            // merge our updated result
                            amendmentController.setModel(result);
                            eventBus.fireEvent(new AmendmentContainerMergeEvent(result));
                            // re-diff
                            amendmentController.getDocumentController().getDocumentEventBus().fireEvent(new AmendmentContainerSavedEvent(amendmentController));
                            String message = (event.getValue() ? "Successfully shared amendment with group " :
                                    "Successfully stopped sharing amendment with group ") + group.getName();
                            eventBus.fireEvent(new NotificationEvent(message));
                        }
                    });
                }
            });
            addWidget(checkBox);
        }
    }

    /**
     * Returns a widget separator as hr html tag
     *
     * @return {@link com.google.gwt.user.client.ui.Widget} separator
     */
    protected Widget getSeparator() {
        return new HTML("<hr class='separator'/>");
    }

    public SharePanelView getView() {
        return view;
    }
}
