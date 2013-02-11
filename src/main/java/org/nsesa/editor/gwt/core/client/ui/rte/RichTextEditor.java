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
package org.nsesa.editor.gwt.core.client.ui.rte;

import com.google.gwt.user.client.ui.IsWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * Date: 13/07/12 19:39
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface RichTextEditor extends IsWidget {

    void addBodyClass(String className);

    void resetBodyClass();

    void setHTML(String content);

    String getHTML();

    void setAmendableWidget(AmendableWidget amendableWidget);

    void init();

    void destroy();

    /**
     * Add a drafting tool widget to the editor
     *
     * @param widget
     */
    void setDraftingTool(IsWidget widget);

    /**
     * Toggle the drafting tool in the editor
     *
     * @param toggled
     */
    void toggleDraftingTool(boolean toggled);

    /**
     * Executes a certain command of the editor
     *
     * @param command The command name
     * @param delay   The delay in milliseconds
     */
    void executeCommand(String command, int delay);

}
