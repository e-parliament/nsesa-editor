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
package org.nsesa.editor.gwt.core.client.ui.rte.ckeditor;

import com.google.gwt.core.client.JavaScriptObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Allow to configure the toolbar for <code>CKEditor</code> editor implementation. Basically the CK editor toolbar
 * is a set of buttons that can be visually grouped. For each button you have to register in CK editor a handler
 * that is called as soon as you press a button.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 14/01/13 13:27
 *
 * @see <a href="http://docs.cksource.com/CKEditor_3.x/Howto/Toolbar_Customization">CK Editor Toolbar</a> for details
 *
 */
public class CKEditorToolbar {

    /**
     * Empty CKEditor toolbar used when the CK editor is initialized in readonly mode
     */
    public static CKEditorToolbar EMPTY_TOOLBAR = new CKEditorToolbar();

    /**
     * Default CKEditor toolbar with the common operations that could be performed:
     * subscript, superscript, undo, redo, special char insertion,
     * find, replace, select all, source,
     * remove format, show/hide draft tool, show/hide draft attributes
     */
    public static CKEditorToolbar DEFAULT_TOOLBAR = new CKEditorToolbar()
            .add(new ToolbarLine().add(Option.Subscript).add(Option.Superscript).add(Option._))
            .add(new ToolbarLine().add(Option.Undo).add(Option.Redo).add(Option._).add(Option.SpecialChar))
            .add(new ToolbarLine().add(Option.Find).add(Option.Replace).add(Option._).add(Option.SelectAll)
                    .add(Option._).add(Option.Source))
            .add(new ToolbarLine().add(Option.NsesaRemoveFormat).add(Option.NsesaToggle)
                    .add(Option.NsesaToggleAttributes).add(Option._));

    /**
     * Enum to define the common button elements that might be added into a toolbar
     */
    public static enum Option {
        NsesaToggle, NsesaRemoveFormat, NsesaToggleAttributes, Subscript, Superscript,
        Undo, Redo, SpecialChar, Find, Replace, SelectAll, Source, _;
    }

    /**
     *
     */
    private List<ToolbarLine> lines;

    /**
     * Default constructor
     */
    public CKEditorToolbar() {
        lines = new ArrayList<ToolbarLine>();
    }

    /**
     * Add a toolbar line into a toolbar
     * @param l the toolbar line
     * @return The toolbar instance
     */
    public CKEditorToolbar add(ToolbarLine l) {
        lines.add(l);
        return this;
    }

    /**
     * Creates a Javascript toolbar representation based on the list of the available toolbar lines
     * @return an array as JavaScriptObject
     */
    public JavaScriptObject getRepresentation() {
        JavaScriptObject array = JavaScriptObject.createArray();
        for (ToolbarLine line : lines) {
            JavaScriptObject representation = line.getRepresentation();
            array = addToArray(array, representation);
        }
        return array;
    }

    /**
     * Add a javascript object into an array
     * @param base  The array
     * @param option the javascript object that will be added in array
     * @return The modified array
     */
    private static native JavaScriptObject addToArray(JavaScriptObject base, JavaScriptObject option) /*-{
        base[base.length] = option;
        return base;
    }-*/;

    /**
     * Add a String into an array
     * @param base  The array
     * @param option the String that will be added in array
     * @return The modified array
     */
    private static native JavaScriptObject addToArray(JavaScriptObject base, String option) /*-{
        base[base.length] = option;
        return base;
    }-*/;

    /**
     * Utility class to group together a list of options that will be displayed in the CKEditor toolbar
     */
    public static class ToolbarLine {
        private List<Option> options;
        private static String LINE_SEPARATOR = "-";

        /**
         *  Default constructor which initialize the list of options
         */
        public ToolbarLine() {
            this.options = new ArrayList<Option>();
        }

        /**
         * Add an option in the list of available options
         * @param opt
         * @return ToolbarLine
         */
        public ToolbarLine add(Option opt) {
            options.add(opt);
            return this;
        }

        /**
         * Creates a JavascriptObject toolbar line representation based on the list of available options
         * @return an array as JavaScriptObject
         */
        public JavaScriptObject getRepresentation() {
            JavaScriptObject array = JavaScriptObject.createArray();
            for (Option opt : options) {
                final String optToString = opt.equals(Option._) ? "-" : opt.toString();
                array = addToArray(array, optToString);
            }
            return array;
        }
    }
}
