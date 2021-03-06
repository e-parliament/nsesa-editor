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
import org.nsesa.editor.gwt.core.client.ui.rte.DefaultRichTextEditorPlugin;

/**
 * Disable enter and shift enter keys in the editor
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 31/01/13 08:40
 *
 */
public class CKEditorDisableEnterKeyPlugin extends DefaultRichTextEditorPlugin {

    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(editor);
    }

    private native void nativeInit(JavaScriptObject editor) /*-{
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
                return;
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

            // Use setTimout so the keys get cancelled immediately.
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
    }-*/;

}
