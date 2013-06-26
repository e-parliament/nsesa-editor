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

import java.util.ArrayList;
import java.util.List;

/**
 * A type of <code>RichTextEditorPlugin</code> that wrap a list of plugins, hiding the wrapped plugins
 * methods.
 *
 * <p>
 * The composite is useful for creating a single plugin out of an aggregate of
 * multiple other plugins.
 * </p>
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 10/01/13 12:05
 */
public class RichTextCompositePlugin implements RichTextEditorPlugin {
    protected List<RichTextEditorPlugin> plugins;

    /**
     * Default constructor
     */
    public RichTextCompositePlugin() {
        this(new ArrayList<RichTextEditorPlugin>());
    }

    public RichTextCompositePlugin(List<RichTextEditorPlugin> plugins) {
        this.plugins = plugins;
    }


    /**
     * Call <code>beforeInit<code/> method for all wrapped plugins
     * @param editor The Rich Text editor as JavaScriptObject
     */
    @Override
    public void beforeInit(JavaScriptObject editor) {
        for (RichTextEditorPlugin plugin : plugins) {
            plugin.beforeInit(editor);
        }
    }

    /**
     * Call <code>init<code/> method for all wrapped plugins
     * @param editor The Rich Text editor as JavaScriptObject
     */
    @Override
    public void init(JavaScriptObject editor) {
        for (RichTextEditorPlugin plugin : plugins) {
            plugin.init(editor);
        }
    }

    @Override
    public void afterInit(JavaScriptObject editor) {
        for (RichTextEditorPlugin plugin : plugins) {
            plugin.afterInit(editor);
        }
    }

}
