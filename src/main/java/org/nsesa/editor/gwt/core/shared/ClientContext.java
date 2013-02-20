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

    PersonDTO getLoggedInPerson();

    void setLoggedInPerson(PersonDTO person);

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
