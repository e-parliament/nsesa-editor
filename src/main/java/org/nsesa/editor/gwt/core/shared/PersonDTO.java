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
import com.inspiresoftware.lib.dto.geda.annotations.Dto;
import com.inspiresoftware.lib.dto.geda.annotations.DtoField;

import java.io.Serializable;

/**
 * A Data Transfer Object (DTO) for a person.
 * Date: 26/06/12 19:15
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Dto
public class PersonDTO implements IsSerializable, Serializable {

    /**
     * The public key identifier for this person.
     */
    @DtoField
    private String personID;
    /**
     * The username of this person.
     */
    @DtoField
    private String username;
    /**
     * The first name of this person.
     */
    @DtoField
    private String name;

    /**
     * The family name of this person.
     */
    @DtoField
    private String lastName;

    public PersonDTO() {
    }

    public PersonDTO(String personID, String username, String name, String lastName) {
        this.personID = personID;
        this.username = username;
        this.name = name;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonDTO personDTO = (PersonDTO) o;

        if (personID != null ? !personID.equals(personDTO.personID) : personDTO.personID != null) return false;
        if (lastName != null ? !lastName.equals(personDTO.lastName) : personDTO.lastName != null) return false;
        if (name != null ? !name.equals(personDTO.name) : personDTO.name != null) return false;
        if (username != null ? !username.equals(personDTO.username) : personDTO.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = personID != null ? personID.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    /**
     * Returns the display name of this person (name + space + family name to upper case)
     *
     * @return the display name
     */
    public String getDisplayName() {
        return name + " " + lastName.toUpperCase();
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
