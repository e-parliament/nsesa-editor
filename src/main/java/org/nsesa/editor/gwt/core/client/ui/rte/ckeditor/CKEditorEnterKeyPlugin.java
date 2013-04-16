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
import com.google.gwt.user.client.DOM;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.*;
import org.nsesa.editor.gwt.core.client.ui.rte.DefaultRichTextEditorPlugin;

import java.util.List;
import java.util.logging.Logger;

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

    private static final Logger LOG = Logger.getLogger(CKEditorEnterKeyPlugin.class.getName());

    /**
     * When you press enter over an given overlay widget it will be converted into another widget
     */
    public static interface ConversionEnterRule {
        abstract OverlayWidget convert(OverlayWidget widget);
    }

    /**
     * When you press enter over an given overlay widget it will be splitted if the rule allows.
     */
    public static interface SplitEnterRule {
        abstract boolean split(OverlayWidget widget);
    }

    /** split always the elements **/
    public static SplitEnterRule SPLIT_ALWAYS_ENTER_RULE = new SplitEnterRule() {
        @Override
        public boolean split(OverlayWidget widget) {
            return true;
        }
    };
    /**
     * Returns the line break as overlay widget by providing the parent overlay widget
     */
    public static interface LineBreakProvider {
        abstract OverlayWidget get(OverlayWidget parentWidget);
    }

    /**
     * Default implementation of line break provider by using the overlay factory
     * to get a representation of BR tag
     */
    public static class DefaultLineBreakProvider implements LineBreakProvider {
        private OverlayFactory overlayFactory;
        public DefaultLineBreakProvider(OverlayFactory overlayFactory) {
            this.overlayFactory = overlayFactory;
        }
        @Override
        public OverlayWidget get(OverlayWidget parentWidget) {
            return overlayFactory.getAmendableWidget(parentWidget.getNamespaceURI(), "br");
        }
    };

    /** conversion enter rule to be applied **/
    private ConversionEnterRule conversionEnterRule;
    /** split enter rule to be applied **/
    private SplitEnterRule splitEnterRule;

    /** snippet factory used to create new widget when press enter**/
    private OverlaySnippetFactory snippetFactory;
    /** split enter rule to be applied **/
    private LineBreakProvider lineBreakProvider;

    private OverlayFactory overlayFactory;

    /**
     * Create an instance with the given parameters
     * @param overlayFactory {@link OverlayFactory}
     * @param lineBreakProvider {@link LineBreakProvider}
     * @param splitEnterRule {@link SplitEnterRule}
     * @param conversionEnterRule {@link ConversionEnterRule}
     */
    @Inject
    public CKEditorEnterKeyPlugin(OverlayFactory overlayFactory,

                                  LineBreakProvider lineBreakProvider,
                                  SplitEnterRule splitEnterRule, ConversionEnterRule conversionEnterRule) {
        this.overlayFactory = overlayFactory;
        this.snippetFactory = snippetFactory;
        this.lineBreakProvider = lineBreakProvider;
        this.splitEnterRule = splitEnterRule;
        this.conversionEnterRule = conversionEnterRule;
    }

    public CKEditorEnterKeyPlugin(OverlayFactory overlayFactory, OverlaySnippetFactory overlaySnippetFactory) {
        this(overlayFactory,
                new DefaultLineBreakProvider(overlayFactory),
                SPLIT_ALWAYS_ENTER_RULE,
                null);
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
                    var toBeSplit = keyPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorEnterKeyPlugin::split(Lcom/google/gwt/core/client/JavaScriptObject;)(container.$);
                    if (toBeSplit) {
                        //collapse the range
                        ranges[0].collapse(true);
                        var elem = ranges[0].splitElement(container);
                        var range = new $wnd.CKEDITOR.dom.range(ranges[0].document);
                        range.setStart(elem, 0);
                        range.setEnd(elem, 0);
                        editor.getSelection().selectRanges([range]);

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
                    var elemAsString = keyPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorEnterKeyPlugin::onEnter(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(startContainer.$, editor);
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
                    var elemAsString = keyPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorEnterKeyPlugin::onEnter(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(endContainer.$, editor);
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
                var container = range.startContainer;
                while (container != null && container.type == $wnd.CKEDITOR.NODE_TEXT) {
                    container = container.getParent();
                }

                var lineBreak = keyPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorEnterKeyPlugin::getLineBreak(Lcom/google/gwt/core/client/JavaScriptObject;)(container.$);
                if (lineBreak) {
                    var brNode = $wnd.CKEDITOR.dom.element.createFromHtml(lineBreak);
                    range.deleteContents();
                    range.insertNode(brNode);
                    // This collapse guarantees the cursor will be blinking.
                    range.collapse(false);
                    range.select(false);
                }
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
     * Identify what type of element will be inserted when press enter over an existing element
     * @param existingElement
     * @return The new element that will be inserted
     */
    private String onEnter(JavaScriptObject existingElement, JavaScriptObject editor) {
        List<OverlayWidget> roots = overlayEditorBody(editor, overlayFactory);
        Element el = existingElement.cast();
        OverlayWidget original = findOverlayWidget(el, roots);

        OverlayWidget newWidget =  null;
        if (conversionEnterRule != null) {
            //fill in the ancestors for this widget
            newWidget = conversionEnterRule.convert(original);
        } else {
            //create a new one on the same type
            newWidget = overlayFactory.getAmendableWidget(original.getNamespaceURI(), original.getType());
            if (newWidget != null) {
                newWidget.getOverlayElement().setInnerText(EMPTY_CHAR);
            }
        }

        return newWidget == null ? null :  DOM.toString((com.google.gwt.user.client.Element) newWidget.getOverlayElement());
    }

    /**
     * Return String representation of brWidget
     * @return String
     */
    private String getLineBreak(JavaScriptObject container) {
        Element el = container.cast();
        OverlayWidget original = overlayFactory.getAmendableWidget(el);
        OverlayWidget lineBreak = lineBreakProvider.get(original);
        if (lineBreak == null) {
            LOG.severe("The line break can not be retrieved for namespace " +
                    (original == null ? "null" : original.getNamespaceURI()));
            return null;
        }
        return DOM.toString((com.google.gwt.user.client.Element) lineBreak.getOverlayElement());
    }

    /**
     * Returns true when the element was set up to be splitted
     * @param existingElement the element to be processed
     * @return True when the element was set up to be splitted;
     */
    private boolean split(JavaScriptObject existingElement) {
        Element el = existingElement.cast();
        OverlayWidget original = overlayFactory.getAmendableWidget(el);
        return (original == null) ? false : splitEnterRule.split(original);
    }


}
