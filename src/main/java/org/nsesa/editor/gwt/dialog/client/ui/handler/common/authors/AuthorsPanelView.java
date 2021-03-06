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
package org.nsesa.editor.gwt.dialog.client.ui.handler.common.authors;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.ImplementedBy;

/**
 * View for the {@link AuthorsPanelController}.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AuthorsPanelViewImpl.class)
public interface AuthorsPanelView extends IsWidget {
    /**
     * Get a reference to the (autocomplete) suggestbox with the author.
     *
     * @return the suggest box
     */
    SuggestBox getSuggestBox();

    /**
     * Get a reference to the selected authors panel.
     *
     * @return the selected authors panel
     */
    VerticalPanel getAuthorsPanel();

    AbsolutePanel getBoundaryPanel();
}
