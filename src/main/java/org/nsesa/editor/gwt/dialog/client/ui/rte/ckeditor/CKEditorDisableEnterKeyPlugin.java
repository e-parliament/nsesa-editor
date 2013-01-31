package org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor;

import com.google.gwt.core.client.JavaScriptObject;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorPlugin;

/**
 * Disable enter and shift enter keys in the editor
 * User: groza
 * Date: 31/01/13
 * Time: 8:40
 */
public class CKEditorDisableEnterKeyPlugin implements RichTextEditorPlugin {
    @Override
    public String getName() {
        return "nsesa-disableEnterKeyPlugin";
    }

    @Override
    public void beforeInit(JavaScriptObject editor) {
        //do nothing
    }

    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(editor);
    }

    private native void nativeInit(JavaScriptObject editor) /*-{
        editor.addCommand( 'enter', {
            modes : { wysiwyg:1 },
            editorFocus : false,
            exec : function( editor ){ enter( editor ); }
        });

        editor.addCommand( 'shiftEnter', {
            modes : { wysiwyg:1 },
            editorFocus : false,
            exec : function( editor ){ shiftEnter( editor ); }
        });

        var keystrokes = editor.keystrokeHandler.keystrokes;
        keystrokes[ 13 ] = 'enter';
        keystrokes[ $wnd.CKEDITOR.SHIFT + 13 ] = 'shiftEnter';

        $wnd.CKEDITOR.plugins.enterkey =
        {
            enterBlock : function( editor, mode, range, forceMode )
            {
                enterBr( editor, mode, range, forceMode );
                return;
            },

            enterBr : function( editor, mode, range, forceMode )
            {
                return;
            }
        };

        var plugin = $wnd.CKEDITOR.plugins.enterkey,
                enterBr = plugin.enterBr,
                enterBlock = plugin.enterBlock;

        function shiftEnter( editor )
        {
            // Only effective within document.
            if ( editor.mode != 'wysiwyg' )
                return false;

            // On SHIFT+ENTER:
            // 1. We want to enforce the mode to be respected, instead
            // of cloning the current block. (#77)
            return enter( editor, editor.config.shiftEnterMode, 1 );
        }

        function enter( editor, mode, forceMode )
        {
            forceMode = editor.config.forceEnterMode || forceMode;

            // Only effective within document.
            if ( editor.mode != 'wysiwyg' )
                return false;

            if ( !mode )
                mode = editor.config.enterMode;

            // Use setTimout so the keys get cancelled immediatelly.
            setTimeout( function()
            {
                editor.fire( 'saveSnapshot' );	// Save undo step.

                if ( mode == $wnd.CKEDITOR.ENTER_BR )
                    enterBr( editor, mode, null, forceMode );
                else
                    enterBlock( editor, mode, null, forceMode );

                editor.fire( 'saveSnapshot' );

            }, 0 );

            return true;
        }
    }-*/;
    @Override
    public void export(RichTextEditorConfig config) {
        //do nothing
    }
}
