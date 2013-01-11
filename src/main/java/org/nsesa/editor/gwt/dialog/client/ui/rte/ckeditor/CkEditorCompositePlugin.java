package org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor;

import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextCompositePlugin;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorPlugin;

import java.util.List;

/**
 * CK Editor composite plugin which is accessible from javascript world.
 * When exporting the plugin, 3 functions could be accessed from outside:
 *       CKEDITOR.nsesaBeforeInit
 *       CKEDITOR.nsesaInit
 *       CKEDITOR.nsesaAfterInit
 *
 * User: groza
 * Date: 10/01/13
 * Time: 12:45
 */
public class CkEditorCompositePlugin extends RichTextCompositePlugin {

    public CkEditorCompositePlugin() {
        super();
    }

    @Override
    public void export() {
        exportNativePlugin(this);
    }

    private native void exportNativePlugin(CkEditorCompositePlugin plugin) /*-{
        $wnd.CKEDITOR.nsesaBeforeInit = $entry(function(editor) {
            plugin.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CkEditorCompositePlugin::beforeInit(Lcom/google/gwt/core/client/JavaScriptObject;)(editor);
        });
        $wnd.CKEDITOR.nsesaInit = $entry(function(editor) {
            plugin.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CkEditorCompositePlugin::init(Lcom/google/gwt/core/client/JavaScriptObject;)(editor);
        });

        $wnd.CKEDITOR.nsesaAfterInit = $entry(function(editor) {
            plugin.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CkEditorCompositePlugin::afterInit(Lcom/google/gwt/core/client/JavaScriptObject;)(editor);
        });
    }-*/;

    public void registerPlugin(RichTextEditorPlugin plugin) {
        plugins.add(plugin);
    }
}
