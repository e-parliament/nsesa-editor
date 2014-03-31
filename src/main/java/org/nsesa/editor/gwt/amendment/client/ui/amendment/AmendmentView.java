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
package org.nsesa.editor.gwt.amendment.client.ui.amendment;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Main amendment view as it is injected into the document.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AmendmentViewImpl.class)
public interface AmendmentView extends IsWidget, HasClickHandlers, HasDoubleClickHandlers {

    public static final String DEFAULT = "default", CONSOLIDATION = "consolidation", SINGLE = "single";

    /**
     * Set the id on the view.
     *
     * @param id the id to set.
     */
    void setId(String id);

    /**
     * Set the bundled amendment containers (if any).
     *
     * @param amendmentContainerIDs the amendment container IDs that are bundled
     */
    void setBundle(String[] amendmentContainerIDs);

    /**
     * Set the title
     *
     * @param title the title to set
     */
    void setTitle(String title);

    /**
     * Sets the body to display as the main amendment content (usually transformed XML).
     *
     * @param xmlContent the xml content to set as the body
     */
    void setBody(String xmlContent);

    /**
     * Sets an introductory part on the amendment (can be used for declarative amendments).
     *
     * @param introduction the introductory part
     */
    void setIntroduction(String introduction);

    /**
     * Sets the status of the amendment on this view.
     *
     * @param status the status to set
     */
    void setStatus(String status);

    /**
     * Sets the groups this amendment is shared with.
     * @param groupNames the names of the groups
     */
    void setGroups(String... groupNames);

    /**
     * Get the body of the amendment as an element.
     *
     * @return the body
     */
    Element getBody();

    /**
     * Return a reference to the component for more actions, such as table, delete, ...
     *
     * @return the component that can be clicked for more actions
     */
    HasClickHandlers getMoreActionsButton();

    /**
     * Return a reference to the component to edit the current amendment.
     *
     * @return the component that can be clicked to edit the amendment
     */
    HasClickHandlers getEditButton();

    /**
     * Returns the reference to the component to delete this amendment controller.
     *
     * @return the component that can be clicked to delete this controller
     */
    HasClickHandlers getDeleteButton();

    void attach();

    void detach();

    String getPathToOriginalContent();

    String getPathToAmendmentContent();

    void setDescription(String description);
}
