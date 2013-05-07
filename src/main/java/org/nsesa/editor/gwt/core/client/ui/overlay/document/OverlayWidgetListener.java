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

import org.nsesa.editor.gwt.core.client.ui.document.OverlayWidgetAware;

/**
 * A listener interface for {@link OverlayWidget}s that will be called during various operations
 * such as adding or removing {@link OverlayWidgetAware}s or child {@link OverlayWidget}s.
 * Date: 23/10/12 14:15
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface OverlayWidgetListener {

    /**
     * Called before an {@link OverlayWidgetAware} is added to the given <tt>overlayWidget</tt>.
     *
     * @param overlayWidget       the overlay widget to which the amendment controller is added
     * @param amendmentController the amendment controller to be added
     * @return <tt>true</tt> if you want to veto the addition of this amendment controller
     */
    boolean beforeOverlayWidgetAwareAdded(OverlayWidget overlayWidget, OverlayWidgetAware amendmentController);

    /**
     * Callback after an {@link OverlayWidgetAware} has been added to a given <tt>overlayWidget</tt>.
     *
     * @param overlayWidget       the overlay widget to which the amendment controller was added
     * @param amendmentController the amendment controller that has been added
     */
    void afterOverlayWidgetAwareAdded(OverlayWidget overlayWidget, OverlayWidgetAware amendmentController);

    /**
     * Called before an {@link OverlayWidgetAware} is removed from the given <tt>overlayWidget</tt>.
     *
     * @param overlayWidget       the overlay widget from which the amendment controller is removed
     * @param amendmentController the amendment controller to be removed
     * @return <tt>true</tt> if you want to veto the removal of this amendment controller
     */
    boolean beforeOverlayWidgetAwareRemoved(OverlayWidget overlayWidget, OverlayWidgetAware amendmentController);

    /**
     * Callback after an {@link OverlayWidgetAware} has been removed from a given <tt>overlayWidget</tt>.
     *
     * @param overlayWidget       the overlay widget from which the amendment controller was removed
     * @param amendmentController the amendment controller that has been removed
     */
    void afterOverlayWidgetAwareRemoved(OverlayWidget overlayWidget, OverlayWidgetAware amendmentController);

    /**
     * Called before an {@link OverlayWidget} is added to the given <tt>overlayWidget</tt>.
     *
     * @param overlayWidget the overlay widget to which the overlay widget is added
     * @param child         the overlay widget to be added
     * @return <tt>true</tt> if you want to veto the addition of this overlay widget
     */
    boolean beforeOverlayWidgetAdded(OverlayWidget overlayWidget, OverlayWidget child);

    /**
     * Called after an {@link OverlayWidget} is added to the given <tt>overlayWidget</tt>.
     *
     * @param overlayWidget the overlay widget to which the amendment controller was added
     * @param child         the overlay widget that was added
     */
    void afterOverlayWidgetAdded(OverlayWidget overlayWidget, OverlayWidget child);

    /**
     * Called before an {@link OverlayWidget} is removed from the given <tt>overlayWidget</tt>.
     *
     * @param overlayWidget the overlay widget from which the overlay widget is removed
     * @param child         the overlay widget to be removed
     * @return <tt>true</tt> if you want to veto the removal of this overlay widget
     */
    boolean beforeOverlayWidgetRemoved(OverlayWidget overlayWidget, OverlayWidget child);

    /**
     * Called after an {@link OverlayWidget} is removed from the given <tt>overlayWidget</tt>.
     *
     * @param overlayWidget the overlay widget from which the amendment controller was removed
     * @param child         the overlay widget that was removed
     */
    void afterOverlayWidgetRemoved(OverlayWidget overlayWidget, OverlayWidget child);
}
