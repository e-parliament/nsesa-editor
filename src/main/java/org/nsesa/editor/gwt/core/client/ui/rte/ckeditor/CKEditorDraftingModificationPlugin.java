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
import com.google.gwt.core.client.JsArrayString;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingModificationEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingModificationEventHandler;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

import java.util.Map;

/**
 * A CK editor plugin to modify the content into the editor text
 * User: groza
 * Date: 17/01/13
 * Time: 15:33
 */
public class CKEditorDraftingModificationPlugin implements RichTextEditorPlugin {

    private static enum MarkupOperation {
        nsesaMod
    }

    private ClientFactory clientFactory;

    public CKEditorDraftingModificationPlugin(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public String getName() {
        return "nsesa-draftingmodification";
    }

    @Override
    public void beforeInit(JavaScriptObject editor) {
        //do nothing
    }

    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(editor, this);
    }


    @Override
    public void export(RichTextEditorConfig config) {
        //do nothing
    }


    private native void nativeInit(JavaScriptObject editor, CKEditorDraftingModificationPlugin plugin) /*-{
        plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingModificationPlugin::handleDrafting(Lcom/google/gwt/core/client/JavaScriptObject;)(editor);
    }-*/;

    private void handleDrafting(final JavaScriptObject editor) {
        clientFactory.getEventBus().addHandler(DraftingModificationEvent.TYPE, new DraftingModificationEventHandler() {
            @Override
            public void onEvent(DraftingModificationEvent event) {
                //change the text editor
                final JsArrayString jsKeys = (JsArrayString) JsArrayString.createArray();
                final JsArrayString jsValues = (JsArrayString) JsArrayString.createArray();
                for (final Map.Entry<String, String> entry : event.getAttributes().entrySet()) {
                    jsKeys.push(entry.getKey());
                    jsValues.push(entry.getValue());
                }

                modify(CKEditorDraftingModificationPlugin.this, editor, jsKeys, jsValues);
            }
        });
    }

    private native void modify(CKEditorDraftingModificationPlugin plugin, JavaScriptObject editor, JsArrayString keys, JsArrayString values) /*-{
        if (editor.cachedElement) {
            var range = new $wnd.CKEDITOR.dom.range(editor.document);
            range.selectNodeContents(editor.cachedElement);
            //editor.getSelection().selectRanges([range]);
            var el = new $wnd.CKEDITOR.dom.element(editor.cachedElement);
            for (i = 0; i < keys.length; i++) {
                if (values[i] != '') {
                    el.setAttribute(keys[i], values[i]);
                }
            }
        }
    }-*/;

}
