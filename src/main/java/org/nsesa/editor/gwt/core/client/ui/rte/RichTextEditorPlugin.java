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

/**
 * Provides possibility to customize the rich text editor functionality without bloating the editor with unneeded
 * features.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 09/01/13 11:06
 */
@ImplementedBy(RichTextCompositePlugin.class)
public interface RichTextEditorPlugin {
    /**
     * Return the plugin name
     *
     * @return The plugin name as String
     */
    abstract String getName();

    /**
     * Run before editor initialization
     *
     * @param editor The Rich Text editor as JavaScriptObject
     */
    abstract void beforeInit(JavaScriptObject editor);

    /**
     * Run during editor initialization
     *
     * @param editor The Rich Text editor as JavaScriptObject
     */
    abstract void init(JavaScriptObject editor);

    /**
     * Used to export the plugin in the javascript world if the case
     *
     * @param config The Rich Text editor configuration as JavaScriptObject
     */
    abstract void export(RichTextEditorConfig config);

}
