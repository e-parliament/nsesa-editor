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

/**
 * A Data Transfer Object (DTO) for the document content.
 * Date: 26/06/12 19:15
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentContentDTO implements IsSerializable, Serializable {

    /**
     * The primary key identifier for this particular document translation.
     */
    private String documentID;

    /**
     * The content of the document.
     */
    private String content;
    /**
     * The document content type.
     */
    private String documentContentType;

    public DocumentContentDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentContentDTO that = (DocumentContentDTO) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (documentContentType != null ? !documentContentType.equals(that.documentContentType) : that.documentContentType != null)
            return false;
        if (documentID != null ? !documentID.equals(that.documentID) : that.documentID != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = documentID != null ? documentID.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (documentContentType != null ? documentContentType.hashCode() : 0);
        return result;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDocumentContentType() {
        return documentContentType;
    }

    public void setDocumentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
    }
}
