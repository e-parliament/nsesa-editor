package org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingToggleEvent;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorPlugin;

import java.util.logging.Logger;

/**
 * Adds a button to CKEditor to show/hide the nsesa drafting tool
 * User: groza
 * Date: 22/01/13
 * Time: 12:42
 */
public class CKEditorShowDraftingToolPlugin implements RichTextEditorPlugin {
    private static final Logger LOG = Logger.getLogger(CKEditorShowDraftingToolPlugin.class.getName());

    private ClientFactory clientFactory;

    private int previousState = -1;

    @Inject
    public CKEditorShowDraftingToolPlugin(ClientFactory clientFactory) {
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

    private native void nativeInit(JavaScriptObject editor, CKEditorShowDraftingToolPlugin plugin) /*-{
        editor.on( 'nsesaToggleDraft', function( ev )
        {
            var toggleDraft = ev.data.nsesaToggleDraft;
            plugin.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorShowDraftingToolPlugin::fireEvent(Z)(toggleDraft);
//            if (toggleDraft) {
//                ev.editor.document.getBody().addClass("akomaNtoso");
//            } else {
//                ev.editor.document.getBody().removeClass("akomaNtoso");
//            }
        });
        // save the state before executing source command
        editor.on( 'beforeCommandExec', function( evt )
        {
            if ( evt.data.name == 'source' && evt.editor.mode == 'wysiwyg' )
            {
                plugin.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorShowDraftingToolPlugin::previousState = editor.getCommand('NsesaToggle').state;
            }
        });

        editor.on( 'mode', function()
        {
            if (editor.mode != 'source') {
                if (editor.getCommand('NsesaToggle')) {
                    var state = plugin.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorShowDraftingToolPlugin::previousState;
                    if (state >= 0) {
                        editor.getCommand('NsesaToggle').setState(state);
                    }
                }
            }
        });

    }-*/;

    private void fireEvent(boolean showTool) {
        clientFactory.getEventBus().fireEvent(new DraftingToggleEvent(showTool));
    }
}
