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
package org.nsesa.editor.gwt.core.client.util;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 21/09/13 22:18
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultIDGenerator implements IDGenerator {
    public static final String PREFIX_GLOBAL = "global", PREFIX_LOCAL = "local", PREFIX_CURRENT = "current";

    public static final String SEPARATOR = "-";


    /**
     * Keeps track of the counter per element type for local ids.
     */
    private Map<String, Counter> localTypeCounter = new HashMap<String, Counter>();

    /**
     * Keeps track of the counter per element type for current ids.
     */
    private Map<String, Counter> currentTypeCounter = new HashMap<String, Counter>();

    @Override
    public String generateGlobalId() {
        return PREFIX_GLOBAL + SEPARATOR + UUID.uuid();
    }

    /**
     * Generates local ids (eg. 'local-point-3', 'local-paragraph-14', ...). They are guaranteed to be unique per type
     * and per namespace (provided there are no similarly generated ids already in the document).
     * <p/>
     * Note that it's up to the caller to set this ID on the <tt>overlayWidget</tt>!
     *
     * @param overlayWidget the overlay widget to get the local id for
     * @return the generated ID
     */
    @Override
    public String generateLocalId(OverlayWidget overlayWidget) {
        Counter counter = localTypeCounter.get(overlayWidget.getType().toLowerCase());
        if (counter == null) {
            counter = new Counter();
            localTypeCounter.put(overlayWidget.getType().toLowerCase(), counter);
        }
        return PREFIX_LOCAL + SEPARATOR + overlayWidget.getType().toLowerCase() + SEPARATOR + counter.incrementAndGet();
    }

    /**
     * Generates current ids (eg. 'local-point-3', 'local-paragraph-14', ...). They are guaranteed to be unique per type
     * and per namespace (provided there are no similarly generated ids already in the document).
     * <p/>
     * Note that it's up to the caller to set this ID on the <tt>overlayWidget</tt>!
     *
     * @param overlayWidget the overlay widget to get the local id for
     * @return the generated ID
     */
    @Override
    public String generateCurrentId(OverlayWidget overlayWidget) {
        Counter counter = currentTypeCounter.get(overlayWidget.getType().toLowerCase());
        if (counter == null) {
            counter = new Counter();
            currentTypeCounter.put(overlayWidget.getType().toLowerCase(), counter);
        }
        return PREFIX_CURRENT + SEPARATOR + overlayWidget.getType().toLowerCase() + SEPARATOR + counter.incrementAndGet();
    }
}
