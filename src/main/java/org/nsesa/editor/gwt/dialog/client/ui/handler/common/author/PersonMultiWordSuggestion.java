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
package org.nsesa.editor.gwt.dialog.client.ui.handler.common.author;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import org.nsesa.editor.gwt.core.shared.PersonDTO;

/**
 * Date: 20/02/13 15:39
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class PersonMultiWordSuggestion extends MultiWordSuggestOracle.MultiWordSuggestion {
    private final PersonDTO person;

    public PersonMultiWordSuggestion() {
        person = null;
    }

    public PersonMultiWordSuggestion(PersonDTO person) {
        super(person.getLastName() + " " + person.getName(), person.getLastName() + " " + person.getName());
        this.person = person;
    }

    public PersonDTO getPerson() {
        return person;
    }
}
