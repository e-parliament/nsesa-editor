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
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

/**
 * A plugin to override the default basic styles (bold, italic, underline, subscript, superscript) of CK editor.
 * The basic styles representation is replaced by spans.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 15/01/13 13:24
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
    public void export(RichTextEditorConfig config) {
        //do nothing
    }

    private native void nativeInit(JavaScriptObject editor) /*-{

        var addButtonCommand = function (buttonName, buttonLabel, commandName, styleDefiniton) {
            NsesaStyleCommand = function (styleDefinition) {
                this.styleDefinition = styleDefinition;
            }
            NsesaStyleCommand.prototype = new $wnd.CKEDITOR.styleCommand();
            NsesaStyleCommand.prototype.exec = function (editor) {
                this.styleDefinition.attributes['ns'] = editor.getSelection().getStartElement().getAttribute('ns');
                this.style = new $wnd.CKEDITOR.style(this.styleDefinition);
                editor.attachStyleStateChange(this.style, function (state) {
                    !editor.readOnly && editor.getCommand(commandName).setState(state);
                });

                $wnd.CKEDITOR.styleCommand.prototype.exec.call(this, editor);
            }
            var nsesaStyleCommand = new NsesaStyleCommand(styleDefiniton);
            editor.addCommand(commandName, nsesaStyleCommand);
            editor.ui.addButton(buttonName,
                {
                    label: buttonLabel,
                    command: commandName
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
            element: newTag,
            attributes: { 'class': className, 'type': newType},
            overrides: oldTag
        };
        addButtonCommand('Bold', lang.bold, 'bold', config.coreStyles_bold);

        newType = "i";
        oldTag = "i";
        className = "widget " + newType;
        config.coreStyles_italic =
        {
            element: newTag,
            attributes: { 'class': className, 'type': newType},
            overrides: oldTag
        };
        addButtonCommand('Italic', lang.italic, 'italic', config.coreStyles_italic);

        newType = "u";
        oldTag = "u";
        className = "widget " + newType;
        config.coreStyles_underline =
        {
            element: newTag,
            attributes: { 'class': className, 'type': newType},
            overrides: oldTag
        };
        addButtonCommand('Underline', lang.underline, 'underline', config.coreStyles_underline);

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
            element: newTag,
            attributes: { 'class': className, 'type': newType},
            overrides: oldTag
        };
        addButtonCommand('Subscript', lang.subscript, 'subscript', config.coreStyles_subscript);

        newType = "sup";
        oldTag = "sup";
        className = "widget " + newType;
        config.coreStyles_superscript =
        {
            element: newTag,
            attributes: { 'class': className, 'type': newType},
            overrides: oldTag
        };
        addButtonCommand('Superscript', lang.superscript, 'superscript', config.coreStyles_superscript);


    }-*/;

}
