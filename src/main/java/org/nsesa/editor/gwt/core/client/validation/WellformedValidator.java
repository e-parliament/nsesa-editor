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
package org.nsesa.editor.gwt.core.client.validation;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

/**
 * A simple validator to check if the passed String containing serialized XML is well-formed.
 * Date: 19/02/13 13:56
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class WellformedValidator implements Validator<String> {
    /**
     * Validate that the given serialized XML content is well-formed by using {@link XMLParser#parse(String)}.
     * @param input the input document as serialized XML
     * @return the validation result
     */
    @Override
    public ValidationResult validate(final String input) {
        try {
            final Document document = XMLParser.parse(input);
        } catch (Exception e) {
            return new ValidationResultImpl(false, "Not well-formed " + e.getMessage());
        }
        return new ValidationResultImpl(true, null);
    }
}
