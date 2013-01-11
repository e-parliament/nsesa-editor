package org.nsesa.editor.gwt.dialog.client.ui.rte;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Defines a plugin attached to a rich text editor
 * User: groza
 * Date: 9/01/13
 * Time: 11:06
 */
public interface RichTextEditorPlugin {
    /**
     * Return the plugin name
     * @return The plugin name
     */
    abstract String getName();

    /**
     * Run before initialization
     * @param editor
     */
    abstract void beforeInit(JavaScriptObject editor);

    /**
     * Run during initialization
     * @param editor
     */
    abstract void init(JavaScriptObject editor);

    /**
     * Run after initialization
     * @param editor
     */
    abstract void afterInit(JavaScriptObject editor);

    /**
     * Used to export plugin in javascript world if the case
     */
    abstract void export();
}
