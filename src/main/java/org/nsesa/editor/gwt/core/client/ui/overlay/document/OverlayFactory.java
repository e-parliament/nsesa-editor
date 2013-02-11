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

import com.google.gwt.dom.client.Element;
import com.google.inject.ImplementedBy;

/**
 * An overlay factory is responsible for translating an element into an amendable widget.
 * This widget will then be used in a separate tree to represent a high(er) level version
 * of the structure in the document.
 * <p/>
 * Date: 04/08/12 17:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultOverlayFactory.class)
public interface OverlayFactory {
    /**
     * Translates a given element into an {@link AmendableWidget}. If the overlay does not succeed (eg. because there
     * is no matching {@link AmendableWidget} found, then null is returned.
     *
     * @param element the element to get the overlaying element for.
     * @return the amendable widget, or null if it cannot be overlaid.
     */
    AmendableWidget getAmendableWidget(Element element);

    AmendableWidget getAmendableWidget(String tag);

    AmendableWidget toAmendableWidget(Element element);

    /**
     * Returns the namespace of the overlay factory
     *
     * @return
     */
    String getNamespace();
}
