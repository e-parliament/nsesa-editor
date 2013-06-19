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
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

/**
 * Set up the caret position by checking the caret marker presence inside of the text nodes.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 11/04/13 16:49
 */
public class CKEditorCaretPositionSetupPlugin implements RichTextEditorPlugin {

    private OverlayFactory overlayFactory;
    private String caretPositionClassName;

    private JavaScriptObject caretNode;

    public CKEditorCaretPositionSetupPlugin(OverlayFactory overlayFactory, String caretPositionClassName) {
        this.overlayFactory = overlayFactory;
        this.caretPositionClassName = caretPositionClassName;
    }

    @Override
    public void beforeInit(JavaScriptObject editor) {
        //do nothing
    }

    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(this, editor);
    }

    private native void nativeInit(CKEditorCaretPositionSetupPlugin caretPlugin, JavaScriptObject editor)/*-{
        editor.on('instanceReady', function (ev) {
            var editorInstance = ev.editor;
            caretPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorCaretPositionSetupPlugin::caretNode = null;
            var range = new $wnd.CKEDITOR.dom.range(editorInstance.document);
            range.selectNodeContents(editorInstance.document.getBody());
            caretPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorCaretPositionSetupPlugin::visitNative(Lorg/nsesa/editor/gwt/core/client/ui/rte/ckeditor/CKEditorCaretPositionSetupPlugin;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(caretPlugin, editorInstance, range.startContainer);
            var caretNode = caretPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorCaretPositionSetupPlugin::caretNode;
            if (caretNode) {
                range = new $wnd.CKEDITOR.dom.range(editorInstance.document);
                var nodeList = caretNode.getChildren();
                for (var i = 0; i < nodeList.count(); i++) {
                    var node = nodeList.getItem(i);
                    if (node.type ==  $wnd.CKEDITOR.NODE_TEXT) {
                        range.setStart(node, 0);
                        range.setEnd(node, node.getText().length);
                        editorInstance.getSelection().selectRanges([range]);
                        break;
                    }
                }
            }
        })
    }-*/;

    private native void visitNative(CKEditorCaretPositionSetupPlugin caretPlugin, JavaScriptObject editorInstance, JavaScriptObject container) /*-{
        var caretNode = caretPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorCaretPositionSetupPlugin::caretNode;
        if (caretNode) {
            return;
        }

        var caretClassName = caretPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorCaretPositionSetupPlugin::caretPositionClassName;
        var documentRange = new $wnd.CKEDITOR.dom.range(editorInstance.document);
        var nodeList = container.getChildren();
        for (var i = 0; i < nodeList.count(); i++) {
            var node = nodeList.getItem(i);
            if (node.type == $wnd.CKEDITOR.NODE_ELEMENT) {
                // see if we find our magic marker
                if (node.hasClass(caretClassName)) {
                    caretPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorCaretPositionSetupPlugin::caretNode = node;
                } else {
                    caretPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorCaretPositionSetupPlugin::visitNative(Lorg/nsesa/editor/gwt/core/client/ui/rte/ckeditor/CKEditorCaretPositionSetupPlugin;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(caretPlugin, editorInstance, node);
                }
            }
        }
    }-*/;

    @Override
    public void afterInit(JavaScriptObject editor) {
        //do nothing
    }

}
