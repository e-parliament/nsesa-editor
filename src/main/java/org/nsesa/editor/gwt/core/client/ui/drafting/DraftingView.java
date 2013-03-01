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
package org.nsesa.editor.gwt.core.client.ui.drafting;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * The drafting view widget is responsible to display the allowed and mandatory children
 * for a specific <code>OverlayWidget</code>
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 16/01/13 13:37
 */
@ImplementedBy(DraftingViewImpl.class)
public interface DraftingView extends IsWidget {
    /**
     * Clean up the widget content
     */
    void clearAll();

    /**
     * Add an allowed child in the view
     * @param widget The widget that will be added in the allowed children area of the view.
     */
    void addAllowedChild(IsWidget widget);

    /**
     * Set the view title
     * @param title
     */
    void setDraftTitle(String title);

    /**
     * Add a mandatory child in the view
     * @param widget The widget that will be added in the  mandatory children area of the view.
     */
    void addMandatoryChild(IsWidget widget);

}
