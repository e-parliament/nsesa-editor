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
package org.nsesa.editor.gwt.editor.client.ui.header;

import com.google.gwt.i18n.shared.GwtLocale;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * View for the {@link HeaderController}.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(HeaderViewImpl.class)
public interface HeaderView extends IsWidget {

    /**
     * Set the CSS class name on the {@link HeaderView}.
     * @param style the CSS class name
     */
    void setStyleName(String style);

    /**
     * Set the name to display as the logged in user in the view.
     * @param personName the person name to display
     */
    void setLoggedInPersonName(String personName);

    /**
     * Set the (security) roles of the logged in user in the view.
     * @param roles the roles of the logged in person
     */
    void setLoggedInPersonRoles(String[] roles);

    /**
     * Set the available UI languages in the view.
     * @param locales the locales to set
     */
    void setAvailableLanguages(List<String> locales);
}
