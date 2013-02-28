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
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Occurrence;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

import java.util.LinkedHashMap;

/**
 * Interface to keep track of the allowed children of a given {@link OverlayWidget} in a given {@link DocumentController}.
 * Date: 08/11/12 11:20
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultCreator.class)
public interface Creator {
    /**
     * Returns a list of all the amendable widget types that are allowed to be created as a sibling.
     * The key of the map is the amendable widget to be used as a sibling where the value is the <code>Occurrence</code>.
     *
     * @return the map of allowed siblings - should never be <tt>null</tt>.
     */
    LinkedHashMap<OverlayWidget, Occurrence> getAllowedSiblings(DocumentController documentController, OverlayWidget overlayWidget);

    /**
     * Returns a map of all the amendable widget types that are allowed to be created as a child.
     * The key of the map is the amendable widget to be used as a child where the value is the <code>Occurrence</code>.
     *
     * @return the map of allowed children - should never be <tt>null</tt>.
     */
    LinkedHashMap<OverlayWidget, Occurrence> getAllowedChildren(DocumentController documentController, OverlayWidget overlayWidget);
}
