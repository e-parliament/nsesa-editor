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
 * A Ck editor plugin which raise GWT application event whenever the user move the cursor
 * or make a selection
 * User: groza
 * Date: 17/01/13
 * Time: 9:38
 */
public class CKEditorSelectionChangedPlugin implements RichTextEditorPlugin {

    private static final Logger LOG = Logger.getLogger(CKEditorSelectionChangedPlugin.class.getName());

    private ClientFactory clientFactory;

    @Inject
    public CKEditorSelectionChangedPlugin(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public String getName() {
        return "nsesa-selectionChanged";
    }

    @Override
    public void beforeInit(JavaScriptObject editor) {
        //do nothing
    }

    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(editor, this);
    }


    @Override
    public void export(RichTextEditorConfig config) {
        //do nothing
    }

    private native void nativeInit(final JavaScriptObject editor, CKEditorSelectionChangedPlugin plugin) /*-{
        var keyHandler, mouseUpListener, mouseDownListener, keyUpListener;

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

    private void fireEvent(JavaScriptObject jsObject, boolean moreTagsSelected, String selectedText) {
        LOG.info("Changed event fired with more tags selected: " + moreTagsSelected + " and selected text " + selectedText);
        Element el = jsObject.cast();
        clientFactory.getEventBus().fireEvent(new SelectionChangedEvent(el, moreTagsSelected, selectedText));
    }

}
