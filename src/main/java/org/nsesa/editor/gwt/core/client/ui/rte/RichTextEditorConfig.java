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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig;

/**
 * Defines the configuration required for a rich text editor in order to run it
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 14/01/13 14:42
 *
 */
@ImplementedBy(CKEditorConfig.class)
public interface RichTextEditorConfig {
    /**
     * Whether or not the editor is readonly.
     *
     * @param readOnly When true no changes are allowed over the content data of the editor
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
     * Add a class to document body of the editor.
     *
     * @param bodyClass The css class name as String
     */
    void addBodyClass(String bodyClass);

    /**
     * Reset the document body class of the editor to the original value.
     */
    void resetBodyClass();

    /**
     * Return a editor configuration representation as JavaScriptObject
     *
     * @return The editor configuration as JavaScriptObject
     */
    JavaScriptObject getConfiguration();

    /**
     * Return the drafting css class name
     *
     * @return The drafting class name as String
     */
    String getDraftingClassName();

    /**
     * Set up the drafting css class name in the draft tool widget
     *
     * @return
     */
    void setDraftingClassName(String draftingClassName);

    /**
     * Set the ns attribute in the body tag
     * @param namespaceURI The namespace URI to be set
     */
    void setBodyNamespaceURI(String namespaceURI);
}
