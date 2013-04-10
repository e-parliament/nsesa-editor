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
package org.nsesa.editor.gwt.dialog.client.ui.handler.create;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor;

/**
 * View for the {@link AmendmentDialogCreateController}.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AmendmentDialogCreateViewImpl.class)
public interface AmendmentDialogCreateView extends IsWidget {

    /**
     * Add the CSS body class for the RTE to mimic the DOM structure of the document.
     * @param className the CSS class to add
     */
    void addBodyClass(String className);

    /**
     * Resets the CSS body class of the RTE editor.
     */
    void resetBodyClass();

    /**
     * Returns the unvalidated HTML content from the view.
     * @return the amendment content
     */
    String getAmendmentContent();

    /**
     * Set the HTML content of the amendment.
     * @param content the content to set.
     */
    void setAmendmentContent(String content);

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

    /**
     * Set the title on the view.
     * @param title the title to set
     */
    void setTitle(String title);

    /**
     * Get a reference to the RTE that is used.
     * @return the RTE
     */
    RichTextEditor getRichTextEditor();

    /**
     * Add a child view to the main view with the given <tt>title</tt>.
     * @param view  the view to add
     * @param title the title to use for the view
     */
    void addView(IsWidget view, String title);
}
