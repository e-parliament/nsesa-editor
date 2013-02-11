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
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingInsertionEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingInsertionEventHandler;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

/**
 * A CK editor plugin to insert an amendable widget into the editor text
 * User: groza
 * Date: 17/01/13
 * Time: 15:33
 */
public class CKEditorDraftingInsertionPlugin implements RichTextEditorPlugin {

    private static enum MarkupOperation {
        nsesaIns, nsesaDel, nsesaMod
    }

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
        plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingInsertionPlugin::handleDrafting(Lcom/google/gwt/core/client/JavaScriptObject;)(editor);
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
        var toInsert = this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorDraftingInsertionPlugin::text(Lcom/google/gwt/dom/client/Element;Ljava/lang/String;)(el, text);
        editor.insertHtml(toInsert);
    }-*/;

    private String text(Element el, String selectedText) {
        el.setInnerHTML(selectedText);
        el.addClassName(MarkupOperation.nsesaIns.name());
        return DOM.toString((com.google.gwt.user.client.Element) el);
    }
}
