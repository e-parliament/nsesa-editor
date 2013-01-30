package org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditor;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorPlugin;

/**
 * Date: 04/12/12 13:19
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
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

    //the main panel for ck editor
    private DockLayoutPanel mainPanel = new DockLayoutPanel(Style.Unit.PX);
    // the holder for drafting tool
    private FlowPanel draftHolderPanel = new FlowPanel();

    public CKEditor(RichTextEditorPlugin plugin, RichTextEditorConfig config) {
        this.plugin = plugin;
        this.config = config;

        this.id = "ckEditor" + counter++;
        textArea.getElement().setId(this.id);
        mainPanel.addEast(draftHolderPanel, 1);
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

            config.setHeight(textArea.getOffsetHeight() + (config.isReadOnly() ? -21 : -55));

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
    public void toggleDraftingTool(boolean toggled) {
        mainPanel.setWidgetSize(draftHolderPanel, toggled ? 100 : 0);
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

    public native void destroy(JavaScriptObject editorInstance) /*-{
        if (editorInstance != null) editorInstance.destroy();
    }-*/;

    private native void executeCommand(JavaScriptObject editorInstance, String cmdName) /*-{
        if (editorInstance != null && editorInstance.getCommand(cmdName) != null) {
            editorInstance.getCommand(cmdName).exec();
        }
    }-*/;

    private native JavaScriptObject getEditor(JavaScriptObject instanceConfig, Object elementID, String content) /*-{
        var editor = $wnd.CKEDITOR.replace(elementID, instanceConfig, content);
        return editor;
    }-*/;

    @Override
    public void setAmendableWidget(AmendableWidget amendableWidget) {
        config.addBodyClass(amendableWidget.getType());
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
