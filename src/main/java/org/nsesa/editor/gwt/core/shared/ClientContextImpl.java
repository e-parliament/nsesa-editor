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
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Date: 24/06/12 18:54
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(EDITOR)
public class ClientContextImpl implements ClientContext, Serializable, IsSerializable {

    private HashMap<String, String[]> parameters = new HashMap<String, String[]>();

    private PersonDTO loggedInPerson;

    private String[] roles;

    private String[] documentIDs;

    private String sessionID;

    private String languageIso = "EN";

    private String documentIso = "EN";

    public ClientContextImpl() {
    }

    public void addParameter(String key, String value) {
        addParameter(key, new String[]{value});
    }

    public void addParameter(String key, String[] values) {
        parameters.put(key, values);
    }

    public void removeParameter(String key) {
        parameters.remove(key);
    }

    public String[] getParameter(String key) {
        return parameters.get(key);
    }

    public HashMap<String, String[]> getParameters() {
        return parameters != null ? new HashMap<String, String[]>(parameters) : null;
    }

    public void setParameters(HashMap<String, String[]> parameters) {
        this.parameters = parameters;
    }

    public PersonDTO getLoggedInPerson() {
        return loggedInPerson;
    }

    public void setLoggedInPerson(PersonDTO loggedInPerson) {
        this.loggedInPerson = loggedInPerson;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String[] getRoles() {
        return roles != null ? Arrays.asList(roles).toArray(new String[roles.length]) : null;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String[] getDocumentIDs() {
        return documentIDs != null ? Arrays.asList(documentIDs).toArray(new String[documentIDs.length]) : null;
    }

    public void setDocumentIDs(String[] documentIDs) {
        this.documentIDs = documentIDs;
    }

    public String getLanguageIso() {
        return languageIso;
    }

    public void setLanguageIso(String languageIso) {
        this.languageIso = languageIso;
    }

    public String getDocumentIso() {
        return documentIso;
    }

    public void setDocumentIso(String documentIso) {
        this.documentIso = documentIso;
    }
}
