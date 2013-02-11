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
