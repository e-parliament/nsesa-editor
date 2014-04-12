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
import java.util.logging.Logger;

/**
 * Default implementation of {@link OverlaySnippetFactory} interface by storing all the snippets into a local map.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 8/04/13 11:38
 */
public class DefaultOverlaySnippetFactory implements OverlaySnippetFactory {

    private static final Logger LOG = Logger.getLogger(DefaultOverlaySnippetFactory.class.getName());

    private final Map<String, Map<String, OverlaySnippet>> cache = new LinkedHashMap<String, Map<String, OverlaySnippet>>();

    /**
     * Default constructor
     */
    public DefaultOverlaySnippetFactory() {
    }

    @Override
    public OverlaySnippet getSnippet(final OverlayWidget widget) {
        final Map<String, OverlaySnippet> snippets = cache.get(getKey(widget));
        if (snippets != null && !snippets.isEmpty()) {
            if (snippets.size() > 1) {
                LOG.warning("You requested a snippet for " + widget.getType() + ", but multiple snippets have been registered for this widget. Please specify the name of the snippet.");
            }
            return snippets.values().iterator().next();
        }
        return null;
    }

    @Override
    public OverlaySnippet getSnippet(OverlayWidget widget, String templateName) {
        final Map<String, OverlaySnippet> snippets = cache.get(getKey(widget));
        if (snippets != null) {
            return snippets.get(templateName);
        }
        return null;
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
        Map<String, OverlaySnippet> snippetMap = cache.get(getKey(widget));
        if (snippetMap == null) {
            cache.put(getKey(widget), new LinkedHashMap<String, OverlaySnippet>());
        }
        cache.get(getKey(widget)).put(overlaySnippet.getName(), overlaySnippet);
    }

    /**
     * Generate the key to store the overlay widget with in the cache.
     * @param widget the widget to get the key for
     * @return the key
     */
    protected String getKey(OverlayWidget widget) {
        return widget.getNamespaceURI() + ":" + widget.getType();
    }
}
