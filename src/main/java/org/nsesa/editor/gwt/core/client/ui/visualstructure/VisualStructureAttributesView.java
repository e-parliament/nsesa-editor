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
package org.nsesa.editor.gwt.core.client.ui.visualstructure;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import java.util.Map;

/**
 * The drafting attributes view widget is responsible to display the attributes
 * for a specific <code>OverlayWidget</code>
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 16/01/13 13:37
 */
@ImplementedBy(VisualStructureAttributesViewImpl.class)
public interface VisualStructureAttributesView extends IsWidget {
    /**
     * Clean up the widget
     */
    void clearAll();

    /**
     * Set up the attributes in the widget
     * @param attributes A map of attributes where the key is the attribute name and the value is the attribute value
     */
    void setAttributes(Map<String, String> attributes);

    /**
     * Returns the map of attributes displayed in the view
     * @return A map of attributes
     */
    public Map<String, String> getAttributes();

}
