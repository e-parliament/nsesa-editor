package org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingInsertionEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingInsertionEventHandler;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorPlugin;

/**
 * A CK editor plugin to insert an amendable widget into the editor text
 * User: groza
 * Date: 17/01/13
 * Time: 15:33
 */
public class CKEditorDraftingInsertionPlugin implements RichTextEditorPlugin {

    private ClientFactory clientFactory;

    public CKEditorDraftingInsertionPlugin(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public String getName() {
        return "nsesa-draftinginsertion";
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


    private native void nativeInit(JavaScriptObject editor, CKEditorDraftingInsertionPlugin plugin) /*-{
        plugin.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorDraftingInsertionPlugin::handleDrafting(Lcom/google/gwt/core/client/JavaScriptObject;)(editor);
    }-*/;

    private void handleDrafting(final JavaScriptObject editor) {
        clientFactory.getEventBus().addHandler(DraftingInsertionEvent.TYPE, new DraftingInsertionEventHandler() {
            @Override
            public void onEvent(DraftingInsertionEvent event) {
                //change the text editor
                insertDrafting(editor, event.getAmendableWidget().getAmendableElement());
            }
        });
    }

    private native void insertDrafting(JavaScriptObject editor, Element el) /*-{
        var text = editor.getSelection() != null ? editor.getSelection().getSelectedText() : "";
        var toInsert = this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorDraftingInsertionPlugin::text(Lcom/google/gwt/dom/client/Element;Ljava/lang/String;)(el, text);
        editor.insertHtml(toInsert);
    }-*/;

    private String text(Element el, String selectedText) {
        el.setInnerHTML(selectedText);
        return DOM.toString((com.google.gwt.user.client.Element) el);
    }
}
