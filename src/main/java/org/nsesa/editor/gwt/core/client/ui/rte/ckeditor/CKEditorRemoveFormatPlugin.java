package org.nsesa.editor.gwt.core.client.ui.rte.ckeditor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

import java.util.logging.Logger;

/**
 * Adds a button to CKEditor to remove the format for the selected element.
 * Replace the common ancestor node of the selection with the selected text
 * User: groza
 * Date: 22/01/13
 * Time: 12:42
 */
public class CKEditorRemoveFormatPlugin implements RichTextEditorPlugin {
    private static final Logger LOG = Logger.getLogger(CKEditorRemoveFormatPlugin.class.getName());

    private ClientFactory clientFactory;

    private int previousState = -1;

    @Inject
    public CKEditorRemoveFormatPlugin(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public String getName() {
        return "nsesa-removeformat";
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

    private native void nativeInit(JavaScriptObject editor, CKEditorRemoveFormatPlugin plugin) /*-{
        var buttonName = "NsesaRemoveFormat";
        var cmd = {
            exec: function(ed) {
                removeElementFormat(ed);
            }
        }
        editor.addCommand(buttonName, cmd);
        editor.ui.addButton(buttonName,{
            label:"Remove format",
            icon:$wnd.CKEDITOR.basePath + "plugins/nsesa/nsesaRemoveFormat.png",
            command: buttonName
        });

        //remove the format for the selected element
        function removeElementFormat(ed) {
            if (ed.getSelection()) {
                //save snapshot before
                var commonAncestor = ed.getSelection().getCommonAncestor();
                ed.fire( 'saveSnapshot' );
                if (commonAncestor) {
                    var text = new $wnd.CKEDITOR.dom.text( commonAncestor.getText());

                    var node = commonAncestor;
                    while (node && node.type != $wnd.CKEDITOR.NODE_ELEMENT) {
                        node = node.getParent();
                    }
                    if (node && node.getAttribute('type')) {
                        node.remove();
                        if (ed.getSelection().getRanges(1).length > 0) {
                            ed.getSelection().getRanges(1)[0].insertNode(text);
                        }
                        //save snapshot after
                        ed.fire( 'saveSnapshot' );
                    }
                }
            }
        }


    }-*/;

}
