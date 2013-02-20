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
        clientFactory.getEventBus().addHandler(BootstrapEvent.TYPE, new BootstrapEventHandler() {
            @Override
            public void onEvent(BootstrapEvent event) {
                setUpLanguages();
            }
        });

        clientFactory.getEventBus().addHandler(AuthenticatedEvent.TYPE, new AuthenticatedEventHandler() {
            @Override
            public void onEvent(AuthenticatedEvent event) {
                view.setLoggedInPersonName(clientFactory.getClientContext().getLoggedInPerson().getName());
                view.setLoggedInPersonRoles(clientFactory.getClientContext().getRoles());
            }
        });
    }

    protected void setUpLanguages() {
        final List<String> locales = new ArrayList<String>();
        for (final String localeName : LocaleInfo.getAvailableLocaleNames()) {
            locales.add(localeName);
        }
        view.setAvailableLanguages(locales);
    }

    public HeaderView getView() {
        return view;
    }
}
