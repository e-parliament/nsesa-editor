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

import java.io.Serializable;
import java.util.Date;

/**
 * A base DTO containing information about a revision of a document (be it a source text or an amendment).
 *
 * Date: 10/07/12 22:34
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class RevisionDTO implements Serializable {

    private PersonDTO person;
    private Date creationDate;
    private Date modificationDate;
    private String revisionID;

    public RevisionDTO() {
    }

    public RevisionDTO(PersonDTO person, Date creationDate, Date modificationDate) {
        this.person = person;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public RevisionDTO(PersonDTO person, Date creationDate, Date modificationDate, String revisionID) {
        this.person = person;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.revisionID = revisionID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RevisionDTO that = (RevisionDTO) o;

        if (!creationDate.equals(that.creationDate)) return false;
        if (modificationDate != null ? !modificationDate.equals(that.modificationDate) : that.modificationDate != null)
            return false;
        if (!person.equals(that.person)) return false;
        if (!revisionID.equals(that.revisionID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = person.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + (modificationDate != null ? modificationDate.hashCode() : 0);
        result = 31 * result + revisionID.hashCode();
        return result;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getRevisionID() {
        return revisionID;
    }

    public void setRevisionID(String revisionID) {
        this.revisionID = revisionID;
    }
}
