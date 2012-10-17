package org.nsesa.editor.gwt.core.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.inject.ImplementedBy;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Date: 24/06/12 18:54
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ClientContextImpl.class)
public interface ClientContext extends Serializable, IsSerializable {

    public static final String DOCUMENT_ID = "documentID";

    void addParameter(String key, String value);

    void addParameter(String key, String[] values);

    void removeParameter(String key);

    String[] getParameter(String key);

    HashMap<String, String[]> getParameters();

    void setParameters(HashMap<String, String[]> parameters);

    String getPrincipal();

    void setPrincipal(String principal);

    String getSessionID();

    void setSessionID(String sessionID);

    String[] getRoles();

    void setRoles(String[] roles);

    String[] getDocumentIDs();

    void setDocumentIDs(String[] documentIDs);

    String getLanguageIso();

    void setLanguageIso(String languageIso);

    String getDocumentIso();

    void setDocumentIso(String documentIso);
}
