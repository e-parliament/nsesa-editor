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
package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * An interface to transform <code>AmendableWidget<code/> object into its XML representation
 * User: groza
 * Date: 20/11/12
 * Time: 10:59
 */
@ImplementedBy(DefaultTransformer.class)
public interface Transformer {
    /**
     * Transforms an amendable widget into XML representation
     *
     * @param widget The amendable widget that will be XML-ized.
     * @return XML representation as String
     */
    String transform(AmendableWidget widget);
}
