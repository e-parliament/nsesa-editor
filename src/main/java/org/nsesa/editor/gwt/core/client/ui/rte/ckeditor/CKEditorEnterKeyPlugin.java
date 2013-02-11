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
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

/**
 * A CK plugin to handle enter and shift enter keystrokes
 * User: groza
 * Date: 10/01/13
 * Time: 12:16
 */
public class CKEditorEnterKeyPlugin implements RichTextEditorPlugin {
    private OverlayFactory overlayFactory;
    private String tagName;

    @Inject
    public CKEditorEnterKeyPlugin(OverlayFactory overlayFactory, String tagName) {
        this.overlayFactory = overlayFactory;
        this.tagName = tagName;
    }

    @Override
    public String getName() {
        return "nsesaEnterKey";
    }

    @Override
    public void beforeInit(JavaScriptObject editor) {
        //do nothing
    }

    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(editor, overlayFactory, tagName);
    }

    private native void nativeInit(JavaScriptObject editor, OverlayFactory overlayFactory, String tagName) /*-{
        editor.addCommand('enter', {
            modes: { wysiwyg: 1 },
            editorFocus: false,
            exec: function (editor) {
                enter(editor);
            }
        });

        editor.addCommand('shiftEnter', {
            modes: { wysiwyg: 1 },
            editorFocus: false,
            exec: function (editor) {
                shiftEnter(editor);
            }
        });

        var keystrokes = editor.keystrokeHandler.keystrokes;
        keystrokes[ 13 ] = 'enter';
        keystrokes[ $wnd.CKEDITOR.SHIFT + 13 ] = 'shiftEnter';

        $wnd.CKEDITOR.plugins.enterkey =
        {
            enterBlock: function (editor, mode, range, forceMode) {
                enterBr(editor, mode, range, forceMode);
                return;
            },

            enterBr: function (editor, mode, range, forceMode) {
                // Get the range for the current selection.
                range = range || getRange(editor);
                // We may not have valid ranges to work on, like when inside a
                // contenteditable=false element.
                if (!range)
                    return;

                var doc = range.document;
                var isPre = false;

                var ns = editor.getSelection().getStartElement().getAttribute('ns');
                var lineBreak;
                //create a span of type br
                lineBreak = doc.createElement('span');
                lineBreak.setAttribute('class', 'widget ' + tagName);
                lineBreak.setAttribute('type', tagName);
                lineBreak.setAttribute('ns', ns);
//                var aw = overlayFactory.@org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory::getAmendableWidget(Ljava/lang/String;)('br');
//                var el = aw.@org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget::getAmendableElement()();
                range.deleteContents();
                range.insertNode(lineBreak);

                // This collapse guarantees the cursor will be blinking.
                range.collapse(true);

                range.select(isPre);
            }
        };

        var plugin = $wnd.CKEDITOR.plugins.enterkey,
            enterBr = plugin.enterBr,
            enterBlock = plugin.enterBlock;

        function shiftEnter(editor) {
            // Only effective within document.
            if (editor.mode != 'wysiwyg')
                return false;

            // On SHIFT+ENTER:
            // 1. We want to enforce the mode to be respected, instead
            // of cloning the current block. (#77)
            return enter(editor, editor.config.shiftEnterMode, 1);
        }

        function enter(editor, mode, forceMode) {
            forceMode = editor.config.forceEnterMode || forceMode;

            // Only effective within document.
            if (editor.mode != 'wysiwyg')
                return false;

            if (!mode)
                mode = editor.config.enterMode;

            // Use setTimout so the keys get cancelled immediatelly.
            setTimeout(function () {
                editor.fire('saveSnapshot');	// Save undo step.

                if (mode == $wnd.CKEDITOR.ENTER_BR)
                    enterBr(editor, mode, null, forceMode);
                else
                    enterBlock(editor, mode, null, forceMode);

                editor.fire('saveSnapshot');

            }, 0);

            return true;
        }

        function getRange(editor) {
            // Get the selection ranges.
            var ranges = editor.getSelection().getRanges(true);

            // Delete the contents of all ranges except the first one.
            for (var i = ranges.length - 1; i > 0; i--) {
                ranges[ i ].deleteContents();
            }

            // Return the first range.
            return ranges[ 0 ];
        }
    }-*/;

    @Override
    public void export(RichTextEditorConfig config) {
        // do nothing
    }

}
