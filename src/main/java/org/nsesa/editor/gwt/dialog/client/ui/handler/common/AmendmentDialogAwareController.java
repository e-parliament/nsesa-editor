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
package org.nsesa.editor.gwt.dialog.client.ui.handler.common;

import com.google.gwt.user.client.ui.IsWidget;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext;

/**
 * An interface for all components that want to register under a parent
 * {@link org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandler}, usually in a tab.
 * Date: 22/11/12 11:43
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendmentDialogAwareController {

    /**
     * The view for the tab.
     * @return the view
     */
    IsWidget getView();

    /**
     * The title to display in the tab.
     * @return the title
     */
    String getTitle();

    /**
     * Perform a validation on the data in the tab.
     * @return <tt>true</tt> if the validation was successful
     */
    boolean validate();

    /**
     * Set the dialog context with runtime information.
     * @param dialogContext the dialog context
     */
    void setContext(DialogContext dialogContext);
}
