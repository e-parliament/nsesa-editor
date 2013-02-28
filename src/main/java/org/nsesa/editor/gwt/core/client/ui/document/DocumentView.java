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
package org.nsesa.editor.gwt.core.client.ui.document;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Main view for the {@link DocumentController}.
 * Date: 24/06/12 21:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DocumentViewImpl.class)
public interface DocumentView extends IsWidget {

    /**
     * Set the total accepted width on this view.
     * @param width the width
     */
    void setWidth(String width);

    /**
     * Set the document height on this view.
     * @param height the height
     */
    void setDocumentHeight(int height);

    /**
     * Set a general css class name on this view.
     * @param style the css class name.
     */
    void setStyleName(String style);

    /**
     * Ask the view to switch to another tab <tt>index</tt>.
     * @param index the tab index
     */
    void switchToTab(int index);

    /**
     * Set the document title on this view
     * @param titleHTML the title, in HTML
     */
    void setDocumentTitle(String titleHTML);

    /**
     * Show or hide a loading indicator in the view.
     * @param show      <tt>true</tt> if the indicator should be shown
     * @param message   the message to display during the loading
     */
    void showLoadingIndicator(boolean show, String message);
}
