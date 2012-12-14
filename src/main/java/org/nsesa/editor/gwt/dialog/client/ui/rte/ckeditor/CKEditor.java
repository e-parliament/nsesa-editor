package org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditor;

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

    private boolean readOnly;
    private String id;
    private String[] cssPath;
    private String bodyClass;

    private int height;

    private JavaScriptObject editorInstance;

    private final TextArea textArea = new TextArea();


    public CKEditor(final String cssPathToSplit, final String bodyClass, final boolean readOnly) {
        this.cssPath = cssPathToSplit.split(",");
        for (int i = 0; i < cssPath.length; i++) {
            cssPath[i] = GWT.getModuleBaseURL() + "../" + cssPath[i];
        }
        this.bodyClass = bodyClass;
        this.readOnly = readOnly;
        this.id = "ckEditor" + counter++;
        textArea.getElement().setId(this.id);
        initWidget(textArea);
        textArea.setWidth("100%");
        textArea.setHeight("100%");
    }

    @Override
    public void init() {
        if (!attached) {
            final JsArrayString jsStrings = (JsArrayString) JsArrayString.createArray();

            for (final String s : cssPath) {
                jsStrings.push(s);
            }
            JavaScriptObject configuration = getConfiguration(jsStrings, bodyClass, readOnly, textArea.getOffsetHeight() + (readOnly ? -21 : -55));
            editorInstance = getEditor(configuration, this.id, temporaryContent);
            if (editorInstance == null) {
                throw new NullPointerException("Editor instance not created!");
            }
            attached = true;
        }
    }

    public native void destroy(JavaScriptObject editorInstance) /*-{
        if (editorInstance != null) editorInstance.destroy();
    }-*/;

    /*

    NOTE: there still is a lot left to do for this editor, the most important being that the inserted content
    in this RTE must become valid according to the overlay factory (or we'll have problems later on when
    the content of the amendment gets parsed and serialized to XML).

    Note: this is possible, but at the moment, this is a manual process. We'll improve it by automatically
    wiring up the overlay factory, but this will take some time.

     */
    private native JavaScriptObject getConfiguration(JsArrayString cssPath, String bodyClass, boolean readOnly, int height) /*-{
        return {
            contentsCss: cssPath,
            bodyClass: bodyClass,
            readOnly: readOnly,
            startupFocus: !readOnly,
            height: height,
            toolbarStartupExpanded: !readOnly,
            toolbar: 'Basic',
            removePlugins: 'elementspath',
            toolbar_Basic: readOnly ? [
                []
            ] : [
                [ 'Subscript', 'Superscript', '-', 'Undo', 'Redo', '-', 'SpecialChar', '-', 'Find', 'Replace', '-', 'SelectAll' ]
            ],
            toolbarLocation: 'bottom',
            resize_enabled: false,
            autoParagraph: false,
            fillEmptyBlocks: false,
            forcePasteAsPlainText: true
            //startupShowBorders:false
        }
    }-*/;

    private native JavaScriptObject getEditor(JavaScriptObject instanceConfig, Object elementID, String content) /*-{
        var editor = $wnd.CKEDITOR.replace(elementID, instanceConfig, content);
        return editor;
    }-*/;

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
        destroy(editorInstance);
        editorInstance = null;
        attached = false;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        init();
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
