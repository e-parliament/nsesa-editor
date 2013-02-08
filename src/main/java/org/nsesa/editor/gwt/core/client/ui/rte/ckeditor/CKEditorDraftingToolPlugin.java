package org.nsesa.editor.gwt.core.client.ui.rte.ckeditor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingToggleEvent;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

import java.util.logging.Logger;

/**
 * Adds a button to CKEditor to fire nsesaToggleDraft event
 * User: groza
 * Date: 22/01/13
 * Time: 12:42
 */
public class CKEditorDraftingToolPlugin implements RichTextEditorPlugin {
    private static final Logger LOG = Logger.getLogger(CKEditorDraftingToolPlugin.class.getName());

    private ClientFactory clientFactory;

    private int previousState = -1;

    @Inject
    public CKEditorDraftingToolPlugin(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public String getName() {
        return "nsesa-draftingtoolbar";
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

    private native void nativeInit(JavaScriptObject editor, CKEditorDraftingToolPlugin plugin) /*-{
        var buttonName = "NsesaToggle";
        var cmd = {
            exec: function(editor) {
                editor.getCommand(buttonName).toggleState();
                var nsesaState = editor.getCommand(buttonName).state;
                var nsesaToggle = {nsesaToggleDraft : (nsesaState == $wnd.CKEDITOR.TRISTATE_ON)};
                editor.fire("nsesaToggleDraft", nsesaToggle);
            }
        }
        editor.addCommand(buttonName, cmd);
        editor.ui.addButton(buttonName,{
            label:"Toggle Drafting tool",
            icon:$wnd.CKEDITOR.basePath + "plugins/nsesa/nsesaDraftingTool.png",
            command: buttonName
        });

        // save the state before executing source command
        editor.on( 'beforeCommandExec', function( evt )
        {
            if ( evt.data.name == 'source' && evt.editor.mode == 'wysiwyg' )
            {
                if (editor.getCommand('NsesaToggle'))
                    plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingToolPlugin::previousState = editor.getCommand('NsesaToggle').state;
            }
        });

        editor.on( 'mode', function()
        {
            if (editor.mode != 'source') {
                if (editor.getCommand('NsesaToggle')) {
                    var state = plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingToolPlugin::previousState;
                    if (state >= 0) {
                        editor.getCommand('NsesaToggle').setState(state);
                        plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingToolPlugin::fireEvent(Z)(true);
                    }
                }
            } else {
                plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingToolPlugin::fireEvent(Z)(false);
            }
        });

        editor.on( 'nsesaToggleDraft', function( ev )
        {
            var toggleDraft = ev.data.nsesaToggleDraft;
            plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingToolPlugin::fireEvent(Z)(toggleDraft);
        });

    }-*/;

    private void fireEvent(boolean showTool) {
        clientFactory.getEventBus().fireEvent(new DraftingToggleEvent(showTool));
    }
}
