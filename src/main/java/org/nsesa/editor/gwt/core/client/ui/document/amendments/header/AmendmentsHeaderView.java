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
package org.nsesa.editor.gwt.core.client.ui.document.amendments.header;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Amendments header view interface
 * User: groza
 * Date: 26/11/12
 * Time: 11:50
 */
@ImplementedBy(AmendmentsHeaderViewImpl.class)
public interface AmendmentsHeaderView extends IsWidget {

    void addSelection(IsWidget selectionWidget);

    void addAction(IsWidget actionWidget);

    void setStyleName(String styleName);
}
