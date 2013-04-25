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
package org.nsesa.editor.gwt.amendment.client.ui.document.amendments;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;

import java.util.List;
import java.util.Map;

/**
 * <code>AmendmentsPanelView</code> view is the main part of the second tabbed panel of the application. It provides a
 * way to display the existing amendments.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 24/06/12 21:44
 *
 */
@ImplementedBy(AmendmentsPanelViewImpl.class)
public interface AmendmentsPanelView extends IsWidget {
    /**
     * Display the given amendments controllers into a view
     * @param amendments The amendments that will be displayed
     */
    public void setAmendmentControllers(Map<String, AmendmentController> amendments);

    /**
     * Clean up the old content of the view and display the new amendment controllers
     * @param amendments
     */
    public void refreshAmendmentControllers(Map<String, AmendmentController> amendments);

    /**
     * Set the style of the view
     * @param styleName
     */
    public void setStyleName(String styleName);

    /**
     * Returns the list of selected amendment controllers that are visible in the view. There might be the case
     * when some amendment controllers are not visible because of the pagination support.
     * @return
     */
    public List<String> getSelectedVisibleAmendmentContainerIds();

    /**
     * Select the amendment controllers into the view
     * @param ids A list of id-s to identify the amendment controllers
     */
    public void selectAmendmentControllers(List<String> ids);

}
