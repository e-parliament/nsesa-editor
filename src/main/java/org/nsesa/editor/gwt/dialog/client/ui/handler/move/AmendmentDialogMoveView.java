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
package org.nsesa.editor.gwt.dialog.client.ui.handler.move;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.content.ContentView;

/**
 * View for the {@link AmendmentDialogMoveController}.
 * <p/>
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AmendmentDialogMoveViewImpl.class)
public interface AmendmentDialogMoveView extends IsWidget {

    /**
     * Get a reference to the save button.
     *
     * @return the save button
     */
    HasClickHandlers getSaveButton();

    /**
     * Get a reference to the cancel link.
     *
     * @return the cancel link
     */
    HasClickHandlers getCancelLink();

    ContentView getContentView();
}
