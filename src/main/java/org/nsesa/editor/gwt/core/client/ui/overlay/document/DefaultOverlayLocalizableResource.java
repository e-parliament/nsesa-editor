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
package org.nsesa.editor.gwt.core.client.ui.overlay.document;

/**
 * Default implementation for <code>OverlayLocalizableResource<code/> interface returning the overlay widget type
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 28/01/13 9:59
 */
public class DefaultOverlayLocalizableResource implements OverlayLocalizableResource {

    /**
     * Return the type of the overlay widget
     *
     * @param widget The overlay widget to be processed
     * @return The overlay widget type
     */
    @Override
    public String getName(OverlayWidget widget) {
        return widget.getType();
    }

    /**
     * Return the type of the overlay widget
     *
     * @param widget The overlay widget to be processed
     * @return The overlay widget type
     */
    @Override
    public String getDescription(OverlayWidget widget) {
        return widget.getType();
    }

    /**
     * The default implementation is not associated with any namespace.
     *
     * @return
     */
    @Override
    public String getNamespaceURI() {
        return null;
    }
}
