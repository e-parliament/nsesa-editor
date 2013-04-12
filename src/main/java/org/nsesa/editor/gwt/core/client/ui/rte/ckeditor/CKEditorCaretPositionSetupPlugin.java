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
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

/**
 * Set up the caret position at the beginning of the text
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 11/04/13 16:49
 */
public class CKEditorCaretPositionSetupPlugin implements RichTextEditorPlugin {

    /**
     * skip rule *
     */
    public static interface SkipCaretPositionRule {
        abstract boolean skip(OverlayWidget widget);
    }

    public static SkipCaretPositionRule NUM_SKIP_RULE = new SkipCaretPositionRule() {
        @Override
        public boolean skip(OverlayWidget widget) {
            return "num".equalsIgnoreCase(widget.getType());
        }
    };

    private OverlayFactory overlayFactory;
    private SkipCaretPositionRule skipCaretPositionRule;

    public CKEditorCaretPositionSetupPlugin(OverlayFactory overlayFactory, SkipCaretPositionRule skipCaretPositionRule) {
        this.overlayFactory = overlayFactory;
        this.skipCaretPositionRule = skipCaretPositionRule;
    }

    public CKEditorCaretPositionSetupPlugin(OverlayFactory overlayFactory) {
        this(overlayFactory, NUM_SKIP_RULE);
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
            var range = new $wnd.CKEDITOR.dom.range(editorInstance.document);
            range.selectNodeContents(editorInstance.document.getBody());
            caretPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorCaretPositionSetupPlugin::visitNative(Lorg/nsesa/editor/gwt/core/client/ui/rte/ckeditor/CKEditorCaretPositionSetupPlugin;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(caretPlugin, editorInstance, range.startContainer);
        })
    }-*/;

    private native void visitNative(CKEditorCaretPositionSetupPlugin caretPlugin, JavaScriptObject editorInstance, JavaScriptObject container) /*-{
        var documentRange = new $wnd.CKEDITOR.dom.range(editorInstance.document);
        var nodeList = container.getChildren();
        for (var i = 0; i < nodeList.count(); i++) {
            var node = nodeList.getItem(i);
            if (node.type == $wnd.CKEDITOR.NODE_ELEMENT) {
                var isSkipped = caretPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorCaretPositionSetupPlugin::skip(Lcom/google/gwt/core/client/JavaScriptObject;)(node.$);
                if (!isSkipped) {
                    // see if we find our magic marker
                    var children = node.getChildren();
                    for (var j = 0; j < children.count(); j++) {
                        var child = children.getItem(j);

                        if (child.type == $wnd.CKEDITOR.NODE_TEXT) {
                            if (child.getText().contains("\u200b")) {
                                var indexStart = child.getText().indexOf("\u200b");
                                var indexEnd = child.getText().indexOf("\u200b", indexStart + 1);

                                var range = new $wnd.CKEDITOR.dom.range(documentRange.document);
                                range.setStart(node, 0);
                                range.setEnd(node, 1); // or 0 instead of 1 to simply place the caret at the beginning
                                editorInstance.getSelection().selectRanges([range]);
                            }
                        }
                    }
                }
                caretPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorCaretPositionSetupPlugin::visitNative(Lorg/nsesa/editor/gwt/core/client/ui/rte/ckeditor/CKEditorCaretPositionSetupPlugin;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(caretPlugin, editorInstance, node);
            }
        }
    }-*/;

    @Override
    public void afterInit(JavaScriptObject editor) {
        //do nothing
    }

    private boolean skip(JavaScriptObject element) {
        Element el = element.cast();
        OverlayWidget original = overlayFactory.getAmendableWidget(el);
        return (original == null) || skipCaretPositionRule.skip(original);

    }
}
