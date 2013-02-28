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
     * Get the location of a given <tt>newChild</tt> that has not yet been added in the
     * collection of the {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget#getChildOverlayWidgets()}
     * <tt>parentOverlayWidget</tt>.
     * <p/>
     * This location will be done in a certain language specified by the given <tt>languageIso</tt>, and can take the
     * children of the element into account, or calculate it standalone.
     * <p/>
     * To clarify the last point: for example, when an amendment is created on an article, its position would be
     * different if the amendment included the entire complex structure of the article, or if it were standalone.
     *
     * @param parentOverlayWidget the parent overlay widget
     * @param newChild            the new child to get the position for
     * @param languageIso         the ISO code of the language to get the position in
     * @param childrenIncluded    <tt>true</tt> if the children are taken into account
     * @return the position of this overlay widget in the tree
     */
    String getLocation(OverlayWidget parentOverlayWidget, OverlayWidget newChild, String languageIso, boolean childrenIncluded);

    /**
     * Get the position of a given <tt>overlayWidget</tt> in a given <tt>languageIso</tt>.
     *
     * @param overlayWidget    the overlay widget to get the position for
     * @param languageIso      the ISO code of the language to get the position in
     * @param childrenIncluded <tt>true</tt> if the children are taken into account
     * @return the position of this overlay widget in the tree
     */
    String getLocation(OverlayWidget overlayWidget, String languageIso, boolean childrenIncluded);
}
