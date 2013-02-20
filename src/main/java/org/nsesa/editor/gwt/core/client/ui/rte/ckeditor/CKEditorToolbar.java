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
 * A toolbar configuration for CK Editor Config
 * User: groza
 * Date: 14/01/13
 * Time: 13:27
 */
public class CKEditorToolbar {

    public static CKEditorToolbar EMPTY_TOOLBAR = new CKEditorToolbar();

    public static CKEditorToolbar DEFAULT_TOOLBAR = new CKEditorToolbar()
            .add(new ToolbarLine().add(Option.Subscript).add(Option.Superscript).add(Option._))
            .add(new ToolbarLine().add(Option.Undo).add(Option.Redo).add(Option._).add(Option.SpecialChar))
            .add(new ToolbarLine().add(Option.Find).add(Option.Replace).add(Option._).add(Option.SelectAll).add(Option._).add(Option.Source))
            .add(new ToolbarLine().add(Option.NsesaRemoveFormat).add(Option.NsesaToggle).add(Option.NsesaToggleAttributes).add(Option._));

    public static enum Option {
        NsesaToggle, NsesaRemoveFormat, NsesaToggleAttributes, Subscript, Superscript, Undo, Redo, SpecialChar, Find, Replace, SelectAll, Source, _;
    }

    public static class ToolbarLine {
        private List<Option> options;
        private static String LINE_SEPARATOR = "-";

        public ToolbarLine() {
            this.options = new ArrayList<Option>();
        }

        public ToolbarLine add(Option opt) {
            options.add(opt);
            return this;
        }

        public JavaScriptObject getRepresentation() {
            JavaScriptObject array = JavaScriptObject.createArray();
            for (Option opt : options) {
                final String optToString = opt.equals(Option._) ? "-" : opt.toString();
                array = addToArray(array, optToString);
            }
            return array;
        }
    }

    private List<ToolbarLine> lines;

    public CKEditorToolbar() {
        lines = new ArrayList<ToolbarLine>();
    }

    public CKEditorToolbar add(ToolbarLine l) {
        lines.add(l);
        return this;
    }


    public JavaScriptObject getRepresentation() {
        JavaScriptObject array = JavaScriptObject.createArray();
        for (ToolbarLine line : lines) {
            JavaScriptObject representation = line.getRepresentation();
            array = addToArray(array, representation);
        }
        return array;
    }

    private static native JavaScriptObject addToArray(JavaScriptObject base, JavaScriptObject option) /*-{
        base[base.length] = option;
        return base;
    }-*/;

    private static native JavaScriptObject addToArray(JavaScriptObject base, String option) /*-{
        base[base.length] = option;
        return base;
    }-*/;

}
