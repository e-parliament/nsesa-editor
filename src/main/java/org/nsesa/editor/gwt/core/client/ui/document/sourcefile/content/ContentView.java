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
package org.nsesa.editor.gwt.core.client.ui.document.sourcefile.content;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.inject.ImplementedBy;

/**
 * View for the {@link ContentView}.
 * Date: 24/06/12 21:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ContentViewImpl.class)
public interface ContentView extends IsWidget {

    /**
     * Set the content of the document as an HTML string.
     * @param documentContent the document content
     */
    void setContent(String documentContent);

    /**
     * Get the underlying element to which the content will be attached.
     * @return the main content element
     */
    Element getContentElement();

    /**
     * Get a reference to the scroll panel.
     * @return the scroll panel
     */
    ScrollPanel getScrollPanel();

    /**
     * General setting of a css class name on this view.
     * @param style the css class name to set
     */
    void setStyleName(String style);
}
