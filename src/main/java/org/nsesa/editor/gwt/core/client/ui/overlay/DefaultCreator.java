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

import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Occurrence;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

import java.util.LinkedHashMap;

/**
 * Date: 08/11/12 11:23
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultCreator implements Creator {

    @Override
    public LinkedHashMap<OverlayWidget, Occurrence> getAllowedSiblings(DocumentController documentController, OverlayWidget overlayWidget) {
        return new LinkedHashMap<OverlayWidget, Occurrence>();
    }

    @Override
    public LinkedHashMap<OverlayWidget, Occurrence> getAllowedChildren(DocumentController documentController, OverlayWidget overlayWidget) {
        return new LinkedHashMap<OverlayWidget, Occurrence>();
    }
}
