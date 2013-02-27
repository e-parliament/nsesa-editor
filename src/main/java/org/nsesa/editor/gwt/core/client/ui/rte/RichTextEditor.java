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
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * Provides functionality for WYSIWYG HTML (Rich Text) Editor widget. This editor works
 * against a given <code>OverlayWidget</code> widget by offering the possibility to change its text,
 * modify its structure or changing the attributes of its children.
 * Date: 13/07/12 19:39
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 14/01/13 11:24
 */
public interface RichTextEditor extends IsWidget {
    /**
     *  Add CSS class to the editor document body.
     * @param className The css class name as String
     */
    void addBodyClass(String className);

    /**
     *  Remove any CSS class from RichText Editor document body that was added before.
     */
    void resetBodyClass();

    /**
     * Set the content data of the Rich Text Editor
     * @param content The content that will be displayed in the editor
     */
    void setHTML(String content);

    /**
     * Returns the content data of the editor.
     * @return the content data as String
     */
    String getHTML();

    /**
     *
     * @param overlayWidget
     */
    void setAmendableWidget(OverlayWidget overlayWidget);

    /**
     * Method that must be called after you instantiate the editor, preferably when you attach the editor to
     * DOM.
     */
    void init();

    /**
     * Method that must be called before you instantiate the editor, preferably when you de attach the editor from
     * DOM.
     */
    void destroy();

    /**
     * Add a drafting tool widget to the editor. The drafting tool is responsible to change the structure of the original
     * overlayWidget.
     *
     * @param widget The drafting tool as widget
     */
    void setDraftingTool(IsWidget widget);

    /**
     * Add a drafting attributes widget to the editor. The drafting attributes tool gives the possibility to change the
     * attribute values of the original overlayWidget children.
     *
     * @param widget The drafting attributes as
     */
    void setDraftingAttributes(IsWidget widget);

    /**
     * Show/hide the drafting tool widget in the editor.
     *
     * @param toggled When true the drafting tool widget is shown
     */
    void toggleDraftingTool(boolean toggled);

    /**
     * Executes a given command of the editor especially when the editor implementation is based on existing Javascript
     * editor implementation whereas there are a lot of commands defined.
     *
     * @param command The command name
     * @param delay   The delay in milliseconds
     */
    void executeCommand(String command, int delay);

    /**
     * Show/hide the drafting attributes widget in the editor
     *
     * @param toggled When true the drafting attributes widget is shown
     */
    void toggleDraftingAttributes(boolean toggled);

    /**
     * Resize the editor
     * @param width The width
     * @param height The height
     */
    void resize(String width, String height);
}
