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

import java.util.ArrayList;
import java.util.List;

/**
 * A composite plugin used to run all the included plugins.
 * User: groza
 * Date: 10/01/13
 * Time: 12:05
 */
public class RichTextCompositePlugin implements RichTextEditorPlugin {
    private String name;
    protected List<RichTextEditorPlugin> plugins;

    public RichTextCompositePlugin() {
        this("RichTextCompositePlugin", new ArrayList<RichTextEditorPlugin>());
    }

    public RichTextCompositePlugin(String name, List<RichTextEditorPlugin> plugins) {
        this.name = name;
        this.plugins = plugins;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void beforeInit(JavaScriptObject editor) {
        for (RichTextEditorPlugin plugin : plugins) {
            plugin.beforeInit(editor);
        }
    }

    @Override
    public void init(JavaScriptObject editor) {
        for (RichTextEditorPlugin plugin : plugins) {
            plugin.init(editor);
        }
    }

    @Override
    public void export(RichTextEditorConfig config) {
        for (RichTextEditorPlugin plugin : plugins) {
            plugin.export(config);
        }
    }
}
