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
package org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar.create;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * View for the {@link ActionBarCreatePanelController}.
 * <p/>
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ActionBarCreatePanelViewImpl.class)
public interface ActionBarCreatePanelView extends IsWidget {

    /**
     * Sets a UI listener to get callbacks on when a new element link is clicked.
     * @param uiListener the UI listener
     */
    void setUIListener(UIListener uiListener);

    /**
     * Attaches this view to the DOM.
     */
    void attach();

    /**
     * Adds a new anchor based on the given <tt>title</tt> for the passed <tt>overlayWidget</tt>.
     *
     * @param title         the title for the link
     * @param overlayWidget the child overlay widget
     */
    void addChildAmendableWidget(String title, OverlayWidget overlayWidget);

    /**
     * Adds a new anchor based on the given <tt>title</tt> for the passed <tt>overlayWidget</tt>.
     *
     * @param title         the title for the link
     * @param overlayWidget the sibling overlay widget
     */
    void addSiblingAmendableWidget(String title, final OverlayWidget overlayWidget);

    /**
     * Adds a separator between the siblings and the new children.
     * @param visible   <tt>true</tt> if the separator should be visible
     */
    void setSeparatorVisible(boolean visible);

    /**
     * Clear the existing child or sibling types.
     */
    void clearChildOverlayWidgets();

    /**
     * Highlights the n-th item.
     * @param n the index of the widget to highlight
     */
    void setHighlight(int n);

    /**
     * UI listener interface.
     */
    public static interface UIListener {
        /**
         * Callback when a link for a new element is clicked.
         * @param newChild  the new {@link OverlayWidget}
         * @param sibling   if <tt>true</tt>, this should be added as a sibling rather than a child element
         */
        void onClick(OverlayWidget newChild, boolean sibling);
    }
}
