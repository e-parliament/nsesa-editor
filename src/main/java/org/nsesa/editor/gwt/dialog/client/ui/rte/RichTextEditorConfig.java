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
     *
     * @param readOnly
     */
    void setReadOnly(boolean readOnly);

    /**
     * Set the height of the editor
     */
    void setHeight(int height);

    /**
     * Whether or not the editor is readonly
     */
    boolean isReadOnly();

    /**
     * Add a class to body
     *
     * @param bodyClass
     */
    void addBodyClass(String bodyClass);

    /**
     * Resets the body class.
     */
    void resetBodyClass();

    /**
     * Return a representation as JavaScriptObject
     *
     * @return
     */
    JavaScriptObject getConfiguration();

    /**
     * Return the drafting classname
     * @return
     */
    String getDraftingClassName();
    /**
     * Set up the drafting class name
     * @return
     */
    void setDraftingClassName(String draftingClassName);

}
