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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Default implementation of {@link OverlaySnippetFactory} interface by storing all the snippets into a local map.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 8/04/13 11:38
 */
public class DefaultOverlaySnippetFactory implements OverlaySnippetFactory {

    private Map<String, OverlaySnippet> cache = new LinkedHashMap<String, OverlaySnippet>();

    /**
     * Default constructor
     */
    public DefaultOverlaySnippetFactory() {
    }

    @Override
    public OverlaySnippet getSnippet(OverlayWidget widget) {
        return cache.get(widget.getNamespaceURI() + ":" + widget.getType());
    }

    @Override
    public String getCaretPositionClassName() {
        return "";
    }

    /**
     * Register a snippet for the given overlay widget
     *
     * @param widget         The widget for which the overlay snippet is registered
     * @param overlaySnippet The overlay snippet to be registered
     */
    public void registerSnippet(final OverlayWidget widget, final OverlaySnippet overlaySnippet) {
        cache.put(widget.getNamespaceURI() + ":" + widget.getType(), overlaySnippet);
    }
}
