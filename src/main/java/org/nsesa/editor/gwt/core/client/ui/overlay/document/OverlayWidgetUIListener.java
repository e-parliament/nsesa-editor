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

/**
 * UI events listener for a {@link OverlayWidget}.
 * Date: 30/06/12 19:10
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface OverlayWidgetUIListener {

    /**
     * Callback when the <tt>sender</tt> was clicked.
     *
     * @param sender the overlay widget that was clicked
     */
    void onClick(OverlayWidget sender);

    /**
     * Callback when the <tt>sender</tt> was double clicked.
     *
     * @param sender the overlay widget that was double clicked
     */
    void onDblClick(OverlayWidget sender);

    /**
     * Callback when the <tt>sender</tt> was hovered over.
     *
     * @param sender the overlay widget that was hovered
     */
    void onMouseOver(OverlayWidget sender);

    /**
     * Callback when the <tt>sender</tt> lost the mouse hoover.
     *
     * @param sender the overlay widget that lost the mouse hoover
     */
    void onMouseOut(OverlayWidget sender);

}
