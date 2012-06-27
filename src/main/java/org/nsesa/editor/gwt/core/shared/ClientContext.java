package org.nsesa.editor.gwt.core.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Date: 24/06/12 18:54
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ClientContext implements Serializable, IsSerializable {

    public static final String DOCUMENT_ID = "documentID";

    private HashMap<String, String[]> parameters = new HashMap<String, String[]>();

    private String principal;

    private String[] roles;

    private String[] documentIDs;

    private String sessionID;

    public ClientContext() {

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
}
