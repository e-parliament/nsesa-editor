package org.nsesa.editor.gwt.editor.client.ui.header;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.AuthenticatedEvent;
import org.nsesa.editor.gwt.core.client.event.AuthenticatedEventHandler;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(EDITOR)
public class HeaderController {

    private final ServiceFactory serviceFactory;
    private final ClientFactory clientFactory;
    private final HeaderView view;

    @Inject
    public HeaderController(final ClientFactory clientFactory, final ServiceFactory serviceFactory, final HeaderView view) {
        this.serviceFactory = serviceFactory;
        this.clientFactory = clientFactory;
        this.view = view;

        registerListeners();
    }

    private void registerListeners() {
        clientFactory.getEventBus().addHandler(AuthenticatedEvent.TYPE, new AuthenticatedEventHandler() {
            @Override
            public void onEvent(AuthenticatedEvent event) {
                view.setLoggedInPersonName(clientFactory.getClientContext().getPrincipal());
                view.setLoggedInPersonRoles(clientFactory.getClientContext().getRoles());
            }
        });
    }

    public HeaderView getView() {
        return view;
    }
}
