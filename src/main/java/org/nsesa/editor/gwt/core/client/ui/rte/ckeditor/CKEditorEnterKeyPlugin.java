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
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.rte.DefaultRichTextEditorPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * A plugin to handle enter and shift enter keys.
 * <p>There are three available modes of representation:
 * The "text" mode corresponds to "wysiwyg" ck editor mode, "visual structure" mode is when the visual structure widget
 * is displayed, while "source" mode corresponds to "source" ck editor mode.
 * a. when we are in the middle of the text node display custom BR tag if you are in "text" or
 * "visual structure" mode. This br tag is translated into the For "source" mode nothing happens.
 * b. when you are at the end of the next node, create a new tag with the same type as the parent
 * container of the original text node by default unless there is a specific rule set up to get
 * the "relevant" ancestor type
 * c. when you are at the beginning of the next node, create a new tag with the same type as the parent
 * container of the original text node by default unless there is a specific rule set up to get
 * the "relevant" ancestor type and to create a new tag with that type
 * d. for shift enter just add html custom br in all cases that is displayed in the same way as it is specified
 * at point a)
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 12/03/13 9:03
 */
public class CKEditorEnterKeyPlugin extends DefaultRichTextEditorPlugin {

    /**
     * An interface to specify the rule when you press enter over an overlay widget in the editor area
     */
    public static interface EnterRule {
        abstract OverlayWidget onEnter(OverlayWidget widget);
    }


    /**
     * Default Implementation of {@link org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorEnterKeyPlugin.EnterRule}
     * by returning a new widget with the same name and uri as the processed widget
     */
    public static class DefaultEnterRule implements EnterRule {

        private OverlayFactory factory;

        /**
         * Create a DefaultRule instnace with the given overlay factory
         * @param factory the {@link OverlayFactory} factory
         */
        public DefaultEnterRule(OverlayFactory factory) {
            this.factory = factory;
        }
        @Override
        public OverlayWidget onEnter(OverlayWidget widget) {
            return factory.getAmendableWidget(widget.getOverlayElement());
        }
    }

     /**
     * Implementation of {@link org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorEnterKeyPlugin.EnterRule}
     * by checking the type and namespace of the processed widget
     */
    public static class FromNameAndUriToWidgetEnterRule implements EnterRule {
        private OverlayWidget to;
        private String fromName;
        private String fromNamespaceUri;

        public FromNameAndUriToWidgetEnterRule(String fromName, String fromNamespaceUri, OverlayWidget to) {
            this.fromName = fromName;
            this.fromNamespaceUri = fromNamespaceUri;
            this.to = to;
        }

        /**
         * Check the type and namespace uri of the processed widget
         * @param toProcess {@link OverlayWidget} to be processed
         * @return <code>to</code> widget when <code>from</code> widget and <code>toProcess</code> widget have the
         * same type and namespace
         */
        @Override
        public OverlayWidget onEnter(OverlayWidget toProcess) {
            if (toProcess.getType().equals(fromName) &&
                    toProcess.getNamespaceURI().equals(fromNamespaceUri)) {
                return to;
            }
            return null;
        }
    }

    /** a list with available rules that need to be applied **/
    private List<EnterRule> enterRules = new ArrayList<EnterRule>();

    /** a list with widgets that need to be splitted **/
    private List<OverlayWidget> toBeSplitWidgets = new ArrayList<OverlayWidget>();

    private OverlayFactory overlayFactory;
    private OverlayWidget brWidget;

    /**
     * Create a plugin instance with the given {@link OverlayFactory}
     * @param overlayFactory The factory used to determine overlay widgets from {@link Element}
     * @param brWidget the overlay widget corresponding to <code>BR</code> html tag
     */
    @Inject
    public CKEditorEnterKeyPlugin(OverlayFactory overlayFactory, OverlayWidget brWidget) {
        this.overlayFactory = overlayFactory;
        this.brWidget = brWidget;
    }

    /**
     * Add a rule in the list of enter rules
     * @param enterRule The rule to be added
     */
    public void addEnterRule(EnterRule enterRule) {
        enterRules.add(enterRule);
    }

    public void addToBeSplitOnEnter(OverlayWidget widget) {
        toBeSplitWidgets.add(widget);
    }


    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(this, editor);
    }

    @Override
    public void afterInit(JavaScriptObject editor) {
        // not working well
        //nativeFilter(this, editor);
    }

    private native void nativeInit(final CKEditorEnterKeyPlugin keyPlugin, JavaScriptObject editor) /*-{
        editor.addCommand('shiftEnter', {
            modes: { wysiwyg: 1 },
            editorFocus: false,
            exec: function (editor) {
                enter(editor, 1);
            }
        });
        editor.addCommand('enter', {
            modes: { wysiwyg: 1 },
            editorFocus: false,
            exec: function (editor) {
                enter(editor, 2);
            }
        });

        var keystrokes = editor.keystrokeHandler.keystrokes;
        keystrokes[ 13 ] = 'enter';
        keystrokes[ $wnd.CKEDITOR.SHIFT + 13 ] = 'shiftEnter';

        $wnd.CKEDITOR.plugins.enterkey =
        {
            enterBlock: function (editor, range) {
                // Get the range for the current selection.
                range = range || getRange(editor);
                // We may not have valid ranges to work on, like when inside a
                // contenteditable=false element.
                if (!range)
                    return;
                // position type = -1 , insert an element before the parent element of the selection
                //identify the parent element of the selection
                var positionType = selectionPosition(editor);
                var ranges = editor.getSelection().getRanges();
                if (positionType == 0) {
                // if the container need to be splited, do it , otherwise introduce br
                    var container = ranges[0].startContainer;
                    while (container != null && container.type == $wnd.CKEDITOR.NODE_TEXT) {
                        container = container.getParent();
                    }
                    var toBeSplit = keyPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorEnterKeyPlugin::toBeSplit(Lcom/google/gwt/core/client/JavaScriptObject;)(container.$);
                    if (toBeSplit) {
                        //collapse the range
                        ranges[0].collapse(true);
                        ranges[0].splitElement(container);
                    } else {
                        enterBr(editor, range);
                    }

                // find start container and end container of the selection
                // if they are text nodes go to their parents
                } else if (positionType == -1) {
                    var startContainer = ranges[0].startContainer;
                    while (startContainer != null && startContainer.type == $wnd.CKEDITOR.NODE_TEXT) {
                        startContainer = startContainer.getParent();
                    }
                    var elemAsString = keyPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorEnterKeyPlugin::onEnter(Lcom/google/gwt/core/client/JavaScriptObject;)(startContainer.$);
                    if (elemAsString) {
                        var elem = $wnd.CKEDITOR.dom.element.createFromHtml(elemAsString);
                        // find the parent from rule
                        while (startContainer != null && (elem.getAttribute('type') != startContainer.getAttribute('type')
                                )) {
                            startContainer = startContainer.getParent();
                        }
                        if (startContainer) {
                            ranges[0].setStartBefore(startContainer);
                            ranges[0].insertNode(elem);
                        }
                    }
                } else {
                    var endContainer = ranges[ranges.length - 1].endContainer;
                    while (endContainer != null && endContainer.type == $wnd.CKEDITOR.NODE_TEXT) {
                        endContainer = endContainer.getParent();
                    }
                    var elemAsString = keyPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorEnterKeyPlugin::onEnter(Lcom/google/gwt/core/client/JavaScriptObject;)(endContainer.$);
                    if (elemAsString) {
                        var elem =  $wnd.CKEDITOR.dom.element.createFromHtml(elemAsString);
                        // find the parent from rule
                        while (endContainer != null) {
                            if ((elem.getAttribute('type') == endContainer.getAttribute('type')
                                    )) {
                                break;
                            }
                            endContainer = endContainer.getParent();
                        }
                        if (endContainer) {
                            ranges[ranges.length - 1].setStartAfter(endContainer);
                            ranges[ranges.length - 1].insertNode(elem);
                        }
                        var range = new $wnd.CKEDITOR.dom.range(range.document);
                        range.setStart(elem, 0);
                        range.setEnd(elem, 0);
                        editor.getSelection().selectRanges([range]);
                    }
                }
                return;
            },

            enterBr: function (editor, range) {
                // Get the range for the current selection.
                range = range || getRange(editor);
                // We may not have valid ranges to work on, like when inside a
                // contenteditable=false element.
                if (!range)
                    return;

                var doc = range.document;
                var elemAsString = keyPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorEnterKeyPlugin::brWidgetAsString()();
                var lineBreak = $wnd.CKEDITOR.dom.element.createFromHtml(elemAsString);
                range.deleteContents();
                range.insertNode(lineBreak);
                // This collapse guarantees the cursor will be blinking.
                range.collapse(false);
                range.select(false);
                return;
            }
        };

        var plugin = $wnd.CKEDITOR.plugins.enterkey,
                enterBr = plugin.enterBr,
                enterBlock = plugin.enterBlock;

        // for mode = 1 add a br for mode 2 in the middle of the text add br, at the beginning of the text or
        // at the end of the text add a new widget as the parent of the text node unless a rule is specified to
        // add a different type of widget
        function enter(editor, mode) {
            // Only effective within document.
            if (editor.mode != 'wysiwyg')
                return false;

            if (editor.getSelection().getType() != $wnd.CKEDITOR.SELECTION_TEXT) {
                return false;
            }

            if (!mode)
                mode = 1;

            // Use setTimout so the keys get cancelled immediately.
            setTimeout(function () {
                editor.fire('saveSnapshot');	// Save undo step.

                if (mode == 1) {
                    enterBr(editor, null);
                } else {
                    enterBlock(editor, null);
                }

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

        // positionType -1, at the beginning, 0 in the middle, 1 at the end
        function selectionPosition(editor) {
            // Get the selection ranges.
            var ranges = editor.getSelection().getRanges(true);
            if (ranges[0].startOffset == 0) {
                return -1;
            }
            if (ranges[0].startContainer.type == $wnd.CKEDITOR.NODE_TEXT) {
                var startContainerText = ranges[0].startContainer.getText();
                if (ranges[0].startOffset == startContainerText.length) {
                    return 1;
                }
            }

            return 0;
        }

    }-*/;

    /**
     * Add a filter transformation for html to data and viceversa for BR tags
     * @param keyPlugin The plugin instance
     * @param editor The editor instance
     */
    private native void nativeFilter(final CKEditorEnterKeyPlugin keyPlugin, JavaScriptObject editor) /*-{
        var dataProcessor = editor.dataProcessor,
                dataFilter = dataProcessor && dataProcessor.dataFilter,
                htmlFilter = dataProcessor && dataProcessor.htmlFilter;
//        // Add filter for html->data transformation.
        if (dataFilter) {
            dataFilter.addRules(
            {
                elements: {
                    'span': function( element ) {
                        if (element.attributes && element.attributes['type'] == "br") {
                            // Span is self closing element - change that.
                            //element.isEmpty = true;
//                            // Save original element name in data-saved-name attribute.
//                            element.attributes[ 'data-saved-type' ] = element.attributes['type'];
//                            element.attributes[ 'data-saved-ns' ] = element.attributes['ns'];
//                            element.attributes[ 'data-saved-class' ] = element.attributes['class'];
//                            // Change name to br.
                            element.name = 'br';
                            // Push zero width space, because empty span would be removed.
                            //element.children.push( new CKEDITOR.htmlParser.text( '\u200b' ) );
                        }
                    }
                }
             });
        }
//
//        // Add filter for data->html transformation.
        if (htmlFilter) {
            htmlFilter.addRules( {
                elements: {
                    'br': function( element ) {
                        element.isEmpty = true;
                        element.children = [];
                        element.attributes['type'] = 'br';
//                        element.attributes['ns']  = 'ns';
                        element.attributes['class'] = 'widget br';

//                        delete element.attributes[ 'data-saved-type' ];
//                        delete element.attributes[ 'data-saved-ns' ];
//                        delete element.attributes[ 'data-saved-class' ];

                        element.name = 'span';
                    }
                }
            });
        }
    }-*/;
    /**
     * Identify what type of element will be inserted when press enter over an existing element
     * @param existingElement
     * @return The new element that will be inserted
     */
    private String onEnter(JavaScriptObject existingElement) {
        Element el = existingElement.cast();
        OverlayWidget original = overlayFactory.getAmendableWidget(el);
        if (original == null) {
            return null;
        }
        //create a new one on the same type
        OverlayWidget from = overlayFactory.getAmendableWidget(original.getNamespaceURI(), original.getType());

        Element result = from.getOverlayElement();
        for (EnterRule enterRule : enterRules) {
            OverlayWidget widget = enterRule.onEnter(from);
            if (widget != null) {
                result = widget.getOverlayElement();
                break;
            }
        }
        result.setInnerText("\u200b");
        return DOM.toString((com.google.gwt.user.client.Element) result);
    }

    /**
     * Return String representation of brWidget
     * @return String
     */
    private String brWidgetAsString() {
        return DOM.toString((com.google.gwt.user.client.Element) brWidget.getOverlayElement());
    }

    /**
     * Returns true when the element was set up to be splitted
     * @param existingElement the element to be processed
     * @return True when the element was set up to be splitted;
     */
    private boolean toBeSplit(JavaScriptObject existingElement) {
        Element el = existingElement.cast();
        OverlayWidget original = overlayFactory.getAmendableWidget(el);
        if (original == null) {
            return false;
        }
        for(OverlayWidget widget : toBeSplitWidgets) {
            if (widget.getType().equals(original.getType())
                    && widget.getNamespaceURI().equals(original.getNamespaceURI())) {
                return true;
            }
        }

        return false;
    }
}
