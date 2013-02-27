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
package org.nsesa.editor.gwt.core.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.inject.ImplementedBy;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Client context giving access to all things client related and his/her environment. Will be passed on to the backend
 * on pretty much every RPC call. Note that this can be altered by the client, so make sure to do an actual validation
 * on its content before assuming everything is in order and granting access.
 *
 * Date: 24/06/12 18:54
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ClientContextImpl.class)
public interface ClientContext extends Serializable, IsSerializable {

    /**
     * Default variable name for the document identifier.
     */
    public static final String DOCUMENT_ID = "documentID";

    /**
     * Add a general free-form parameter to the existing map of parameters.
     * @param key       the key
     * @param values    the value(s)
     */
    void addParameter(String key, String... values);

    /**
     * Remove a given parameter via its key from the map of parameters. If no entry with the given key exists, it will
     * not do anything.
     * @param key   the key to remove
     */
    void removeParameter(String key);

    /**
     * Get the values associated with a given <tt>key</tt>.
     * @param key
     * @return
     */
    String[] getParameter(String key);

    /**
     * Returns a copy of the full map of parameters.
     * @return the map of parameters
     */
    HashMap<String, String[]> getParameters();

    /**
     * Set a new map of parameters on the client context.
     * @param parameters    the new map of parameters
     */
    void setParameters(HashMap<String, String[]> parameters);

    /**
     * Get the logged in person DTO associated with this client context.
     * @return  the logged in person, or <tt>null</tt> if no backend authentication has been performed yet
     */
    PersonDTO getLoggedInPerson();

    /**
     * Sets the logged in person into the client context.
     * @param person    the person to set
     */
    void setLoggedInPerson(PersonDTO person);

    /**
     * Returns the session identifier.
     * @return the session identifier
     */
    String getSessionID();

    /**
     * Sets a session identifier
     * @param sessionID the session identifier
     */
    void setSessionID(String sessionID);

    /**
     * Returns a copy of the roles for this client context
     * @return the roles
     */
    String[] getRoles();

    /**
     * Sets the roles on this client context
     * @param roles the roles to set
     */
    void setRoles(String[] roles);

    /**
     * Returns a copy of the document identifiers
     * @return the document identifiers
     */
    String[] getDocumentIDs();

    void setDocumentIDs(String[] documentIDs);

    /**
     * Returns the UI language ISO code.
     * @return the UI language ISO code
     */
    String getUILanguageISOCode();

    /**
     * Set the UI language ISO code for this context
     * @param languageIso the ISO code for this context
     */
    void setUILanguageISOCOde(String languageIso);

    /**
     * Returns the default document's translation for this client.
     * @return the ISO code of the translation to serve to this client.
     */
    String getDocumentTranslationLanguageCode();

    /**
     * Set the default document's translation ISO code.
     * @param documentIso the document's ISO code
     */
    void setDocumentTranslationLanguageCode(String documentIso);
}
