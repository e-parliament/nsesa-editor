package org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorConfig;

/**
 * Defines configuration environment for CK Editor
 * <p/>
 * User: groza
 * Date: 11/01/13
 * Time: 15:24
 */
public class CKEditorConfig implements RichTextEditorConfig {

    private String[] contentCss;
    private String bodyClass, originalBodyClass;
    private boolean readOnly;
    private boolean startupFocus;
    private int height;
    private boolean toolbarStartupExpanded;
    private CKEditorToolbar toolbar;
    private String removePlugins;
    private String extraPlugins;
    private String toolbarLocation;
    private boolean resize_enabled;
    private boolean autoParagraph;
    private boolean fillEmptyBlocks;
    private boolean forcePasteAsPlainText;

    private JavaScriptObject config = JavaScriptObject.createObject();

    public CKEditorConfig() {
    }

    public JavaScriptObject getConfiguration() {
        return config;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        readOnly(readOnly);
    }

    public CKEditorConfig readOnly(boolean readOnly) {
        this.readOnly = readOnly;
        setNativeReadOnly(readOnly);
        return this;
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    public void addBodyClass(String newBodyClass) {
        setBodyClass(this.bodyClass + " " + newBodyClass);
    }

    @Override
    public void resetBodyClass() {
        setBodyClass(this.originalBodyClass == null ? this.bodyClass : this.originalBodyClass);
    }

    public CKEditorConfig setContentCss(String[] contentCss) {
        this.contentCss = contentCss;
        final JsArrayString jsStrings = (JsArrayString) JsArrayString.createArray();
        for (final String s : contentCss) {
            jsStrings.push(s);
        }
        setNativeContentCss(jsStrings);
        return this;
    }

    public CKEditorConfig setExtraPlugins(String extraPlugins) {
        this.extraPlugins = extraPlugins;
        setNativeExtraPlugins(extraPlugins);
        return this;
    }

    public CKEditorConfig setBodyClass(String bodyClass) {
        this.originalBodyClass = this.bodyClass;
        this.bodyClass = bodyClass;
        setNativeBodyClass(bodyClass);
        return this;
    }

    public CKEditorConfig setStartupFocus(boolean startupFocus) {
        this.startupFocus = startupFocus;
        setNativeStartupFocus(startupFocus);
        return this;
    }

    public void setHeight(int height) {
        height(height);
    }

    public CKEditorConfig height(int height) {
        this.height = height;
        setNativeHeight(height);
        return this;
    }

    public CKEditorConfig setToolbarStartupExpanded(boolean toolbarStartupExpanded) {
        this.toolbarStartupExpanded = toolbarStartupExpanded;
        setNativeToolbarStartupExpanded(toolbarStartupExpanded);
        return this;
    }

    public CKEditorConfig setToolbar(CKEditorToolbar toolbar) {
        this.toolbar = toolbar;
        setNativeToolbar(toolbar.getRepresentation());
        return this;
    }

    public CKEditorConfig setRemovePlugins(String removePlugins) {
        this.removePlugins = removePlugins;
        setNativeRemovePlugins(removePlugins);
        return this;
    }


    public CKEditorConfig setToolbarLocation(String toolbarLocation) {
        this.toolbarLocation = toolbarLocation;
        setNativeToolbarLocation(toolbarLocation);
        return this;
    }


    public CKEditorConfig setResize_enabled(boolean resize_enabled) {
        this.resize_enabled = resize_enabled;
        setNativeResize_enabled(resize_enabled);
        return this;
    }

    public CKEditorConfig setAutoParagraph(boolean autoParagraph) {
        this.autoParagraph = autoParagraph;
        setNativeAutoParagraph(autoParagraph);
        return this;
    }

    public CKEditorConfig setFillEmptyBlocks(boolean fillEmptyBlocks) {
        this.fillEmptyBlocks = fillEmptyBlocks;
        setNativeFillEmptyBlocks(fillEmptyBlocks);
        return this;
    }

    public CKEditorConfig setForcePasteAsPlainText(boolean forcePasteAsPlainText) {
        this.forcePasteAsPlainText = forcePasteAsPlainText;
        setNativeForcePasteAsPlainText(forcePasteAsPlainText);
        return this;
    }

    public static void keepEmptyTag(String tagName) {
        keepNativeEmptyTag(tagName);
    }

    public CKEditorConfig replaceSubStyle(String newTag, String newClassName, String newType, String nameSpace) {
        replaceCoreStyle("coreStyles_subscript", "sub", newTag, newClassName, newType, nameSpace);
        return this;
    }

    public CKEditorConfig replaceSupStyle(String newTag, String newClassName, String newType, String nameSpace) {
        replaceCoreStyle("coreStyles_superscript", "sup", newTag, newClassName, newType, nameSpace);
        return this;
    }

    public CKEditorConfig replaceBoldStyle(String newTag, String newClassName, String newType, String nameSpace) {
        replaceCoreStyle("coreStyles_bold", "b", newTag, newClassName, newType, nameSpace);
        return this;
    }

    public CKEditorConfig replaceItalicStyle(String newTag, String newClassName, String newType, String nameSpace) {
        replaceCoreStyle("coreStyles_italic", "i", newTag, newClassName, newType, nameSpace);
        return this;
    }

    public CKEditorConfig replaceUnderlineStyle(String newTag, String newClassName, String newType, String nameSpace) {
        replaceCoreStyle("coreStyles_underline", "u", newTag, newClassName, newType, nameSpace);
        return this;
    }

    public CKEditorConfig replaceStrikeThroughStyle(String newTag, String newClassName, String newType, String nameSpace) {
        replaceCoreStyle("coreStyles_strikethrough", "strike", newTag, newClassName, newType, nameSpace);
        return this;
    }

    private void replaceCoreStyle(String ckStyleName, String oldTag, String newTag, String newClassName, String newType, String nameSpace) {
        replaceNativeCoreStyle(ckStyleName, oldTag, newTag, newClassName, newType, nameSpace);
    }

    private native void setNativeReadOnly(boolean readOnly) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.readOnly = readOnly;
    }-*/;

    private native void setNativeContentCss(JsArrayString contentCss) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.contentsCss = contentCss;
    }-*/;

    private native void setNativeExtraPlugins(String extraPlugins) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.extraPlugins = extraPlugins;
    }-*/;

    private native void setNativeBodyClass(String bodyClass) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.bodyClass = bodyClass;
    }-*/;

    private native void setNativeStartupFocus(boolean startupFocus) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.startupFocus = startupFocus;
    }-*/;

    private native void setNativeHeight(int height) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.height = height;
    }-*/;

    private native void setNativeToolbarStartupExpanded(boolean toolbarStartupExpanded) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.toolbarStartupExpanded = toolbarStartupExpanded;
    }-*/;

    private native void setNativeToolbar(JavaScriptObject toolbar) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.toolbar = 'temp';
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.toolbar_temp = toolbar;
    }-*/;

    private native void setNativeRemovePlugins(String removePlugins) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.removePlugins = removePlugins;
    }-*/;

    private native void setNativeToolbarLocation(String toolbarLocation) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.toolbarLocation = toolbarLocation;
    }-*/;

    private native void setNativeFillEmptyBlocks(boolean fillEmptyBlocks) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.fillEmptyBlocks = fillEmptyBlocks;
    }-*/;

    private native void setNativeResize_enabled(boolean resize_enabled) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.resize_enabled = resize_enabled;
    }-*/;

    private native void setNativeAutoParagraph(boolean autoParagraph) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.autoParagraph = autoParagraph;
    }-*/;

    private native void setNativeForcePasteAsPlainText(boolean forcePasteAsPlainText) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config.forcePasteAsPlainText = forcePasteAsPlainText;
    }-*/;

    private static native void keepNativeEmptyTag(String tagName) /*-{
        $wnd.CKEDITOR.dtd.$removeEmpty[tagName] = 0;
    }-*/;

    private native void replaceNativeCoreStyle(String ckStyleName, String oldTag, String newTag, String className, String newType, String nameSpace) /*-{
        this.@org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorConfig::config[ckStyleName] =
        {
            element: newTag,
            attributes: { 'class': className, 'type': newType, 'ns': nameSpace},
            overrides: oldTag
        };
    }-*/;
}
