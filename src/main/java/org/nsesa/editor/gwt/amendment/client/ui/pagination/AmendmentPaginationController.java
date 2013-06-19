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
package org.nsesa.editor.gwt.amendment.client.ui.pagination;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerInjectedEvent;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerInjectedEventHandler;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerStatusUpdatedEvent;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerStatusUpdatedEventHandler;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.pagination.PaginationController;
import org.nsesa.editor.gwt.core.client.ui.pagination.PaginationView;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * <code>PaginationController</code> class is responsible to control {@link org.nsesa.editor.gwt.core.client.ui.pagination.PaginationView} view and
 * to react to any amendment changes the user performs in the application.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 30/11/12 15:29
 */
@Scope(DOCUMENT)
@Singleton
public class AmendmentPaginationController extends PaginationController {

    private HandlerRegistration amendmentContainerInjectedEventHandlerRegistration;
    private HandlerRegistration amendmentContainerStatusUpdatedEventHandlerRegistration;


    /**
     * Create <code>PaginationController</code> object with the given parameters
     *
     * @param documentEventBus The event bus used to manage events
     * @param paginationView   The view associated to controller
     */
    @Inject
    public AmendmentPaginationController(DocumentEventBus documentEventBus, PaginationView paginationView) {
        super(documentEventBus, paginationView);
        registerListeners();
    }

    /**
     * Refresh the pagination as a reaction of the events occurred in the system
     */
    private void registerListeners() {
        amendmentContainerInjectedEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerInjectedEvent.TYPE, new AmendmentContainerInjectedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerInjectedEvent event) {
                resetPage();
            }
        });

        amendmentContainerStatusUpdatedEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerStatusUpdatedEvent.TYPE, new AmendmentContainerStatusUpdatedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerStatusUpdatedEvent event) {
                resetPage();
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        super.removeListeners();
        amendmentContainerInjectedEventHandlerRegistration.removeHandler();
        amendmentContainerStatusUpdatedEventHandlerRegistration.removeHandler();
    }

}
