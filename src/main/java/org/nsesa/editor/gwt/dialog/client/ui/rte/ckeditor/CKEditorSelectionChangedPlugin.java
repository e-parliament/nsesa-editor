package org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.drafting.SelectionChangedEvent;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorPlugin;

import java.util.logging.Logger;

/**
 * A Ck editor plugin which raise GWT application event whenever the user move the cursor
 * or make a selection
 * User: groza
 * Date: 17/01/13
 * Time: 9:38
 */
public class CKEditorSelectionChangedPlugin implements RichTextEditorPlugin {

    private static final Logger LOG = Logger.getLogger(CKEditorSelectionChangedPlugin.class.getName());

    private ClientFactory clientFactory;

    @Inject
    public CKEditorSelectionChangedPlugin(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public String getName() {
        return "nsesa-selectionChanged";
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

    private native void nativeInit(JavaScriptObject editor, CKEditorSelectionChangedPlugin plugin) /*-{

        editor.on( 'selectionChange', function( ev )
        {
            var env = $wnd.CKEDITOR.env,
                    selection = ev.data.selection,
                    element = selection.getStartElement(),
                    html = [],
                    editor = ev.editor,
                    parentTagType,
                    nameSpace,
                    moreTagsSelected = false,
                    selectedText = selection.getSelectedText();

            parentTagType = element.getAttribute('type');
            nameSpace = element.getAttribute('ns');
//            for (var i = 0; i < selection.getRanges().length; i++) {
//                range = selection.getRanges()[i];
//                if (range.startContainer != range.endContainer) {
//                    moreTagsSelected = true;
//                }
//            }
//            if (!moreTagsSelected) {
//                var walker = new $wnd.CKEDITOR.dom.walker(, node;
//                while (node = walker.next()) {
//                    moreTagsSelected = true;
//                    break;
//                }
//            }
            if (parentTagType) {
                    plugin.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorSelectionChangedPlugin::fireEvent(Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;)(parentTagType, nameSpace, moreTagsSelected, selectedText);
            }
        });
    }-*/;

    private void fireEvent(String parentTagType, String nameSpace, boolean moreTagsSelected, String selectedText) {
        LOG.info("Changed event fired with more tags selected: " + moreTagsSelected + " and selected text " + selectedText);
        clientFactory.getEventBus().fireEvent(new SelectionChangedEvent(parentTagType, nameSpace, moreTagsSelected, selectedText));
    }
}
