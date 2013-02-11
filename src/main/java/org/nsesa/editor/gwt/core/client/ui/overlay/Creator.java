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
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.LinkedHashMap;

/**
 * Date: 08/11/12 11:20
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultCreator.class)
public interface Creator {
    /**
     * Returns a list of all the amendable widget types that are allowed to be created as a sibling.
     * The key of the map is the title (or resource key), where the value is the amendable widget to be used as a sibling.
     *
     * @return the map of allowed siblings - should never be <tt>null</tt>.
     */
    LinkedHashMap<String, AmendableWidget> getAllowedSiblings(DocumentController documentController, AmendableWidget amendableWidget);

    /**
     * Returns a map of all the amendable widget types that are allowed to be created as a child.
     * The key of the map is the title (or resource key), where the value is the amendable widget to be used as a child.
     *
     * @return the map of allowed children - should never be <tt>null</tt>.
     */
    LinkedHashMap<String, AmendableWidget> getAllowedChildren(DocumentController documentController, AmendableWidget amendableWidget);
}
