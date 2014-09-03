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
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.widget.OverlayWidgetModifyEvent;
import org.nsesa.editor.gwt.core.client.event.widget.OverlayWidgetStructureChangeEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.*;
import org.nsesa.editor.gwt.core.client.ui.rte.DefaultRichTextEditorPlugin;
import org.nsesa.editor.gwt.core.client.ui.rte.event.RTEStructureChangedEvent;
import org.nsesa.editor.gwt.core.client.util.OverlayUtil;

import java.util.List;
import java.util.logging.Logger;

/**
 * A plugin to handle enter and shift enter keys.
 * <p>Inside the rte editor the text can be displayed in three different  ways:
 * - "text" mode : corresponds to "wysiwyg" ck editor mode,
 * - "visual structure" mode : the text is decorated with css before/after tags.
 * - "source" mode: corresponds to "source" ck editor mode.
 *
 * The following rules are applied when the user press enter key:
 * a. in the middle of the text node check if the conversion rule returns an overlay widget. If yes then add that
 * widget in the text. If no just add BR tag (keep in mind the BR tag is retrieved by using the overlay factory
 * corresponding to the parent container).
 * b. at the end of the next node apply same logic specified at point a)
 * c. at the beginning of the next node apply same logic specified at point a)
 *
 * For shift enter just add html custom br inside of the text.
 *
 * Remark: The Br tag is not added if the text is displayed in "source" mode.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
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
    }

    /** conversion enter rule to be applied **/
    private ConversionEnterRule conversionEnterRule;
    /** split enter rule to be applied **/
    private SplitEnterRule splitEnterRule;
    /** split enter rule to be applied **/
    private LineBreakProvider lineBreakProvider;

    private ClientFactory clientFactory;

    private OverlayFactory overlayFactory;

    /**
     * Create an instance with the given parameters
     * @param overlayFactory {@link OverlayFactory}
     * @param clientFactory {@link org.nsesa.editor.gwt.core.client.ClientFactory}
     * @param lineBreakProvider {@link LineBreakProvider}
     * @param splitEnterRule {@link SplitEnterRule}
     * @param conversionEnterRule {@link ConversionEnterRule}
     */
    @Inject
    public CKEditorEnterKeyPlugin(OverlayFactory overlayFactory,
                                  ClientFactory clientFactory,
                                  LineBreakProvider lineBreakProvider,
                                  SplitEnterRule splitEnterRule,
                                  ConversionEnterRule conversionEnterRule) {

        this.overlayFactory = overlayFactory;
        this.lineBreakProvider = lineBreakProvider;
        this.splitEnterRule = splitEnterRule;
        this.conversionEnterRule = conversionEnterRule;
        this.clientFactory = clientFactory;
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
//                $wnd.console.log("Position " + positionType);
                var ranges = editor.getSelection().getRanges();
//                $wnd.console.log("Ranges: ", ranges);

                var endContainer = ranges[ranges.length - 1].endContainer;
                while (endContainer != null && endContainer.type == $wnd.CKEDITOR.NODE_TEXT) {
                    endContainer = endContainer.getParent();
                }
                var elemAsString = keyPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorEnterKeyPlugin::onEnter(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(endContainer.$, editor);
                if (elemAsString) {
                    var elem = $wnd.CKEDITOR.dom.element.createFromHtml(elemAsString);
                    // find the parent from rule
                    while (endContainer != null) {
                        if ((elem.getAttribute('data-type') == endContainer.getAttribute('data-type')
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
                    elem.scrollIntoView(false);
                    keyPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorEnterKeyPlugin::notifyModification(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(container.$, editor);
                }

                editor.fire('caretPosition');
            },

            enterBr: function (editor, range) {
                // Get the range for the current selection.
                range = range || getRange(editor);
                // We may not have valid ranges to work on, like when inside a
                // contenteditable=false element.
                if (!range)
                    return;
                var container = range.startContainer;
                $wnd.console.log("->", container);
                while (container && container.type == $wnd.CKEDITOR.NODE_TEXT) {
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

            // Use setTimeout so the keys get cancelled immediately.
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
        Element el = existingElement.cast();
        OverlayWidget original = findOverlayWidget(el, getEditorBodyAsOverlay(editor, overlayFactory));

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

    private void notifyModification(JavaScriptObject existingElement, JavaScriptObject editor) {
        Element el = existingElement.cast();
        final OverlayWidget overlayWidget = findOverlayWidget(el, getEditorBodyAsOverlay(editor, overlayFactory));
        if (overlayWidget != null && overlayWidget.getParentOverlayWidget() != null) {
            clientFactory.getEventBus().fireEvent(new RTEStructureChangedEvent(overlayWidget.getParentOverlayWidget()));
        }
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
     * Returns true when the element was set up to be split
     * @param existingElement the element to be processed
     * @return True when the element was set up to be split;
     */
    private boolean split(JavaScriptObject existingElement) {
        Element el = existingElement.cast();
        OverlayWidget original = overlayFactory.getAmendableWidget(el);
        return (original == null) ? false : splitEnterRule.split(original);
    }


}
