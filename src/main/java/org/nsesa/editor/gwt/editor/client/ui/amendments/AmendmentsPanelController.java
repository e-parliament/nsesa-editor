package org.nsesa.editor.gwt.editor.client.ui.amendments;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEventHandler;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerStatusUpdatedEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerStatusUpdatedEventHandler;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentView;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentRefreshRequestEventHandler;

import java.util.ArrayList;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(EDITOR)
public class AmendmentsPanelController {

    private final ClientFactory clientFactory;
    private final AmendmentsPanelView view;
    private final AmendmentManager amendmentManager;

    @Inject
    public AmendmentsPanelController(ClientFactory clientFactory, AmendmentsPanelView view, AmendmentManager amendmentManager) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.amendmentManager = amendmentManager;
        registerListeners();
    }

    public AmendmentsPanelView getView() {
        return view;
    }

    public AmendmentManager getAmendmentManager() {
        return amendmentManager;
    }

    private void registerListeners() {
        EventBus eventBus = clientFactory.getEventBus();
        eventBus.addHandler(DocumentRefreshRequestEvent.TYPE, new DocumentRefreshRequestEventHandler() {
            @Override
            public void onEvent(DocumentRefreshRequestEvent event) {
                refreshAmendments();
            }
        });

        eventBus.addHandler(AmendmentContainerInjectedEvent.TYPE, new AmendmentContainerInjectedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerInjectedEvent event) {
                refreshAmendments();
            }
        });

        eventBus.addHandler(AmendmentContainerStatusUpdatedEvent.TYPE, new AmendmentContainerStatusUpdatedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerStatusUpdatedEvent event) {
                refreshAmendments();
            }
        });
    }

    private void refreshAmendments() {
        ArrayList<AmendmentView> amendmentViews = new ArrayList<AmendmentView>();
        for (AmendmentController amendmentController : amendmentManager.getAmendmentControllers()) {
            amendmentViews.add(amendmentController.getExtendedView());
        }
        view.refreshAmendments(amendmentViews);
    }
}
