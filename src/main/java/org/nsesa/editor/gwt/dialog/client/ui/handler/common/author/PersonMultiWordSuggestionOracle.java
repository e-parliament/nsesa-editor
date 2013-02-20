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
package org.nsesa.editor.gwt.dialog.client.ui.handler.common.author;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.shared.PersonDTO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Date: 20/02/13 15:39
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 * @see <a href="http://eggsylife.co.uk"></a>
 */
public class PersonMultiWordSuggestionOracle extends MultiWordSuggestOracle {

    private final ServiceFactory serviceFactory;
    private final ClientFactory clientFactory;

    public PersonMultiWordSuggestionOracle(ServiceFactory serviceFactory, ClientFactory clientFactory) {
        this.serviceFactory = serviceFactory;
        this.clientFactory = clientFactory;
    }

    public PersonMultiWordSuggestionOracle(String whitespaceChars, ServiceFactory serviceFactory, ClientFactory clientFactory) {
        super(whitespaceChars);
        this.serviceFactory = serviceFactory;
        this.clientFactory = clientFactory;
    }

    @Override
    public void requestSuggestions(final Request request, final Callback callback) {

        serviceFactory.getGwtAmendmentService().getAvailableAuthors(clientFactory.getClientContext(), request.getQuery(), new AsyncCallback<ArrayList<PersonDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                // woops? No suggestions available ...
                callback.onSuggestionsReady(request, new Response());

            }

            @Override
            public void onSuccess(ArrayList<PersonDTO> result) {
                final Collection<PersonMultiWordSuggestion> suggestions = new ArrayList<PersonMultiWordSuggestion>();
                for (PersonDTO personDTO : result) {
                    suggestions.add(new PersonMultiWordSuggestion(personDTO));
                }
                callback.onSuggestionsReady(request, new Response(suggestions));
            }
        });
    }
}
