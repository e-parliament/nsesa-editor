package org.nsesa.editor.gwt.core.client.ui.rte;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.ImplementedBy;

/**
 * Defines a plugin attached to a rich text editor
 * User: groza
 * Date: 9/01/13
 * Time: 11:06
 */
@ImplementedBy(RichTextCompositePlugin.class)
public interface RichTextEditorPlugin {
    /**
     * Return the plugin name
     *
     * @return The plugin name
     */
    abstract String getName();

    /**
     * Run before initialization
     *
     * @param editor
     */
    abstract void beforeInit(JavaScriptObject editor);

    /**
     * Run during initialization
     *
     * @param editor
     */
    abstract void init(JavaScriptObject editor);

    /**
     * Used to export plugin in javascript world if the case
     */
    abstract void export(RichTextEditorConfig config);

}
