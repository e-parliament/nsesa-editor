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
package org.nsesa.editor.gwt.core.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.util.Date;

/**
 * A Data Transfer Object (DTO) for a non-logical document (eg. translation).
 * Date: 26/06/12 19:15
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentDTO implements IsSerializable, Serializable {

    /**
     * The primary key identifier for this particular document translation.
     */
    private String documentID;

    /**
     * The name of the document.
     */
    private String name;
    /**
     * The 2 letter ISO code for this translation.
     */
    private String languageIso;

    /**
     * A flag indicating if this document is amendable or not.
     */
    private boolean amendable;

    /**
     * The deadline for this document.
     */
    private Date deadline;

    public DocumentDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentDTO that = (DocumentDTO) o;

        if (documentID != null ? !documentID.equals(that.documentID) : that.documentID != null) return false;
        if (languageIso != null ? !languageIso.equals(that.languageIso) : that.languageIso != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = documentID != null ? documentID.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (languageIso != null ? languageIso.hashCode() : 0);
        return result;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getLanguageIso() {
        return languageIso;
    }

    public void setLanguageIso(String languageIso) {
        this.languageIso = languageIso;
    }

    public boolean isAmendable() {
        return amendable;
    }

    public void setAmendable(boolean amendable) {
        this.amendable = amendable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline != null ? new Date(deadline.getTime()) : null;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
