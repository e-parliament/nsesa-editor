package org.nsesa.editor.gwt.core.shared;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;

import java.io.Serializable;
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
        Log.info("----------------- CLIENT CONTEXT () -------------------------");
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
        return parameters;
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
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String[] getDocumentIDs() {
        return documentIDs;
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
