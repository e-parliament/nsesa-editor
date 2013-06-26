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
package org.nsesa.editor.gwt.amendment.client.ui.document.amendments.header;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * <code>AmendmentsHeaderView</code> view is used in the header of the second tabbed panel and determine what
 * type of selections and what type of actions can be done against existing amendments.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 26/11/12 11:50
 */
@ImplementedBy(AmendmentsHeaderViewImpl.class)
public interface AmendmentsHeaderView extends IsWidget {

    /**
     * Add a widget in the selection area of the view
     * @param selectionWidget
     */
    void addSelection(IsWidget selectionWidget);

    /**
     * Add a widget in the action area of the view
     * @param actionWidget
     */
    void addAction(IsWidget actionWidget);

    /**
     * Set the style name of the view
     * @param styleName The style name as String
     */
    void setStyleName(String styleName);
}
