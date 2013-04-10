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
package org.nsesa.editor.gwt.dialog.client.ui.handler.delete;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * View for the {@link AmendmentDialogDeleteController}.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AmendmentDialogDeleteViewImpl.class)
public interface AmendmentDialogDeleteView extends IsWidget {

    /**
     * Set the title on the main widget.
     * @param title the title
     */
    void setTitle(String title);

    /**
     * Add a child view to the main view with the given <tt>title</tt>
     * @param view  the view to set
     * @param title the title
     */
    void addView(IsWidget view, String title);

    /**
     * Get a reference to the save button.
     * @return the save button
     */
    HasClickHandlers getSaveButton();

    /**
     * Get a reference to the cancel link
     * @return the cancel link
     */
    HasClickHandlers getCancelLink();
}
