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
package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.inject.ImplementedBy;

/**
 * An interface to provide localizable information about amendable widgets
 * User: groza
 * Date: 28/01/13
 * Time: 9:55
 */
@ImplementedBy(DefaultOverlayLocalizableResource.class)
public interface OverlayLocalizableResource {
    /**
     * Returns localized tag name for the given amendable widget
     *
     * @param widget
     * @return
     */
    abstract String getName(OverlayWidget widget);

    /**
     * Returns localized details for the given amendable widget
     *
     * @param widget
     * @return
     */
    abstract String getDescription(OverlayWidget widget);
}
