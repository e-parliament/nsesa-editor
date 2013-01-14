package org.nsesa.editor.gwt.dialog.client.ui.rte;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Defines the configuration required for an editor in order to run it
 * User: groza
 * Date: 14/01/13
 * Time: 14:42
 */
public interface RichTextEditorConfig {
    /**
     * Whether or not the editor is readonly
     * @param readOnly
     */
    abstract void setReadOnly(boolean readOnly);

    /**
     * Set the height of the editor
     */
    abstract void setHeight(int height);

    /**
     * Whether or not the editor is readonly
     */
    abstract boolean isReadOnly();

    /**
     * Add a class to body
     * @param bodyClass
     */
    abstract void addBodyClass(String bodyClass);
    /**
     * Return a representation as JavaScriptObject
     * @return
     */
    abstract JavaScriptObject getConfiguration();
}
