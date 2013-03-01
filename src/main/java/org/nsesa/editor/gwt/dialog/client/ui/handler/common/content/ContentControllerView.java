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
package org.nsesa.editor.gwt.dialog.client.ui.handler.common.content;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor;

/**
 * View for the {@link ContentControllerView}.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ContentControllerViewImpl.class)
public interface ContentControllerView extends IsWidget {

    /**
     * Add the CSS class to be used as the 'body'. This allows us to mimic the DOM layout for the content
     * that is displayed in the RTE.
     *
     * @param className the CSS class name to use for the body
     */
    void addBodyClass(String className);

    /**
     * Reset the body class of the RTE.
     */
    void resetBodyClass();

    /**
     * Set the original HTML content.
     * @param content the original content.
     */
    void setOriginalText(String content);

    /**
     * Get the original HTML content.
     * @return the original content
     */
    String getOriginalText();

    /**
     * Get the RTE associated with this view.
     * @return the RTE
     */
    RichTextEditor getRichTextEditor();
}
