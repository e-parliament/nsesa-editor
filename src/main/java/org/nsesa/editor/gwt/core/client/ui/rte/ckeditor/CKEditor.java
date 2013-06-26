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

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

/**
 * An implementation of <code>RichTextEditor</code> interface based on CK editor. The layout of this implementation
 * is based on DockLayoutPanel class where the CKEditor JavaScriptObject representation is added in the middle of
 * the panel. The draft tool widget is added at north while draft attributes widget is positioned at south.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 11/01/13 16:24
 *
 * @see <a href="http://ckeditor.com">CK Editor</a> for more details
 */
public class CKEditor extends Composite implements RichTextEditor {
    /**
     * Keep a count of editor instances
     */
    private static int counter = 1;

    /**
     *  whether or not the editor is attached to dom
     */
    private boolean attached;

    /**
     * used to set up the content data whilst the editor is initializing
     */
    private String temporaryContent;

    /**
     *  identify uniquely the editor when there are more instances created
     */
    private String id;

    /**
     * javascript object representation of this editor
     */
    private JavaScriptObject editorInstance;

    /**
     * textarea used to attach the CK editor instance
     */
    private final TextArea textArea = new TextArea();

    /**
     * CK editor plugin to expose editor functionality
     */
    private RichTextEditorPlugin plugin;

    /**
     * the CK editor configuration
     */
    private RichTextEditorConfig config;

    /**
     * show/hide the drafting tool and attributes widgets inside of the editor
     */
    private boolean showDraftingTool;

    /**
     *  the main panel for ck editor
     */
    private DockLayoutPanel mainPanel = new DockLayoutPanel(Style.Unit.PX);

    /**
     * the holder for drafting tool widget
     */
    private FlowPanel draftHolderPanel = new FlowPanel();

    /**
     * the holder for attributes widget
     */
    private FlowPanel attributesHolderPanel = new FlowPanel();

    /**
     * keep the height of the editor to be used when resizing the editor
     */
    private int height;

    /**
     * keep the toggle status
     */
    private boolean toggled;

    /**
     * keep a reference to the widget over which the editor is operating
     */
    private OverlayWidget overlayWidget;

    /**
     * Create an instance of the editor.
     * @param plugin The plugin linked to the editor which
     * @param config The editor configuration
     * @param showDraftingTool Show/hide the drafting tool and attributes widgets
     */
    public CKEditor(RichTextEditorPlugin plugin, RichTextEditorConfig config, boolean showDraftingTool) {
        this.plugin = plugin;
        this.config = config;
        this.showDraftingTool = showDraftingTool;

        this.id = "ckEditor" + counter++;
        textArea.getElement().setId(this.id);
        if (showDraftingTool) {
            mainPanel.addEast(draftHolderPanel, 0);
            mainPanel.addSouth(attributesHolderPanel, 0);
        }
        mainPanel.add(textArea);
        initWidget(mainPanel);
        if (!GWT.isScript())
            mainPanel.setTitle(this.getClass().getName());

        mainPanel.setWidth("100%");
        mainPanel.setHeight("100%");
        textArea.setWidth("100%");
        textArea.setHeight("100%");
        textArea.setName(this.id + "-textArea");
    }

    /**
     * Initialize the editor by creating a new JavaScriptObject representation of CKEditor.
     * If there is any plugin attached to the editor it will be exported to JavaScript world.
     */
    @Override
    public void init() {
        if (!attached) {

            this.height = textArea.getOffsetHeight() + (config.isReadOnly() ? -21 : -55);
            config.setHeight(this.height);

            editorInstance = getEditor(config.getConfiguration(), this.id, temporaryContent);
            if (editorInstance == null) {
                throw new NullPointerException("Editor instance not created!");
            }
            setBodyNamespaceURI();
            if (!isAttached()) onAttach();
            attached = true;
        }
    }


    @Override
    public void destroy() {
        destroy(editorInstance);
        editorInstance = null;
        attached = false;
    }

    @Override
    public void setFocus(boolean focus) {
        if (focus) {
            setFocusNative(editorInstance);
        }
        else {
            setBlurNative(editorInstance);
        }
    }

    @Override
    public void setVisualStructureWidget(IsWidget widget) {
        draftHolderPanel.add(widget);
    }

    @Override
    public void setVisualStructureAttributesWidget(IsWidget widget) {
        attributesHolderPanel.add(widget);
    }

    /**
     * Setup the size of the draft tool holder panel according with the value of <code>toggled</code> param.
     * @param toggled When true the drafting tool widget is shown
     */
    @Override
    public void toggleVisualStructure(boolean toggled) {
        //toggle the view when the editor is attached to DOM
        this.toggled = toggled;

        if (isAttached()) {
            //the flag to show drafting tool has been set up
            if (showDraftingTool) {
                // TODO: only do this if there actually is a draft visualStructureChildPanel set
                final Widget visualStructureChildPanel = draftHolderPanel.getWidgetCount() > 0 ? draftHolderPanel.getWidget(0) : null;
                if (visualStructureChildPanel != null)
                    mainPanel.setWidgetSize(draftHolderPanel, toggled ? 100 : 0);
            }
            if (toggled) {
                // add drafting css class to editor instance
                addBodyClassName(editorInstance, config.getDraftingClassName());
            } else {
                // remove drafting css class to editor instance
                removeBodyClassName(editorInstance, config.getDraftingClassName());
            }
        }
    }

    @Override
    public void executeCommand(final String command, int delay) {
        Timer timer = new Timer() {
            @Override
            public void run() {
                executeCommand(editorInstance, command);
            }
        };
        timer.schedule(delay);
    }
    /**
     * Setup the size of the drafting attributes tool holder panel according with the value of <code>toggled</code> param.
     * @param toggled When true the drafting attributes tool widget is shown
     */
    @Override
    public void toggleVisualStructureAttributes(boolean toggled) {
        //toggle the view when the editor is attached to DOM
        if (isAttached()) {
            if (showDraftingTool) {
                mainPanel.setWidgetSize(attributesHolderPanel, toggled ? 100 : 0);
                //resize the height of the editor by using the original height saved on initialization
                resize("100%", (toggled ? (height - 100) + "" : height +""));
            }
        }
    }

    @Override
    public void resize(String width, String height) {
        resize(editorInstance, width, height);
    }

    @Override
    public CaretPosition getCaretPosition() {
        CaretPosition caretPosition = new CaretPosition();
        nativeCaretPosition(caretPosition, editorInstance);
        caretPosition.setLeft(caretPosition.getLeft() + this.getAbsoluteLeft());
        caretPosition.setTop(caretPosition.getTop() + this.getAbsoluteTop());
        return caretPosition;
    }

    public native void setFocusNative(JavaScriptObject editorInstance) /*-{
        if (editorInstance != null) editorInstance.focus();
    }-*/;

    public native void setBlurNative(JavaScriptObject editorInstance) /*-{

    }-*/;

    public native void destroy(JavaScriptObject editorInstance) /*-{
        if (editorInstance != null) editorInstance.destroy();
    }-*/;

    private native void addBodyClassName(JavaScriptObject editorInstance, String className) /*-{
        if (editorInstance != null && editorInstance.document != null && editorInstance.document.getBody() != null)
            editorInstance.document.getBody().addClass(className);
    }-*/;

    /**
     * return the editor instance body as element
     * @param editorInstance The editor instance processed
     * @return The body element as JavaScriptObject
     */
    private native JavaScriptObject getBodyElement(JavaScriptObject editorInstance) /*-{
        if (editorInstance && editorInstance.document) {
            return editorInstance.document.$.body;
        }

        return null;
    }-*/;

    /**
     * return the editor window element
     * @param editorInstance The editor instance processed
     * @return The body element as JavaScriptObject
     */
    private native JavaScriptObject getWindowElement(JavaScriptObject editorInstance) /*-{
        if (editorInstance && editorInstance.document) {
            return editorInstance.document.getWindow().$;
        }

        return null;
    }-*/;

    private native void removeBodyClassName(JavaScriptObject editorInstance, String className) /*-{
        if (editorInstance != null && editorInstance.document != null && editorInstance.document.getBody() != null)
            editorInstance.document.getBody().removeClass(className);
    }-*/;

    private native void executeCommand(JavaScriptObject editorInstance, String cmdName) /*-{
        if (editorInstance != null && editorInstance.getCommand(cmdName) != null) {
            editorInstance.getCommand(cmdName).exec();
        }
    }-*/;

    private native void resize(JavaScriptObject editorInstance, String width, String height) /*-{
        try{
            editorInstance.resize(width, height, true);
        } catch(e){
            // ignore - strange exception being thrown in native ckeditor.js
        }
    }-*/;

    private native JavaScriptObject getEditor(JavaScriptObject instanceConfig, Object elementID, String content) /*-{
        var editor = $wnd.CKEDITOR.replace(elementID, instanceConfig, content);
        return editor;
    }-*/;


    @Override
    public void setOverlayWidget(OverlayWidget overlayWidget) {
        this.overlayWidget = overlayWidget;
        config.resetBodyClass();
        config.addBodyClass(overlayWidget.getType());
        config.setBodyNamespaceURI(overlayWidget.getNamespaceURI());
    }

    @Override
    public void setHTML(final String content) {
        //creating a javaScriptObject editor representation might be a time consuming operation, keep the content data
        // that need to be set up also in a temporary variable
        setTemporaryContent(content);
        if (attached) {
            setHTMLInternal(editorInstance, content);
            if (toggled) {
                //force it again since the editor loose the setting when set up the content
                addBodyClassName(editorInstance, config.getDraftingClassName());
            }
        }
    }

    private native void setHTMLInternal(final JavaScriptObject editorInstance, final String content) /*-{
        editorInstance.setData(content);
    }-*/;

    @Override
    public String getHTML() {
        if (!attached) {
            return temporaryContent;
        } else
            return getHTMLInternal(editorInstance);
    }

    private native String getHTMLInternal(final JavaScriptObject javaScriptObject) /*-{
        return javaScriptObject.getData();
    }-*/;

    private void setTemporaryContent(String content) {
        this.textArea.setText(content);
        this.temporaryContent = content;
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        destroy();
        //otherwise the resize functionality will not work fine as soon as you reinitialize the editor
        if (showDraftingTool) {
            mainPanel.setWidgetSize(attributesHolderPanel, 0);
        }
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        // we cannot call init() directly because it will cause IE < 9 to choke
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                init();
            }
        });
    }

    @Override
    public void addBodyClass(String className) {
        this.config.addBodyClass(className);
    }

    @Override
    public void resetBodyClass() {
        config.resetBodyClass();
    }


    private void setBodyNamespaceURI() {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                final String ns = overlayWidget != null ? overlayWidget.getNamespaceURI() : config.getBodyNamespaceURI();
                nativeSetBodyNamespaceURI(editorInstance, ns);
            }
        });
    }
    private native void nativeSetBodyNamespaceURI(JavaScriptObject editor, String ns) /*-{
        editor.on('mode', function (ev) {
            var editorInstance = ev.editor;
            if (editorInstance && editorInstance.document) {
                editorInstance.document.getBody().setAttribute("ns", ns);
            }
        })
    }-*/;
    /**
     * Computes the caret position by introducing a fake img and summing the offsets
     * @param caretPosition  {@link CaretPosition}
     * @param editorInstance The editor instance as {@link JavaScriptObject}
     */
    private native void nativeCaretPosition(CaretPosition caretPosition, JavaScriptObject editorInstance) /*-{
        if (editorInstance.document) {
            var lastSnapshot = editorInstance.getSnapshot();
            var dummyElement = editorInstance.document.createElement('img', {
                attributes: {
                    src : 'null',
                    width : 0,
                    height : 0
                }
            });
            var range;
            if (editorInstance.getSelection() && editorInstance.getSelection().getRanges()) {
                range = editorInstance.getSelection().getRanges()[0];
                range.insertNode(dummyElement);
            } else {
                editorInstance.insertElement(dummyElement);
            }
            var obj = dummyElement.$;
            var cursor = {left : 0, top :0};
            cursor.keydown = false;
            while (obj.offsetParent) {
                cursor.left += obj.offsetLeft;
                cursor.top += obj.offsetTop;
                obj = obj.offsetParent;
            }
            cursor.left += obj.offsetLeft;
            cursor.top += obj.offsetTop;
            cursor.keydown = true;
            dummyElement.remove();

            caretPosition.@org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor.CaretPosition::setLeft(I)(cursor.left);
            caretPosition.@org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor.CaretPosition::setTop(I)(cursor.top);
        }
    }-*/;


}
