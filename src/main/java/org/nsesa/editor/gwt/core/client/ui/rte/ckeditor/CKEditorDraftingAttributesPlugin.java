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
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingAttributesToggleEvent;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

import java.util.logging.Logger;

/**
 * Adds a button to CKEditor to fire nsesaToggleDraftAttributes event
 * User: groza
 * Date: 22/01/13
 * Time: 12:42
 */
public class CKEditorDraftingAttributesPlugin implements RichTextEditorPlugin {
    private static final Logger LOG = Logger.getLogger(CKEditorDraftingAttributesPlugin.class.getName());

    private ClientFactory clientFactory;

    private int previousState = -1;

    @Inject
    public CKEditorDraftingAttributesPlugin(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public String getName() {
        return "nsesa-draftingattributestoolbar";
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

    private native void nativeInit(JavaScriptObject editor, CKEditorDraftingAttributesPlugin plugin) /*-{
        var buttonName = "NsesaToggleAttributes";
        var cmd = {
            exec: function (editor) {
                editor.getCommand(buttonName).toggleState();
                var nsesaState = editor.getCommand(buttonName).state;
                var nsesaToggle = {nsesaToggleDraftAttributes: (nsesaState == $wnd.CKEDITOR.TRISTATE_ON)};
                editor.fire("nsesaToggleDraftAttributes", nsesaToggle);
            }
        }
        editor.addCommand(buttonName, cmd);
        editor.ui.addButton(buttonName, {
            label: "Toggle Drafting Attributes tool",
            icon: $wnd.CKEDITOR.basePath + "plugins/nsesa/nsesaDraftingAttributes.png",
            command: buttonName
        });

        // save the state before executing source command
        editor.on('beforeCommandExec', function (evt) {
            if (evt.data.name == 'source' && evt.editor.mode == 'wysiwyg') {
                if (editor.getCommand(buttonName)) {
                    plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingAttributesPlugin::previousState = editor.getCommand(buttonName).state;
                }
            }
        });

        editor.on('mode', function () {
            if (editor.mode != 'source') {
                if (editor.getCommand(buttonName)) {
                    var state = plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingAttributesPlugin::previousState;
                    if (state >= 0) {
                        editor.getCommand(buttonName).setState(state);
                        plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingAttributesPlugin::fireEvent(Z)(state == 1);
                    }
                }
            } else {
                plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingAttributesPlugin::fireEvent(Z)(false);
            }
        });

        editor.on('nsesaToggleDraftAttributes', function (ev) {
            var toggleDraftAttributes = ev.data.nsesaToggleDraftAttributes;
            plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingAttributesPlugin::fireEvent(Z)(toggleDraftAttributes);
        });

    }-*/;

    private void fireEvent(boolean showTool) {
        clientFactory.getEventBus().fireEvent(new DraftingAttributesToggleEvent(showTool));
    }
}
