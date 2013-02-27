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
import com.google.gwt.user.client.Element;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.drafting.SelectionChangedEvent;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

import java.util.logging.Logger;

/**
 * CK editor plugin which raise <code>SelectionChangedEvent</code>GWT application event whenever the user move
 * the cursor, press a key or make a selection in the editor area. The event is propagated further through
 * event bus and can be handled by different controllers like <code>DraftingController</code>
 * ({@link org.nsesa.editor.gwt.core.client.ui.drafting.DraftingController})
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 17/01/13 9:38
 */
public class CKEditorSelectionChangedPlugin implements RichTextEditorPlugin {
    /**
     * The Logger
     */
    private static final Logger LOG = Logger.getLogger(CKEditorSelectionChangedPlugin.class.getName());

    /**
     * The Client factory
     */
    private ClientFactory clientFactory;

    @Inject
    public CKEditorSelectionChangedPlugin(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public String getName() {
        return "nsesa-selectionChanged";
    }

    /**
     * No before init operation performed
     * @param editor The Rich Text editor as JavaScriptObject
     */
    @Override
    public void beforeInit(JavaScriptObject editor) {
        //do nothing
    }

    /**
     * Catch any  mouseup, mousedown or keypress browser events.
     * In such cases raise a <code>SelectionChangedEvent</code> and pass further the start element whereas
     * the selection occurs, among with the selected text from the editor area and a flag to see whether more
     * tag elements have been included in the selection.
     * @param editor The Rich Text editor as JavaScriptObject
     */
    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(editor, this);
    }

    /**
     * No export operation performed
     * @param config The Rich Text editor configuration as JavaScriptObject
     */
    @Override
    public void export(RichTextEditorConfig config) {
        //do nothing
    }

    /**
     * Catch any mouseup, mousedown or keypress browser events.
     * In such cases raise a <code>SelectionChangedEvent</code> and pass further the start element whereas
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

        function checkSelection(selection, ed) {
            var env = $wnd.CKEDITOR.env,
                element = selection.getStartElement(),
                html = [],
                parentTagType,
                nameSpace,
                moreTagsSelected = false,
                selectedText = selection.getSelectedText();
            //keep a track of the last visited element
            ed["cachedElement"] = element;
            parentTagType = element.getAttribute('type');
            nameSpace = element.getAttribute('ns');

            moreTagsSelected = selectMoreTags(selection);
            //if (parentTagType) {
            plugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorSelectionChangedPlugin::fireEvent(Lcom/google/gwt/core/client/JavaScriptObject;ZLjava/lang/String;)(element.$, moreTagsSelected, selectedText);
            //
        }

        function selectMoreTags(selection) {
            if (selection.getType == $wnd.CKEDITOR.SELECTION_TEXT) {
                return false;
            }
            var ranges = selection.getRanges();
            return !ranges[0].startContainer.equals(ranges[ranges.length - 1].endContainer);
        }

        editor.on('mode', function () {
            // get the listeners if they exists
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
        clientFactory.getEventBus().fireEvent(new SelectionChangedEvent(el, moreTagsSelected, selectedText));
    }

}
