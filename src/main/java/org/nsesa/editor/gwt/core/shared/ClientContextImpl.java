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

    private String principal;

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

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
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
