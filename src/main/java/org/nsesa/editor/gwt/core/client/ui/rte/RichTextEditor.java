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
package org.nsesa.editor.gwt.core.client.ui.rte;

import com.google.gwt.user.client.ui.IsWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * Provides functionality for WYSIWYG HTML (Rich Text) Editor widget. This editor works
 * against a given <code>OverlayWidget</code> widget by offering the possibility to change its text,
 * modify its structure or changing the attributes of its children.
 * Date: 13/07/12 19:39
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
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
     * Set the overlay widget that will be processed by the editor
     * @param overlayWidget
     */
    void setOverlayWidget(OverlayWidget overlayWidget);

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
     * Set or unset the focus on this RTE.
     * @param focus the focus flag
     */
    void setFocus(boolean focus);

    /**
     * Add a visual structure tool widget to the editor. The visual structure tool is responsible to change the
     * structure of the original overlayWidget.
     *
     * @param widget The drafting tool as widget
     */
    void setVisualStructureWidget(IsWidget widget);

    /**
     * Add a visual structure attributes widget to the editor. The visual structure attributes  attributes tool gives
     * the possibility to change the attribute values of the the selected overlayWidget.
     *
     * @param widget The drafting attributes as
     */
    void setVisualStructureAttributesWidget(IsWidget widget);

    /**
     * Show/hide the visual structure tool widget in the editor.
     *
     * @param toggled When true the visual structure tool widget is shown
     */
    void toggleVisualStructure(boolean toggled);

    /**
     * Executes a given command of the editor especially when the editor implementation is based on existing Javascript
     * editor implementation whereas there are a lot of commands defined.
     *
     * @param command The command name
     * @param delay   The delay in milliseconds
     */
    void executeCommand(String command, int delay);

    /**
     * Show/hide the visual structure attributes widget in the editor
     *
     * @param toggled When true the visual structure attributes widget is shown
     */
    void toggleVisualStructureAttributes(boolean toggled);

    /**
     * Resize the editor
     * @param width The width
     * @param height The height
     */
    void resize(String width, String height);

    /**
     * Expose stylename setter for easier styling in UIBinder.
     * @param styleName the stylename to set
     */
    void setStyleName(String styleName);


    /**
     * Expose visibility method for easier styling in UIBinder.
     * @param visible true to set the element visible
     */
    void setVisible(boolean visible);

    /**
     * Return the caret position from the editor  area
     * @return {@link CaretPosition}
     */
    CaretPosition getCaretPosition();

    /**
     * A holder class for RichTextEditor caret position
     */
    public static class CaretPosition {

        private int left;
        private int top;

        //default constructor
        public CaretPosition() {
        }

        public CaretPosition(int left, int top) {
            this.left = left;
            this.top = top;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getTop() {
            return top;
        }

        public int getLeft() {
            return left;
        }
    }
}
