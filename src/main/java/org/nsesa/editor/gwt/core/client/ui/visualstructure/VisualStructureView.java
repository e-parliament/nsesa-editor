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
package org.nsesa.editor.gwt.core.client.ui.visualstructure;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Occurrence;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

import java.util.HashMap;

/**
 * The drafting view widget is responsible to display the allowed and mandatory children
 * for a specific <code>OverlayWidget</code>
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 16/01/13 13:37
 */
@ImplementedBy(VisualStructureViewImpl.class)
public interface VisualStructureView extends IsWidget {

    /**
     * A callback interfaced for code<>DraftingView</code> interface. The typical implementation
     * will use the event bus in order to raise different types of events.
     */
    public static interface VisualStructureCallback {
        /**
         * Execute this operation when child is selected in
         * @param child The child selected
         */
        void onChildrenSelect(OverlayWidget child);
    }

    /**
     * Clean up the widget content
     */
    void clearAll();

    /**
     * Set the view title
     * @param title
     */
    void setVisualStructureTitle(String title);

    /**
     * Refresh the allowed children into the view
     * @param allowedChildren A Map containing the allowed widget children with their occurrence
     * @param callback gets called when the user select a child from the interface
     */
    void refreshAllowedChildren(HashMap<OverlayWidget,Occurrence> allowedChildren, VisualStructureCallback callback);

}
