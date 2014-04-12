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
package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * An interface to transform {@link OverlayWidget} object into a String representation.
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 20/11/12 10:59
 */
@ImplementedBy(DefaultFormatter.class)
public interface Formatter {
    /**
     * Formats an overlay widget into a String representation
     *
     * @param widget The overlay widget that will be XML-ized.
     * @return XML representation as String
     */
    String format(OverlayWidget widget);
}
