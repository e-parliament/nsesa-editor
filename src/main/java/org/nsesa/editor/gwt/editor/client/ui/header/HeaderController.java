/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.editor.client.ui.header;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.AuthenticatedEvent;
import org.nsesa.editor.gwt.core.client.event.AuthenticatedEventHandler;
import org.nsesa.editor.gwt.core.client.event.BootstrapEvent;
import org.nsesa.editor.gwt.core.client.event.BootstrapEventHandler;
import org.nsesa.editor.gwt.core.client.util.Scope;

import java.util.ArrayList;
import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Controller for the editor's header component.
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(EDITOR)
public class HeaderController {

    /**
     * The service factory, giving access to the RPC services.
     */
    protected final ServiceFactory serviceFactory;

    /**
     * The client factory, giving access to the local dependencies and user context
     */
    protected final ClientFactory clientFactory;

    /**
     * The view
     */
    protected final HeaderView view;
    private HandlerRegistration bootstrapEventHandlerRegistration;
    private HandlerRegistration authenticatedEventHandlerRegistration;

    @Inject
    public HeaderController(final ClientFactory clientFactory, final ServiceFactory serviceFactory, final HeaderView view) {
        this.serviceFactory = serviceFactory;
        this.clientFactory = clientFactory;
        this.view = view;

        registerListeners();
    }

    private void registerListeners() {
        bootstrapEventHandlerRegistration = clientFactory.getEventBus().addHandler(BootstrapEvent.TYPE, new BootstrapEventHandler() {
            @Override
            public void onEvent(BootstrapEvent event) {
                setUpLanguages();
            }
        });

        authenticatedEventHandlerRegistration = clientFactory.getEventBus().addHandler(AuthenticatedEvent.TYPE, new AuthenticatedEventHandler() {
            @Override
            public void onEvent(AuthenticatedEvent event) {
                view.setLoggedInPersonName(clientFactory.getClientContext().getLoggedInPerson().getName());
                view.setLoggedInPersonRoles(clientFactory.getClientContext().getRoles());
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        bootstrapEventHandlerRegistration.removeHandler();
        authenticatedEventHandlerRegistration.removeHandler();
    }

    /**
     * Set up the language after the {@link BootstrapEvent} has been received. Sets all available languages according
     * to {@link com.google.gwt.i18n.client.LocaleInfo#getAvailableLocaleNames()}.
     */
    protected void setUpLanguages() {
        final List<String> locales = new ArrayList<String>();
        // no System.arrayCopy because it's not emulated
        for (final String localeName : LocaleInfo.getAvailableLocaleNames()) {
            // note that the locale name is 'like the ISO code, except for the default (which is named 'default')'
            locales.add(localeName);
        }
        view.setAvailableLanguages(locales);
    }

    /**
     * Return the associated view.
     *
     * @return the view
     */
    public HeaderView getView() {
        return view;
    }
}
