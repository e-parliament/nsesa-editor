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
package org.nsesa.editor.gwt.compare.client.ui.compare;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.compare.client.event.HideComparePanelEvent;
import org.nsesa.editor.gwt.compare.client.event.HideComparePanelEventHandler;
import org.nsesa.editor.gwt.compare.client.event.ShowComparePanelEvent;
import org.nsesa.editor.gwt.compare.client.event.ShowComparePanelEventHandler;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.shared.RevisionDTO;

import java.util.List;
import java.util.logging.Logger;

/**
 * A controller for inline editing. Can be attached to an {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget}.
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class CompareController implements ProvidesResize {

    private static final Logger LOG = Logger.getLogger(CompareController.class.getName());

    /**
     * The client factory, with access to the {@link com.google.web.bindery.event.shared.EventBus},
     * {@link org.nsesa.editor.gwt.core.shared.ClientContext}, ..
     */
    protected final ClientFactory clientFactory;

    /**
     * Service factory giving access to the remote services. Exposed for easier subclassing.
     */
    protected final ServiceFactory serviceFactory;

    /**
     * Service for retrieving comparison information.
     */
    protected ComparisonProvider comparisonProvider;

    protected final CompareView view;

    protected final PopupPanel popupPanel = new DecoratedPopupPanel(false, true);

    private HandlerRegistration cancelButtonHandlerRegistration;
    private HandlerRegistration rollbackButtonHandlerRegistration;

    private com.google.web.bindery.event.shared.HandlerRegistration hideComparePanelEventHandlerRegistration;
    private com.google.web.bindery.event.shared.HandlerRegistration showComparePanelEventHandlerRegistration;


    @Inject
    public CompareController(final ClientFactory clientFactory,
                             final ServiceFactory serviceFactory,
                             final CompareView view) {
        this.clientFactory = clientFactory;
        this.serviceFactory = serviceFactory;
        this.view = view;
        this.popupPanel.setWidget(view);
        this.popupPanel.setTitle("Amendment Revisions");
        this.popupPanel.setGlassEnabled(true);

        view.asWidget().setWidth(Window.getClientWidth() - 100 + "px");
        view.asWidget().setHeight(Window.getClientHeight() - 100 + "px");

        registerListeners();
    }

    private void registerListeners() {
        cancelButtonHandlerRegistration = view.getCancelAnchor().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        rollbackButtonHandlerRegistration = view.getRollbackButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // TODO
                hide();
            }
        });

        hideComparePanelEventHandlerRegistration = clientFactory.getEventBus().addHandler(HideComparePanelEvent.TYPE, new HideComparePanelEventHandler() {
            @Override
            public void onEvent(HideComparePanelEvent event) {
                hide();
            }
        });


        showComparePanelEventHandlerRegistration = clientFactory.getEventBus().addHandler(ShowComparePanelEvent.TYPE, new ShowComparePanelEventHandler() {
            @Override
            public void onEvent(ShowComparePanelEvent event) {
                CompareController.this.comparisonProvider = event.getComparisonProvider();
                show();
                retrieveRevisions();
            }
        });
    }

    public void retrieveRevisions() {
        comparisonProvider.getRevisions(new AsyncCallback<List<RevisionDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not retrieve revisions.", caught));
            }

            @Override
            public void onSuccess(List<RevisionDTO> result) {
                view.setAvailableRevisions(result);
                // retrieve default revisions: the first and second one
                if (result.size() > 1) {
                    retrieveRevisionContent(result.get(1).getRevisionID(), result.get(0).getRevisionID());
                } else {
                    // TODO: doesn't seem like a good solution: perhaps show a warning screen saying no revisions are
                    // available?
                    retrieveRevisionContent(result.get(0).getRevisionID(), result.get(0).getRevisionID());
                }
            }
        });
    }

    public void retrieveRevisionContent(final String revisionIDA, final String revisionIDB) {
        if (comparisonProvider != null) {

            comparisonProvider.getRevision(revisionIDA, new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not retrieve revision " + revisionIDA, caught));
                }

                @Override
                public void onSuccess(String result) {
                    view.setRevisionA(result);
                }
            });

            comparisonProvider.getRevision(revisionIDB, new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not retrieve revision " + revisionIDB, caught));
                }

                @Override
                public void onSuccess(String result) {
                    view.setRevisionB(result);
                }
            });
        }
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        rollbackButtonHandlerRegistration.removeHandler();
        cancelButtonHandlerRegistration.removeHandler();
        hideComparePanelEventHandlerRegistration.removeHandler();
        showComparePanelEventHandlerRegistration.removeHandler();
    }

    /**
     * Resizes the dialog, centers and shows the popup.
     */
    public void show() {
        popupPanel.center();
        popupPanel.show();
    }

    /**
     * Call to hide the comparison popup.
     */
    public void hide() {
        view.destroy();
        popupPanel.hide();
    }

    public CompareView getView() {
        return view;
    }
}
