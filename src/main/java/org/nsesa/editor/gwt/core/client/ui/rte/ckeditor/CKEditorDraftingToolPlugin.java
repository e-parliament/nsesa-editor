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
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingToggleEvent;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

import java.util.logging.Logger;

/**
 * Add a button to CKEditor to fire <code>DraftingToggleEvent</code> GWT event.
 * The event is propagated further through the event bus and is handled by <code>DraftingController</code> controller
 * to show/hide drafting tool widget.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 22/01/13 12:32
 */
public class CKEditorDraftingToolPlugin implements RichTextEditorPlugin {
    private static final Logger LOG = Logger.getLogger(CKEditorDraftingToolPlugin.class.getName());

    /**
     * The client factory used to get event bus
     */
    private ClientFactory clientFactory;

    /**
     * Keep previous state of the button
     */
    private int previousState = -1;

    @Inject
    public CKEditorDraftingToolPlugin(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public String getName() {
        return "nsesa-draftingtoolbar";
    }

    /**
     * No beforeInit operation performed
     * @param editor The Rich Text editor as JavaScriptObject
     */
    @Override
    public void beforeInit(JavaScriptObject editor) {
        //do nothing
    }

    /**
     * The main method responsible to create a CK editor button, to attach to editor instance and
     * to raise <code>DraftingToggleEvent</code> as soon as the button is pressed.
     * Be carefully, the button name shall be the same with the one defined in <code>CKEditorToolbar</code>
     * toolbar configuration.
     * @param editor The Rich Text editor as JavaScriptObject
     */
    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(editor, this);
    }

    /**
     * No export operation performed
     * @param config The Rich Text editor configuration as JavaScriptObject
     */
    @Override
    public void export(RichTextEditorConfig config) {
        //do nothing
    }

    /**
     * Creates <code>NsesaToggle</code> button and raise the specific event when the button is pressed.
     * Since the action of the <code>Source</code> could alter the state of <code>NsesaToggle</code> button
     * there is also a handler to preserve the state of this new button
     * @param editor
     * @param plugin
     */
    private native void nativeInit(JavaScriptObject editor, CKEditorDraftingToolPlugin plugin) /*-{
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
                    plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingToolPlugin::previousState = editor.getCommand('NsesaToggle').state;
            }
        });

        editor.on('mode', function () {
            if (editor.mode != 'source') {
                if (editor.getCommand('NsesaToggle')) {
                    var state = plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingToolPlugin::previousState;
                    if (state >= 0) {
                        editor.getCommand('NsesaToggle').setState(state);
                        plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingToolPlugin::fireEvent(Z)(state == 1);
                    }
                }
            } else {
                plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingToolPlugin::fireEvent(Z)(false);
            }
        });

        editor.on('nsesaToggleDraft', function (ev) {
            var toggleDraft = ev.data.nsesaToggleDraft;
            plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingToolPlugin::fireEvent(Z)(toggleDraft);
        });

    }-*/;

    private void fireEvent(boolean showTool) {
        clientFactory.getEventBus().fireEvent(new DraftingToggleEvent(showTool));
    }
}
