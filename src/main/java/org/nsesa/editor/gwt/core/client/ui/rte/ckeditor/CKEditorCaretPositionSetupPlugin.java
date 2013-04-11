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

    /** skip rule **/
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
        editor.on('instanceReady', function(ev){
            var editorInstance = ev.editor;
            var range = new $wnd.CKEDITOR.dom.range(editorInstance.document);
            range.selectNodeContents(editorInstance.document.getBody());
            var nodeList = range.startContainer.getChildren();
            var elem;
            for (i = 0; i < nodeList.count(); i++) {
                var node = nodeList.getItem(i);
                if (node.type == $wnd.CKEDITOR.NODE_ELEMENT) {
                    var isSkipped = caretPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorCaretPositionSetupPlugin::skip(Lcom/google/gwt/core/client/JavaScriptObject;)(node.$);
                    if (!isSkipped) {
                        elem = node;
                        break;
                    }
                }
            }
            if (elem) {
                range = new $wnd.CKEDITOR.dom.range(range.document);
                range.setStart(elem, 0);
                range.setEnd(elem, 0);
                editorInstance.getSelection().selectRanges([range]);
            }
        })
    }-*/;

    @Override
    public void afterInit(JavaScriptObject editor) {
        //do nothing
    }

    private boolean skip(JavaScriptObject element) {
        Element el = element.cast();
        OverlayWidget original = overlayFactory.getAmendableWidget(el);
        return (original == null) ? true : skipCaretPositionRule.skip(original);

    }
}
