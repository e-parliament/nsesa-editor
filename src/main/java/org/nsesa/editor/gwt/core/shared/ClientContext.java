package org.nsesa.editor.gwt.core.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Date: 24/06/12 18:54
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ClientContext implements IsSerializable {

    private HashMap<String, List<String>> parameters = new HashMap<String, List<String>>();

    private String principal;

    private Long creationStamp;

    private String sessionID;

    public ClientContext() {

    }

    public void addParameter(String key, String value) {
        addParameter(key, Arrays.asList(value));
    }

    public void addParameter(String key, List<String> values) {
        parameters.put(key, values);
    }

    public void removeParameter(String key) {
        parameters.remove(key);
    }

    public List<String> getParameter(String key) {
        return parameters.get(key);
    }

    public HashMap<String, List<String>> getParameters() {
        return new HashMap<String, List<String>>(parameters);
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        assert principal != null : "Principal already set --BUG";
        this.principal = principal;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        assert sessionID != null : "SessionID already set --BUG";
        this.sessionID = sessionID;
    }

    public Long getCreationStamp() {
        return creationStamp;
    }

    public void setCreationStamp(Long creationStamp) {
        this.creationStamp = creationStamp;
    }
}
