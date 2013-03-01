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
package org.nsesa.editor.gwt.dialog.client.ui.handler;

import com.google.gwt.user.client.ui.IsWidget;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext;

/**
 * Main interface for a UI handler for dealing with the creation or modification of an amendment. This UI
 * handler can be specific to a certain {@link org.nsesa.editor.gwt.core.shared.AmendmentAction} and/or
 * {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget} that is being passed around in
 * the {@link DialogContext}.
 * <p/>
 * Date: 10/07/12 20:34
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendmentUIHandler {

    /**
     * Set the dialog context to get access to all environment variables such as the amendment dto and controller,
     * the document controller, the place to insert a new element, etc ..
     * @param dialogContext the dialog context
     */
    void setContext(DialogContext dialogContext);

    /**
     * Actually handle the displaying of the context - being called after the context has been set.
     */
    void handle();

    /**
     * Get the view of the UI handler.
     * @return the view
     */
    IsWidget getView();
}
