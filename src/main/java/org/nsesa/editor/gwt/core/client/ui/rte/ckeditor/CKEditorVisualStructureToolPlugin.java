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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureToggleEvent;
import org.nsesa.editor.gwt.core.client.ui.rte.DefaultRichTextEditorPlugin;

import java.util.logging.Logger;

/**
 * Add a button to CKEditor to fire <code>VisualStructureToggleEvent</code> GWT event.
 * The event is propagated further through the event bus and is handled by <code>VisualStructureController</code> controller
 * to show/hide drafting tool widget.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 22/01/13 12:32
 */
public class CKEditorVisualStructureToolPlugin extends DefaultRichTextEditorPlugin {
    private static final Logger LOG = Logger.getLogger(CKEditorVisualStructureToolPlugin.class.getName());

    /**
     * The client factory used to get event bus
     */
    private ClientFactory clientFactory;

    /**
     * Keep previous state of the button
     */
    private int previousState = -1;

    @Inject
    public CKEditorVisualStructureToolPlugin(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    /**
     * The main method responsible to create a CK editor button, to attach to editor instance and
     * to raise <code>VisualStructureToggleEvent</code> as soon as the button is pressed.
     * Be carefully, the button name shall be the same with the one defined in <code>CKEditorToolbar</code>
     * toolbar configuration.
     * @param editor The Rich Text editor as JavaScriptObject
     */
    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(editor, this);
    }

    /**
     * Creates <code>NsesaToggle</code> button and raise the specific event when the button is pressed.
     * Since the action of the <code>Source</code> could alter the state of <code>NsesaToggle</code> button
     * there is also a handler to preserve the state of this new button
     * @param editor
     * @param plugin
     */
    private native void nativeInit(JavaScriptObject editor, CKEditorVisualStructureToolPlugin plugin) /*-{
        var buttonName = "NsesaToggle";
        var cmd = {
            exec: function (editor) {
                editor.getCommand(buttonName).toggleState();
                var nsesaState = editor.getCommand(buttonName).state;
                var nsesaToggle = {nsesaToggleDraft: (nsesaState == $wnd.CKEDITOR.TRISTATE_ON)};
                editor.fire("nsesaToggleDraft", nsesaToggle);
            }
        }
        editor.addCommand(buttonName, cmd);
        editor.ui.addButton(buttonName, {
            label: "Toggle Drafting tool",
            icon: $wnd.CKEDITOR.basePath + "plugins/nsesa/nsesaDraftingTool.png",
            command: buttonName
        });

        // save the state before executing source command
        editor.on('beforeCommandExec', function (evt) {
            if (evt.data.name == 'source' && evt.editor.mode == 'wysiwyg') {
                if (editor.getCommand('NsesaToggle'))
                    plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorVisualStructureToolPlugin::previousState = editor.getCommand('NsesaToggle').state;
            }
        });

        editor.on('mode', function () {
            if (editor.mode != 'source') {
                if (editor.getCommand('NsesaToggle')) {
                    var state = plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorVisualStructureToolPlugin::previousState;
                    if (state >= 0) {
                        editor.getCommand('NsesaToggle').setState(state);
                        plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorVisualStructureToolPlugin::fireEvent(Z)(state == 1);
                    }
                }
            } else {
                plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorVisualStructureToolPlugin::fireEvent(Z)(false);
            }
        });

        editor.on('nsesaToggleDraft', function (ev) {
            var toggleDraft = ev.data.nsesaToggleDraft;
            plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorVisualStructureToolPlugin::fireEvent(Z)(toggleDraft);
        });

    }-*/;

    private void fireEvent(boolean showTool) {
        clientFactory.getEventBus().fireEvent(new VisualStructureToggleEvent(showTool));
    }
}
