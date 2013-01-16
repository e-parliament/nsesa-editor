package org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor;

import com.google.gwt.core.client.JavaScriptObject;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorPlugin;

/**
 * A plugin to override the default basic styles plugin
 * User: groza
 * Date: 15/01/13
 * Time: 13:04
 */
public class CKEditorBasicStylesPlugin implements RichTextEditorPlugin {
    @Override
    public String getName() {
        return "nsesa-basicstyles";
    }

    @Override
    public void beforeInit(JavaScriptObject editor) {
        //do nothing
    }

    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(editor);
    }

    @Override
    public void afterInit(JavaScriptObject editor) {
        //do nothing
    }

    @Override
    public void export() {
        //do nothing
    }

    private native void nativeInit(JavaScriptObject editor) /*-{

        var addButtonCommand = function( buttonName, buttonLabel, commandName, styleDefiniton )
        {
            NsesaStyleCommand = function(styleDefinition) {
                this.styleDefinition = styleDefinition;
            }
            NsesaStyleCommand.prototype = new $wnd.CKEDITOR.styleCommand();
            NsesaStyleCommand.prototype.exec = function(editor) {
                this.styleDefinition.attributes['ns'] = editor.getSelection().getStartElement().getAttribute('ns');
                this.style = new $wnd.CKEDITOR.style( this.styleDefinition);
                editor.attachStyleStateChange(this.style, function( state )
                {
                    !editor.readOnly && editor.getCommand( commandName ).setState( state );
                });

                $wnd.CKEDITOR.styleCommand.prototype.exec.call(this, editor);
            }
            var nsesaStyleCommand = new NsesaStyleCommand(styleDefiniton);
            editor.addCommand( commandName, nsesaStyleCommand);
            editor.ui.addButton( buttonName,
                    {
                        label : buttonLabel,
                        command : commandName
                    });
        };
        var config = editor.config,
            lang = editor.lang,
            newTag = "span",
            newType,
            className,
            oldTag;

        newType = "b";
        oldTag = "b";
        className = "widget " + newType;
        config.coreStyles_bold =
        {
            element : newTag,
            attributes : { 'class' : className, 'type' : newType},
            overrides : oldTag
        };
        addButtonCommand( 'Bold'		, lang.bold		, 'bold'		, config.coreStyles_bold );

        newType = "i";
        oldTag = "i";
        className = "widget " + newType;
        config.coreStyles_italic =
        {
            element : newTag,
            attributes : { 'class' : className, 'type' : newType},
            overrides : oldTag
        };
        addButtonCommand( 'Italic'		, lang.italic		, 'italic'		, config.coreStyles_italic );

        newType = "u";
        oldTag = "u";
        className = "widget " + newType;
        config.coreStyles_underline =
        {
            element : newTag,
            attributes : { 'class' : className, 'type' : newType},
            overrides : oldTag
        };
        addButtonCommand( 'Underline'	, lang.underline		, 'underline'	, config.coreStyles_underline );

//        newType = "strike";
//        oldTag = "strike";
//        config.coreStyles_strike =
//        {
//            element : newTag,
//            attributes : { 'class' : className, 'type' : newType, 'ns' : nameSpace},
//            overrides : oldTag
//        };
//        addButtonCommand( 'Strike'		, lang.strike		, 'strike'		, config.coreStyles_strike );

        newType = "sub";
        oldTag = "sub";
        className = "widget " + newType;
        config.coreStyles_subscript =
        {
            element : newTag,
            attributes : { 'class' : className, 'type' : newType},
            overrides : oldTag
        };
        addButtonCommand( 'Subscript'	, lang.subscript		, 'subscript'	, config.coreStyles_subscript );

        newType = "sup";
        oldTag = "sup";
        className = "widget " + newType;
        config.coreStyles_superscript =
        {
            element : newTag,
            attributes : { 'class' : className, 'type' : newType},
            overrides : oldTag
        };
        addButtonCommand( 'Superscript'	, lang.superscript		, 'superscript'	, config.coreStyles_superscript );


    }-*/;

}
