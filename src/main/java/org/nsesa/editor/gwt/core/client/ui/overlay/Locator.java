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
 * Class responsible for reporting the location of an {@link OverlayWidget}.
 * Date: 06/07/12 17:21
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultLocator.class)
public interface Locator {

    /**
     * Get the position of a given <tt>overlayWidget</tt> in a given <tt>languageIso</tt>.
     *
     * @param overlayWidget    the overlay widget to get the position for
     * @param languageIso      the ISO code of the language to get the position in
     * @param childrenIncluded <tt>true</tt> if the children are taken into account
     * @return the position of this overlay widget in the tree
     */
    String getLocation(OverlayWidget overlayWidget, String languageIso, boolean childrenIncluded);

    /**
     * Get the number for a given <tt>overlayWidget</tt>. The reported number depends various cases, but can be thought
     * of in general that if the {@link NumberingType} is constant, the {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget#getTypeIndex()}
     * will instead be used to remove confusion about the path.
     *
     * @param overlayWidget the overlay widget to get the number for
     * @param languageIso   the ISO code of the language
     * @param format        if <tt>true</tt>, we format the numbering (if possible), so instead of '7b', you would get '(7b)'
     * @return the number, should never return <tt>null</tt>
     */
    String getNum(final OverlayWidget overlayWidget, final String languageIso, boolean format);
}
