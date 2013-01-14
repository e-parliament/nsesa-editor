package org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor;

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
            .add(new ToolbarLine().add(Option.Find).add(Option.Replace).add(Option._).add(Option.SelectAll).add(Option._).add(Option.Source));

    public static enum Option {
        Subscript, Superscript, Undo, Redo, SpecialChar, Find, Replace, SelectAll,Source, _;
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
            for(Option opt: options){
                final String optToString = opt.equals(Option._) ? "-" : opt.toString();
                array = addToArray(array,optToString);
            }
            return array;
        }
    }

    private List<ToolbarLine> lines;

    public CKEditorToolbar(){
        lines = new ArrayList<ToolbarLine>();
    }

    public CKEditorToolbar add(ToolbarLine l){
        lines.add(l);
        return this;
    }


    public JavaScriptObject getRepresentation(){
        JavaScriptObject array = JavaScriptObject.createArray();
        for(ToolbarLine line:lines){
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
