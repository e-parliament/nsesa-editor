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
package org.nsesa.editor.gwt.core.client.validation;

import com.google.inject.ImplementedBy;

/**
 * Interface for a {@link Validator} for a given type <tt>T</tt>.
 * Date: 19/02/13 13:32
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(WellformedValidator.class)
public interface Validator<T> {

    /**
     * Validate a given type <tt>T</tt> instance.
     * @param toValidate the instance to validate
     * @return the validation result
     */
    ValidationResult validate(T toValidate);
}
