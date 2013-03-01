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
package org.nsesa.editor.gwt.dialog.client.ui.handler.modify;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor;

/**
 * View for the {@link AmendmentDialogModifyController}.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AmendmentDialogModifyViewImpl.class)
public interface AmendmentDialogModifyView extends IsWidget {

    /**
     * Sets the body class on the RTE to mimic the DOM structure of the document.
     * @param className the CSS class name
     */
    void addBodyClass(String className);

    /**
     * Resets the body class to the original one by remove any additional css body class.
     */
    void resetBodyClass();

    /**
     * Set the title on the main panel.
     * @param title the title to set
     */
    void setTitle(String title);

    /**
     * Set the amendment content on the RTE.
     * @param amendmentContent the amendment content
     */
    void setAmendmentContent(String amendmentContent);

    /**
     * Get the unvalidated HTML amendment content.
     * @return the amendment content
     */
    String getAmendmentContent();

    /**
     * Add a new child widget to the view under the given <tt>title</tt>.
     * @param view  the view to add
     * @param title the title
     */
    void addView(IsWidget view, String title);

    /**
     * Get a reference to the RTE.
     * @return the RTE
     */
    RichTextEditor getRichTextEditor();

    /**
     * Get a reference to the save button.
     * @return the save button
     */
    HasClickHandlers getSaveButton();

    /**
     * Get a reference to the cancel link.
     * @return the cancel link
     */
    HasClickHandlers getCancelLink();
}
