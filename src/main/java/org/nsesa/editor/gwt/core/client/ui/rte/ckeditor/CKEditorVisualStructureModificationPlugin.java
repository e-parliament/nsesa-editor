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
import org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureModificationEvent;
import org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureModificationEventHandler;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

import java.util.Map;

/**
 * A CK editor plugin to handle <code>VisualStructureModificationEvent</code> GWT event by changing the attributes of the
 * selected element in the editor area.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 22/01/13 13:27
 */
public class CKEditorVisualStructureModificationPlugin implements RichTextEditorPlugin {

    private ClientFactory clientFactory;

    public CKEditorVisualStructureModificationPlugin(ClientFactory clientFactory) {
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

    /**
     * Main method to modify the attributes of the selected element in the editor area as soon as a
     * <code>VisualStructureModificationEvent</code> GWT event is raised by other components.
     * @param editor The Rich Text editor as JavaScriptObject
     */
    @Override
    public void init(JavaScriptObject editor) {
        handleDrafting(editor);
    }


    @Override
    public void export(RichTextEditorConfig config) {
        //do nothing
    }

    /**
     * Handle <code>VisualStructureModificationEvent</code> GWT event
     * @param editor
     */
    private void handleDrafting(final JavaScriptObject editor) {
        clientFactory.getEventBus().addHandler(VisualStructureModificationEvent.TYPE, new VisualStructureModificationEventHandler() {
            @Override
            public void onEvent(VisualStructureModificationEvent event) {
                //change the text editor
                final JsArrayString jsKeys = (JsArrayString) JsArrayString.createArray();
                final JsArrayString jsValues = (JsArrayString) JsArrayString.createArray();
                for (final Map.Entry<String, String> entry : event.getAttributes().entrySet()) {
                    jsKeys.push(entry.getKey());
                    jsValues.push(entry.getValue());
                }

                modify(CKEditorVisualStructureModificationPlugin.this, editor, jsKeys, jsValues);
            }
        });
    }

    /**
     * Identify the selected element from editor area and change its attribute values
     * @param plugin
     * @param editor
     * @param keys The attributes keys
     * @param values The new attributes values
     */
    private native void modify(CKEditorVisualStructureModificationPlugin plugin, JavaScriptObject editor, JsArrayString keys, JsArrayString values) /*-{
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
