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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import org.nsesa.editor.gwt.core.client.event.ResizeEvent;
import org.nsesa.editor.gwt.core.client.event.ResizeEventHandler;
import org.nsesa.editor.gwt.core.shared.*;

import java.util.ArrayList;
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

    private String revisionA, revisionB;

    protected final PopupPanel popupPanel = new DecoratedPopupPanel(false, true);

    private HandlerRegistration cancelButtonHandlerRegistration;
    private HandlerRegistration rollbackButtonHandlerRegistration;

    private com.google.web.bindery.event.shared.HandlerRegistration hideComparePanelEventHandlerRegistration;
    private com.google.web.bindery.event.shared.HandlerRegistration showComparePanelEventHandlerRegistration;
    private HandlerRegistration revisionAChangeHandlerRegistration;
    private HandlerRegistration revisionBChangeHandlerRegistration;
    private com.google.web.bindery.event.shared.HandlerRegistration resizeEventHandlerRegistration;


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

        resize();
    }

    public void registerListeners() {
        cancelButtonHandlerRegistration = view.getCancelAnchor().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        rollbackButtonHandlerRegistration = view.getRollbackButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (comparisonProvider != null) {
                    comparisonProvider.rollback(view.getRevisionsA().getValue(view.getRevisionsA().getSelectedIndex()));
                    hide();
                }
            }
        });

        final ChangeHandler revisionChangeHandler = new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                int version = view.getRevisionsA().getItemCount() - (view.getRevisionsA().getSelectedIndex());
                view.getRollbackButton().setText("Rollback to version " + version);
                retrieveRevisionContent(
                        view.getRevisionsA().getValue(view.getRevisionsA().getSelectedIndex()),
                        view.getRevisionsB().getValue(view.getRevisionsB().getSelectedIndex()));
            }
        };
        revisionAChangeHandlerRegistration = view.getRevisionsA().addChangeHandler(revisionChangeHandler);
        revisionBChangeHandlerRegistration = view.getRevisionsB().addChangeHandler(revisionChangeHandler);

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
                retrieveRevisions();
            }
        });

        resizeEventHandlerRegistration = clientFactory.getEventBus().addHandler(ResizeEvent.TYPE, new ResizeEventHandler() {
            @Override
            public void onEvent(ResizeEvent event) {
                resize();
            }
        });
    }

    protected void resize() {
        clientFactory.getScheduler().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                view.asWidget().setWidth(Window.getClientWidth() - 100 + "px");
                view.asWidget().setHeight(Window.getClientHeight() - 100 + "px");
                view.adaptScrollPanel();
            }
        });

    }

    public void retrieveRevisions() {
        // reset the revisions
        revisionA = null;
        revisionB = null;

        comparisonProvider.getRevisions(new AsyncCallback<List<RevisionDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not retrieve revisions.", caught));
            }

            @Override
            public void onSuccess(List<RevisionDTO> result) {
                view.setAvailableRevisions(result);
                if (!result.isEmpty()) {
                    view.getRollbackButton().setText("Rollback to version " + (result.size() - 1));
                }
                // retrieve default revisions: the first and second one
                if (result.size() > 1) {
                    retrieveRevisionContent(result.get(1).getRevisionID(), result.get(0).getRevisionID());
                } else {
                    retrieveRevisionContent(result.get(0).getRevisionID(), result.get(0).getRevisionID());
                }
            }
        });
    }

    public void retrieveRevisionContent(final String revisionIDA, final String revisionIDB) {
        if (comparisonProvider != null) {

            comparisonProvider.getRevisionContent(revisionIDA, new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not retrieve revision " + revisionIDA, caught));
                }

                @Override
                public void onSuccess(String result) {
                    revisionA = result;
                    afterRevisionSet();
                }
            });

            comparisonProvider.getRevisionContent(revisionIDB, new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not retrieve revision " + revisionIDB, caught));
                }

                @Override
                public void onSuccess(String result) {
                    revisionB = result;
                    afterRevisionSet();
                }
            });
        }
    }

    private void afterRevisionSet() {
        if (revisionA != null && revisionB != null) {
            // request diffing
            final ArrayList<DiffRequest> commands = new ArrayList<DiffRequest>();

            final DiffRequest diffRequest = new DiffRequest(revisionA, revisionB, DiffMethod.WORD, DiffStyle.TRACK_CHANGES);
            commands.add(diffRequest);

            serviceFactory.getGwtDiffService().diff(commands, new AsyncCallback<ArrayList<DiffResult>>() {
                @Override
                public void onFailure(Throwable caught) {
                    clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not perform diffing.", caught));
                }

                @Override
                public void onSuccess(final ArrayList<DiffResult> result) {
                    clientFactory.getScheduler().scheduleDeferred(new Scheduler.ScheduledCommand() {
                        @Override
                        public void execute() {
                            view.setRevision(result.get(0).getAmendment());
                            if (!popupPanel.isShowing())
                                show();
                        }
                    });
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
        revisionAChangeHandlerRegistration.removeHandler();
        revisionBChangeHandlerRegistration.removeHandler();
        hideComparePanelEventHandlerRegistration.removeHandler();
        showComparePanelEventHandlerRegistration.removeHandler();
        resizeEventHandlerRegistration.removeHandler();
    }

    /**
     * Resizes the dialog, centers and shows the popup.
     */
    public void show() {
        popupPanel.center();
        popupPanel.show();
        view.adaptScrollPanel();
    }

    /**
     * Call to hide the comparison popup.
     */
    public void hide() {
        popupPanel.hide();
    }

    public CompareView getView() {
        return view;
    }
}
