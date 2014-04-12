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
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureInsertionEvent;
import org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureInsertionEventHandler;
import org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureSelectionChangedEvent;
import org.nsesa.editor.gwt.core.client.ui.rte.DefaultRichTextEditorPlugin;

/**
 * A CK editor plugin to handle a <code>VisualStructureInsertionEvent</code> GWT event by inserting a DOM element into the
 * editor area.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 22/01/13 13:27
 */
public class CKEditorVisualStructureInsertionPlugin extends DefaultRichTextEditorPlugin {

    /**
     * Enum used to markup a newly introduced element in the editor area.
     */
    private static enum MarkupOperation {
        nsesaIns, nsesaDel, nsesaMod
    }

    /**
     * Used to get a reference to event bus
     */
    private ClientFactory clientFactory;

    public CKEditorVisualStructureInsertionPlugin(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    /**
     *
     * @param editor The Rich Text editor as JavaScriptObject
     */
    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(editor, this);
    }

    private native void nativeInit(JavaScriptObject editor, CKEditorVisualStructureInsertionPlugin plugin) /*-{
        plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorVisualStructureInsertionPlugin::handleDrafting(Lcom/google/gwt/core/client/JavaScriptObject;)(editor);
    }-*/;

    /**
     * Add a handler for <code>VisualStructureInsertionEvent</code>
     * @param editor
     */
    private void handleDrafting(final JavaScriptObject editor) {
        clientFactory.getEventBus().addHandler(VisualStructureInsertionEvent.TYPE, new VisualStructureInsertionEventHandler() {
            @Override
            public void onEvent(VisualStructureInsertionEvent event) {
                //change the text editor
                insertDrafting(CKEditorVisualStructureInsertionPlugin.this, editor, event.getOverlayWidget().getOverlayElement());
            }
        });
    }

    /**
     * Insert a new element in the editor area at the cursor position and raise a <code>VisualStructureSelectionChangedEvent</code>
     * GWT event to refresh if the case other views interested in editor changes.
     * @param plugin
     * @param editor
     * @param el
     */
    private native void insertDrafting(CKEditorVisualStructureInsertionPlugin plugin, JavaScriptObject editor, Element el) /*-{
        if (editor.getSelection()) {
            var text = editor.getSelection() != null ? editor.getSelection().getSelectedText() : "";
            var toInsert = this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorVisualStructureInsertionPlugin::text(Lcom/google/gwt/dom/client/Element;Ljava/lang/String;)(el, text);
            editor.insertHtml(toInsert);
            var endContainer = editor.getSelection().getRanges(1)[0].endContainer;
            var parentTagType = endContainer.getAttribute('data-type'),
                    nameSpace = endContainer.getAttribute('data-ns');
            plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorVisualStructureInsertionPlugin::fireEvent(Lcom/google/gwt/core/client/JavaScriptObject;ZLjava/lang/String;)(endContainer.$, false, "");
        }

    }-*/;

    /**
     * Get a string representation of the element
     * @param el The element that will be processed
     * @param selectedText The inner html that will be added for the given element
     * @return String representation of the given element
     */
    private String text(Element el, String selectedText) {
        el.setInnerHTML(selectedText);
        el.addClassName(MarkupOperation.nsesaIns.name());
        return DOM.toString((com.google.gwt.user.client.Element) el);
    }

    private void fireEvent(JavaScriptObject jsObject, boolean moreTagsSelected, String selectedText) {
        Element el = jsObject.cast();
        clientFactory.getEventBus().fireEvent(new VisualStructureSelectionChangedEvent(el, moreTagsSelected, selectedText));
    }

}
