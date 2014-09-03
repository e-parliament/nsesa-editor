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
import org.nsesa.editor.gwt.core.client.ui.rte.DefaultRichTextEditorPlugin;

/**
 * A plugin to override the default basic styles (bold, italic, underline, subscript, superscript) of CK editor.
 * The basic styles representation is replaced by spans.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 15/01/13 13:24
 */
public class CKEditorBasicStylesPlugin extends DefaultRichTextEditorPlugin {

    private OverlayFactory overlayFactory;
    private BasicStyleProvider basicStyleProvider;

    /**
     * Identify the basic styles used in CK editor
     */
    public static enum BasicStyleDefinition {
        b, i, u, sub, sup
    }

    /**
     * An interface to provide the corresponding overlay widget to a given basic style definition
     */
    public static interface BasicStyleProvider {
        OverlayWidget get(BasicStyleDefinition basicStyleDefinition, OverlayWidget parentOverlayWidget);
    }

    /**
     * Default implementation of BasicStyleProvider interface by using the overlay factory to create an overlay widget
     * which should correspond to the basic styles defined in the editor
     *
     */
    public static class DefaultBasicStyleProvider implements BasicStyleProvider {
        private OverlayFactory overlayFactory;

        public DefaultBasicStyleProvider(OverlayFactory overlayFactory) {
            this.overlayFactory = overlayFactory;
        }
        public OverlayWidget get(BasicStyleDefinition basicStyleDefinition, OverlayWidget parentOverlayWidget) {
            return overlayFactory.getAmendableWidget(parentOverlayWidget.getNamespaceURI(), basicStyleDefinition.name());
        }
    }

    /**
     * Default constructor
     * @param overlayFactory {@link OverlayFactory}
     */
    public CKEditorBasicStylesPlugin(OverlayFactory overlayFactory) {
        this(overlayFactory, new DefaultBasicStyleProvider(overlayFactory));
    }

    /**
     * Create an instance with the given parameters
     * @param overlayFactory {@link OverlayFactory}
     * @param basicStyleProvider {@link BasicStyleProvider}
     */
    public CKEditorBasicStylesPlugin(OverlayFactory overlayFactory, BasicStyleProvider basicStyleProvider) {
        this.overlayFactory = overlayFactory;
        this.basicStyleProvider = basicStyleProvider;
    }

    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(this, editor);
    }

    private native void nativeInit(CKEditorBasicStylesPlugin basicStylesPlugin, JavaScriptObject editor) /*-{

        var addButtonCommand = function (buttonName, buttonLabel, commandName, styleDefiniton) {
            NsesaStyleCommand = function (styleDef) {
                this.styleDefinition = styleDef;
            }
            NsesaStyleCommand.prototype = new $wnd.CKEDITOR.styleCommand();
            NsesaStyleCommand.prototype.exec = function (editor) {
                // identify the basic style elements based on the selection start element
                var container = editor.getSelection().getStartElement().$;
                $wnd.console.log("--->", container);
                var tagName = this.styleDefinition.overrides;
                var basicEl = basicStylesPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorBasicStylesPlugin::findBasicStyleElement(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)(container, tagName);
                if (basicEl) {
                    this.styleDefinition.attributes['data-ns'] = basicEl.getAttribute('data-ns');
                    this.styleDefinition.attributes['class'] = basicEl.getAttribute('class');
                    this.styleDefinition.attributes['data-type'] = basicEl.getAttribute('data-type');
                }

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
            oldTag;

        oldTag = "b";
        config.coreStyles_bold =
        {
            element: newTag,
            attributes: {},
            overrides: oldTag
        };
        addButtonCommand('Bold', 'Bold', 'bold', config.coreStyles_bold);

        oldTag = "i";
        config.coreStyles_italic =
        {
            element: newTag,
            attributes: {},
            overrides: oldTag
        };
        addButtonCommand('Italic', 'Italic', 'italic', config.coreStyles_italic);

        oldTag = "u";
        config.coreStyles_underline =
        {
            element: newTag,
            attributes: {},
            overrides: oldTag
        };
        addButtonCommand('Underline', 'Underline', 'underline', config.coreStyles_underline);

        oldTag = "sub";
        config.coreStyles_subscript =
        {
            element: newTag,
            attributes: {},
            overrides: oldTag
        };
        addButtonCommand('Subscript', 'Subscript', 'subscript', config.coreStyles_subscript);

        oldTag = "sup";
        config.coreStyles_superscript =
        {
            element: newTag,
            attributes: {},
            overrides: oldTag
        };
        addButtonCommand('Superscript', 'Superscript', 'superscript', config.coreStyles_superscript);


    }-*/;

    /**
     * Find basic style element based on the given container an style type
     * @param container the parent element as {@link JavaScriptObject}
     * @param tagName The tag name
     * @return {@link JavaScriptObject}
     */
    private JavaScriptObject findBasicStyleElement(JavaScriptObject container, String tagName) {
        Element el = container.cast();
        OverlayWidget parent = overlayFactory.getAmendableWidget(el);
        if (parent != null) {
            OverlayWidget basicStyleWidget = basicStyleProvider.get(BasicStyleDefinition.valueOf(tagName), parent);
            return basicStyleWidget.getOverlayElement();
        }
        return null;
    }
}
