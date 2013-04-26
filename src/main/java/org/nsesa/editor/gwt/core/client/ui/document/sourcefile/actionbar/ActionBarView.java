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
package org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * View for the {@link ActionBarController}.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ActionBarViewImpl.class)
public interface ActionBarView extends IsWidget {

    void addWidget(IsWidget isWidget);

    ComplexPanel getActionPanel();

    /**
     * Get a reference to the modification link.
     * @return the modification link
     */
    FocusWidget getModifyHandler();

    /**
     * Get a reference to the delete link.
     * @return the delete link
     */
    FocusWidget getDeleteHandler();

    /**
     * Get a reference to the bundle link.
     * @return the bundle link
     */
    FocusWidget getBundleHandler();

    /**
     * Get a reference to the move link.
     * @return the move link
     */
    FocusWidget getMoveHandler();

    /**
     * Get a reference to the child link to create new elements.
     * @return the child link
     */
    FocusWidget getChildHandler();

    /**
     * Get a reference to the translate link.
     * @return the translate link
     */
    FocusWidget getTranslateHandler();

    /**
     * Set the location of the overlay widget in the tree.
     * @param location the location
     */
    void setLocation(String location);

    /**
     * Toggle visibility.
     * @param visible <tt>true</tt> if the element should be visible.
     */
    void setVisible(boolean visible);

    /**
     * 'Physically' attach the component to the DOM tree.
     */
    void attach();

    /**
     * General method to set a CSS class name on this component.
     * @param styleName the css class name to set.
     */
    void setStyleName(String styleName);
}
