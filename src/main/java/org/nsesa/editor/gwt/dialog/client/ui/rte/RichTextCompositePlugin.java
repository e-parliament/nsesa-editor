package org.nsesa.editor.gwt.dialog.client.ui.rte;

import com.google.gwt.core.client.JavaScriptObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A composite plugin used to run all the included plugins.
 * User: groza
 * Date: 10/01/13
 * Time: 12:05
 *
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
        for(RichTextEditorPlugin plugin : plugins) {
            plugin.beforeInit(editor);
        }
    }

    @Override
    public void init(JavaScriptObject editor) {
        for(RichTextEditorPlugin plugin : plugins) {
            plugin.init(editor);
        }
    }

    @Override
    public void export(RichTextEditorConfig config) {
        for(RichTextEditorPlugin plugin : plugins) {
            plugin.export(config);
        }
    }
}
