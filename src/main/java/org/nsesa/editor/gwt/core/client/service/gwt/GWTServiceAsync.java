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
package org.nsesa.editor.gwt.core.client.service.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.PersonDTO;

/**
 * Async interface of the {@link GWTService}.
 */
public interface GWTServiceAsync {

    /**
     * Authenticate the client context (set the principal and roles).
     */
    void authenticate(ClientContext clientContext, AsyncCallback<ClientContext> async);

    /**
     * Get a given {@link PersonDTO} based on a given <tt>id</tt>.
     * @param clientContext the client context.
     * @param id            the id of the person
     * @param async         the callback
     */
    void getPerson(ClientContext clientContext, String id, AsyncCallback<PersonDTO> async);
}
