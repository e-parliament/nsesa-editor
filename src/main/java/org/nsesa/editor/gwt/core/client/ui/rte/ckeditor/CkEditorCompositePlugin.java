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
package org.nsesa.editor.gwt.core.client.ui.rte.ckeditor;

import org.nsesa.editor.gwt.core.client.ui.rte.RichTextCompositePlugin;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

/**
 * Make possible to run Java plugins from an external Java script code.
 * There is a wrapper plugin under js CK editor plugins folder (nsesa) responsible to load this java plugin.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 10/01/13 12:24
 */
public class CkEditorCompositePlugin extends RichTextCompositePlugin {

    public CkEditorCompositePlugin() {
        super();
        nativeExport(this);
    }

    /**
     * Assigns beforeInit and init methods of java plugin to visible javascript <code>CKEDITOR</code> object.
     * @param plugin
     */
    private native void nativeExport(CkEditorCompositePlugin plugin) /*-{
        //TODO we need to find a better way to expose the plugin in javascript world
        $wnd.CKEDITOR.nsesaBeforeInit = $entry(function (ed) {
            plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CkEditorCompositePlugin::beforeInit(Lcom/google/gwt/core/client/JavaScriptObject;)(ed);
        });
        $wnd.CKEDITOR.nsesaInit = $entry(function (ed) {
            plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CkEditorCompositePlugin::init(Lcom/google/gwt/core/client/JavaScriptObject;)(ed);
        });
        $wnd.CKEDITOR.nsesaAfterInit = $entry(function (ed) {
            plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CkEditorCompositePlugin::afterInit(Lcom/google/gwt/core/client/JavaScriptObject;)(ed);
        });
    }-*/;

    /**
     * Add a plugin in the wrapped plugins list
     * @param plugin The plugin that will be added
     */
    public void registerPlugin(RichTextEditorPlugin plugin) {
        plugins.add(plugin);
    }
}
