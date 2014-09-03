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
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event;
import org.nsesa.editor.gwt.core.client.ui.rte.DefaultRichTextEditorPlugin;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

/**
 * Since CKEditor is based on iframe which do not buddle up key/mouse events to the parent,
 * this plugin will bubble those events further to be then treated by the application listeners.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 5/04/13 8:06
 */
public class CKEditorBubbleUpEventsPlugin extends DefaultRichTextEditorPlugin {
    @Override
    public void beforeInit(JavaScriptObject editor) {
    }

    /**
     * Bubble up key up event to the parent document
     * @param editor The Rich Text editor as JavaScriptObject
     */
    @Override
    public void init(JavaScriptObject editor) {
        nativeInit(editor, this);
    }

    private native void nativeInit(JavaScriptObject editor, CKEditorBubbleUpEventsPlugin editorPlugin) /*-{
        editor.on('mode', function () {
            // reapply the listeners if they exists, since they are removed when switching back and forth with the
            //source representation of the editor
            if (editor.mode != 'source') {
                editor.document.on('keyup', function (ev) {
                    var keyHandler = {ctrlKey : ev.data.$.ctrlKey, altKey : ev.data.$.altKey,
                        shiftKey : ev.data.$.shiftKey,
                        metaKey : ev.data.$.metaKey,
                        keyCode : ev.data.$.keyCode || ev.data.$.which};
                    editorPlugin.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorBubbleUpEventsPlugin::bubbleUpKeyEvent(Lcom/google/gwt/core/client/JavaScriptObject;)(keyHandler);
                })
            }
        });

    }-*/;

    @Override
    public void afterInit(JavaScriptObject editor) {

    }

    /**
     * Fire a new key up event
     * @param jso {@link JavaScriptObject} representation of {@link KeyHandler}
     */
    private void bubbleUpKeyEvent(JavaScriptObject jso) {
        KeyHandler keyHandler = jso.cast();
        NativeEvent nativeEvent = Document.get().createKeyUpEvent(keyHandler.isCtrlKey(),
                keyHandler.isAltKey(),
                keyHandler.isShiftKey(),
                keyHandler.isMetaKey(),
                keyHandler.getKeyCode());

        Event.fireNativePreviewEvent(nativeEvent);

    }

    /**
     * GWT Overlay type representation for key
     */
    public static class KeyHandler extends JavaScriptObject {
        protected KeyHandler() {
        }

        public final native boolean isCtrlKey() /*-{return this.ctrlKey}-*/;
        public final native boolean isAltKey() /*-{return this.altKey}-*/;
        public final native boolean isShiftKey() /*-{return this.shiftKey}-*/;
        public final native boolean isMetaKey() /*-{return this.metaKey}-*/;
        public final native int getKeyCode() /*-{return this.keyCode}-*/;

    }
}
