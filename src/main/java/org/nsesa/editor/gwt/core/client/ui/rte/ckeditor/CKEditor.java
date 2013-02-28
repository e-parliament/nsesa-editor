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
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;

/**
 * Date: 04/12/12 13:19
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class CKEditor extends Composite implements RichTextEditor {

    private static int counter = 1;

    private boolean attached;

    private String temporaryContent;
    private String id;

    private JavaScriptObject editorInstance;

    private final TextArea textArea = new TextArea();
    // the CK wrapper plugin
    private RichTextEditorPlugin plugin;
    // the CK editor configuration
    private RichTextEditorConfig config;
    private boolean showDraftingTool;

    //the main panel for ck editor
    private DockLayoutPanel mainPanel = new DockLayoutPanel(Style.Unit.PX);
    // the holder for drafting tool
    private FlowPanel draftHolderPanel = new FlowPanel();
    // the holder for attributes
    private FlowPanel attributesHolderPanel = new FlowPanel();

    private int height;

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
        mainPanel.setWidth("100%");
        mainPanel.setHeight("100%");
        textArea.setWidth("100%");
        textArea.setHeight("100%");
    }

    @Override
    public void init() {
        if (!attached) {
            //export the plugin
            if (plugin != null) {
                plugin.export(config);
            }
            this.height = textArea.getOffsetHeight() + (config.isReadOnly() ? -21 : -55);
            config.setHeight(this.height);

            editorInstance = getEditor(config.getConfiguration(), this.id, temporaryContent);
            if (editorInstance == null) {
                throw new NullPointerException("Editor instance not created!");
            }
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
    public void setDraftingTool(IsWidget widget) {
        draftHolderPanel.add(widget);
    }

    @Override
    public void setDraftingAttributes(IsWidget widget) {
        attributesHolderPanel.add(widget);
    }

    @Override
    public void toggleDraftingTool(boolean toggled) {
        //toggle the view when the editor is attached to DOM
        if (isAttached()) {
            if (showDraftingTool) {
                mainPanel.setWidgetSize(draftHolderPanel, toggled ? 100 : 0);
            }
            if (toggled) {
                addBodyClassName(editorInstance, config.getDraftingClassName());
            } else {
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

    @Override
    public void toggleDraftingAttributes(boolean toggled) {
        //toggle the view when the editor is attached to DOM
        if (isAttached()) {
            if (showDraftingTool) {
                mainPanel.setWidgetSize(attributesHolderPanel, toggled ? 100 : 0);
                resize("100%", (toggled ? (height - 100) + "" : height +""));
            }
        }
    }

    @Override
    public void resize(String width, String height) {
        resize(editorInstance, width, height);
    }

    public native void destroy(JavaScriptObject editorInstance) /*-{
        if (editorInstance != null) editorInstance.destroy();
    }-*/;

    private native void addBodyClassName(JavaScriptObject editorInstance, String className) /*-{
        if (editorInstance != null && editorInstance.document != null && editorInstance.document.getBody() != null)
            editorInstance.document.getBody().addClass(className);
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
        } catch(e){}
    }-*/;

    private native JavaScriptObject getEditor(JavaScriptObject instanceConfig, Object elementID, String content) /*-{
        var editor = $wnd.CKEDITOR.replace(elementID, instanceConfig, content);
        return editor;
    }-*/;


    @Override
    public void setAmendableWidget(OverlayWidget overlayWidget) {
        config.addBodyClass(overlayWidget.getType());
    }

    @Override
    public void setHTML(final String content) {
        setTemporaryContent(content);
        if (attached) setHTMLInternal(editorInstance, content);
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
        if (showDraftingTool) {
            mainPanel.setWidgetSize(attributesHolderPanel, 0);
        }
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        init();
    }

    public void setHeight(int height) {
        this.config.setHeight(height);
    }

    @Override
    public void addBodyClass(String className) {
        this.config.addBodyClass(className);
    }

    @Override
    public void resetBodyClass() {
        config.resetBodyClass();
    }
}
