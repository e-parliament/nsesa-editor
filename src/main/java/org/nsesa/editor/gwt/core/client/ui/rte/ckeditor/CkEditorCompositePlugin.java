package org.nsesa.editor.gwt.core.client.ui.rte.ckeditor;

import com.google.gwt.core.client.JavaScriptObject;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextCompositePlugin;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

/**
 * CK Editor composite plugin which is accessible from javascript world.
 * When exporting the plugin, 3 functions could be accessed from outside:
 * CKEDITOR.nsesaBeforeInit
 * CKEDITOR.nsesaInit
 * CKEDITOR.nsesaAfterInit
 * <p/>
 * User: groza
 * Date: 10/01/13
 * Time: 12:45
 */
public class CkEditorCompositePlugin extends RichTextCompositePlugin {

    public CkEditorCompositePlugin() {
        super();
    }

    @Override
    public void export(RichTextEditorConfig config) {
        nativeExport(config.getConfiguration(), this);
    }

    private native void nativeExport(JavaScriptObject config, CkEditorCompositePlugin plugin) /*-{
        //TODO we need to find a better way to expose the plugin in javascript world
        $wnd.CKEDITOR.nsesaBeforeInit = $entry(function (ed) {
            plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CkEditorCompositePlugin::beforeInit(Lcom/google/gwt/core/client/JavaScriptObject;)(ed);
        });
        $wnd.CKEDITOR.nsesaInit = $entry(function (ed) {
            plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CkEditorCompositePlugin::init(Lcom/google/gwt/core/client/JavaScriptObject;)(ed);
        });
    }-*/;

    public void registerPlugin(RichTextEditorPlugin plugin) {
        plugins.add(plugin);
    }
}
