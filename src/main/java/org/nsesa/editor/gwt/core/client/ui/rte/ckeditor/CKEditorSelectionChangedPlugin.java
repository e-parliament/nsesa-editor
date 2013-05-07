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
import com.google.gwt.user.client.Element;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.visualstructure.VisualStructureSelectionChangedEvent;
import org.nsesa.editor.gwt.core.client.ui.rte.DefaultRichTextEditorPlugin;

import java.util.logging.Logger;

/**
 * CK editor plugin which raise <code>VisualStructureSelectionChangedEvent</code>GWT application event whenever the user move
 * the cursor, press a key or make a selection in the editor area. The event is propagated further through
 * event bus and can be handled by different controllers like <code>VisualStructureController</code>
 * ({@link org.nsesa.editor.gwt.core.client.ui.visualstructure.VisualStructureController})
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 17/01/13 9:38
 */
public class CKEditorSelectionChangedPlugin extends DefaultRichTextEditorPlugin {
    /**
     * The Logger
     */
    private static final Logger LOG = Logger.getLogger(CKEditorSelectionChangedPlugin.class.getName());

    /**
     * The Client factory
     */
    private ClientFactory clientFactory;

    // keep reference to previous selected element
    private Element previousElement;
    private boolean previousMoreTagsSelected;
    private String previousSelectedText = "";

    /**
     * Create <code>CKEditorSelectionChangedPlugin</code> object with the given <code>clientFactory</code>
     * @param clientFactory {@link ClientFactory} used to fire GWT events through its
     *                                           reference to {@link org.nsesa.editor.gwt.core.client.ClientFactory#getEventBus()}
     */
    @Inject
    public CKEditorSelectionChangedPlugin(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }


    /**
     * Catch any  mouseup, mousedown or keypress browser events.
     * In such cases raise a <code>VisualStructureSelectionChangedEvent</code> and pass further the start element whereas
     * the selection occurs, among with the selected text from the editor area and a flag to see whether more
     * tag elements have been included in the selection.
     * @param editor The Rich Text editor as JavaScriptObject
     */
    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(editor, this);
    }

    /**
     * Catch any mouseup, mousedown or keypress browser events.
     * In such cases raise a <code>VisualStructureSelectionChangedEvent</code> and pass further the start element whereas
     * the selection occurs, among with the selected text from the editor area and a flag to see whether more
     * tag elements have been included in the selection
     * @param editor  The JavaScriptObject editor instance
     * @param plugin  The CKEditorSelectionChangedPlugin instance
     */
    private native void nativeInit(final JavaScriptObject editor, CKEditorSelectionChangedPlugin plugin) /*-{
        var keyHandler, mouseUpListener, mouseDownListener, keyUpListener;

        //handle the selection when mouseup, mouse down, keypress
        function selectionHandle(ed) {
            if (ed.document) {
                ed.document.on('mouseup', function (ev) {
                    if (ed.getSelection())
                        checkSelection(ed.getSelection(), ed);
                })
                ed.document.on('mousedown', function (ev) {
                    //remove the ranges
                    if (ed.getSelection()) {
                        //editor.getSelection().removeAllRanges();
                        var range = new $wnd.CKEDITOR.dom.range(ed.document);
                        range.setStart(ed.document.getBody(), 0);
                        range.setEnd(ed.document.getBody(), 0);
                        ed.getSelection().selectRanges([range]);
                    }
                })
                ed.document.on('keyup', function (ev) {
                    $wnd.clearTimeout(keyHandler);
                    keyHandler = $wnd.setTimeout(function () {
                        checkSelection(ed.getSelection(), ed);
                    }, 500)
                })
            }
            ;

        }

        // check the selection and fire an SelectionChangedEvent to be handled by the other components
        function checkSelection(selection, ed) {
            var env = $wnd.CKEDITOR.env,
                element = selection.getStartElement(),
                html = [],
                moreTagsSelected = false,
                selectedText = selection.getSelectedText();
            //keep a track of the last visited element
            var previousElement = ed["cachedElement"];
            ed["cachedElement"] = element;

            moreTagsSelected = selectMoreTags(selection);
            if (true) {
                plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorSelectionChangedPlugin::fireEvent(Lcom/google/gwt/core/client/JavaScriptObject;ZLjava/lang/String;)(element.$, moreTagsSelected, selectedText);
            }
        }

        //check the number of DOM elements enclosed into the selection
        function selectMoreTags(selection) {
            if (selection.getType == $wnd.CKEDITOR.SELECTION_TEXT) {
                return false;
            }
            var ranges = selection.getRanges();
            // find start container and end container of the selection
            // if they are text nodes go to their parents
            var startContainer = ranges[0].startContainer;
            while (startContainer != null && startContainer.type == $wnd.CKEDITOR.NODE_TEXT) {
                startContainer = startContainer.getParent();
            }
            var endContainer = ranges[ranges.length - 1].endContainer;
            while (endContainer != null && endContainer.type == $wnd.CKEDITOR.NODE_TEXT) {
                endContainer = endContainer.getParent();
            }
            return !startContainer.equals(endContainer);
        }
        //check to see whether the selection has been changed by comparing start element and end element
        function selectionChanged(selection, ed) {
            var ranges = selection.getRanges();
            // find start container and end container of the selection
            // if they are text nodes go to their parents
            var startContainer = selection.getStartElement();
            var startOffSet = ranges[0].startOffset;
            var endContainer = ranges[ranges.length - 1].endContainer;
            var endOffSet = ranges[ranges.length - 1].endOffset;
            var previousStart = ed["cachedElement"];
            var previousStartOffSet = ed["cachedStartOffSet"];
            var previousEnd = ed["cachedEndElement"];
            var previousEndOffSet = ed["cachedEndOffSet"];

            ed["cachedElement"] = startContainer;
            ed["cachedEndElement"] = endContainer;
            ed["cachedStartOffSet"] = startOffSet;
            ed["cachedEndOffSet"] = endOffSet;
            return startContainer !== previousStart || !endContainer.$.isEqualNode(previousEnd ? previousEnd.$ : null) || startOffSet !== previousStartOffSet || endOffSet !== previousEndOffSet;
        }

        editor.on('mode', function () {
            // reapply the listeners if they exists, since they are removed when switching back and forth with the
            //source representation of the editor
            if (editor.mode != 'source') {
                selectionHandle(editor);
            }
        });


    }-*/;

    /**
     * A convenient way to fire an event from java code
     * @param jsObject The parent element
     * @param moreTagsSelected True when more tag elements have been selected
     * @param selectedText The selected text from the editor area
     */
    private void fireEvent(JavaScriptObject jsObject, boolean moreTagsSelected, String selectedText) {
        LOG.info("Changed event fired with more tags selected: " + moreTagsSelected + " and selected text " + selectedText);
        Element el = jsObject.cast();
        //raise the event only when something has been changed
        if (el != null) {
            if (!el.toString().equalsIgnoreCase(previousElement != null ? previousElement.toString() : null) ||
                    moreTagsSelected != previousMoreTagsSelected ||
                    !selectedText.equalsIgnoreCase(previousSelectedText)) {

                clientFactory.getEventBus().fireEvent(new VisualStructureSelectionChangedEvent(el, moreTagsSelected, selectedText));
            }
        }
        previousElement = el;
        previousMoreTagsSelected = moreTagsSelected;
        previousSelectedText = selectedText;

    }

}
