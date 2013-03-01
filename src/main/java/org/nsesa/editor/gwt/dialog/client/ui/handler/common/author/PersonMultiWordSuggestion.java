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
 * An extension to the {@link MultiWordSuggestOracle.MultiWordSuggestion} to keep a reference to the given
 * {@link PersonDTO}. Used in combination with the {@link PersonMultiWordSuggestionOracle}.
 *
 * Date: 20/02/13 15:39
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class PersonMultiWordSuggestion extends MultiWordSuggestOracle.MultiWordSuggestion {

    /**
     * A reference to the underlying person DTO.
     */
    private final PersonDTO person;

    public PersonMultiWordSuggestion() {
        person = null;
    }

    /**
     * Creates a suggestion based on the passed {@link PersonDTO}.
     * @param person the person DTO
     */
    public PersonMultiWordSuggestion(final PersonDTO person) {
        super(person.getDisplayName(), person.getDisplayName());
        this.person = person;
    }

    /**
     * Get the reference to the person DTO that was used to build this suggestion.
     * @return the person DTO
     */
    public PersonDTO getPerson() {
        return person;
    }
}
