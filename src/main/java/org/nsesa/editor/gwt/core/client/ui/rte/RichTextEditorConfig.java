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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig;

/**
 * Defines the configuration required for an editor in order to run it
 * User: groza
 * Date: 14/01/13
 * Time: 14:42
 */
@ImplementedBy(CKEditorConfig.class)
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
     *
     * @return
     */
    String getDraftingClassName();

    /**
     * Set up the drafting class name
     *
     * @return
     */
    void setDraftingClassName(String draftingClassName);

}
