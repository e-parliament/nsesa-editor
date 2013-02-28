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
import com.google.gwt.core.client.JsArrayString;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;

import java.util.List;

/**
 * Defines configuration settings for CK Editor.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 11/01/13 15:24
 *
 * @see <a href="http://docs.cksource.com/ckeditor_api/symbols/CKEDITOR.config.html">CkEditor configuration</a>
 */
public class CKEditorConfig implements RichTextEditorConfig {

    /**
     * Nsesa plugin name defined in CK editor plugins area
     */
    public static String NSESA_PLUGIN_NAME = "nsesa";

    private List<String> contentCss;
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
    private String draftingClassName;
    private int zIndex = 10000;

    private JavaScriptObject config = JavaScriptObject.createObject();

    public CKEditorConfig() {
    }

    public JavaScriptObject getConfiguration() {
        return config;
    }

    @Override
    public String getDraftingClassName() {
        return draftingClassName;
    }

    @Override
    public void setDraftingClassName(String draftingClassName) {
        this.draftingClassName = draftingClassName;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        readOnly(readOnly);
    }

    /**
     * If true, makes the editor start in read-only state.
     * @param readOnly
     * @return editor instance
     */
    public CKEditorConfig readOnly(boolean readOnly) {
        this.readOnly = readOnly;
        setNativeReadOnly(readOnly);
        return this;
    }

    /**
     * Set the base Z-index for floating dialog windows and popups.
     * @param zIndex
     * @return editor instance
     */
    public CKEditorConfig setZIndex(int zIndex) {
        this.zIndex = zIndex;
        setNativeZIndex(zIndex);
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

    /**
     * Set the CSS file(s) to be used to apply style to editor contents.
     * @param contentCss
     * @return
     */
    public CKEditorConfig setContentCss(List<String> contentCss) {
        this.contentCss = contentCss;
        final JsArrayString jsStrings = (JsArrayString) JsArrayString.createArray();
        for (final String s : contentCss) {
            jsStrings.push(s);
        }
        setNativeContentCss(jsStrings);
        return this;
    }

    /**
     * Set a list of additional plugins to be loaded.
     * @param extraPlugins A list of plugins separated by quota
     * @return
     */
    public CKEditorConfig setExtraPlugins(String extraPlugins) {
        this.extraPlugins = extraPlugins;
        setNativeExtraPlugins(extraPlugins);
        return this;
    }

    /**
     * Sets the class attribute to be used on the body element of the editing area
     * @param bodyClass
     * @return
     */
    public CKEditorConfig setBodyClass(String bodyClass) {
        this.originalBodyClass = this.bodyClass;
        this.bodyClass = bodyClass;
        setNativeBodyClass(bodyClass);
        return this;
    }

    /**
     * Sets whether the editor should have the focus when the page loads.
     * @param startupFocus
     * @return
     */
    public CKEditorConfig setStartupFocus(boolean startupFocus) {
        this.startupFocus = startupFocus;
        setNativeStartupFocus(startupFocus);
        return this;
    }

    /**
     * Sets the height of the editor when the editor is first displayed.
     * @param height
     */
    public void setHeight(int height) {
        height(height);
    }

    /**
     * Sets the height of the editor when the editor is first displayed.
     * @param height
     */
    public CKEditorConfig height(int height) {
        this.height = height;
        setNativeHeight(height);
        return this;
    }

    /**
     * Whether the toolbar must start expanded when the editor is loaded
     * @param toolbarStartupExpanded
     * @return
     */
    public CKEditorConfig setToolbarStartupExpanded(boolean toolbarStartupExpanded) {
        this.toolbarStartupExpanded = toolbarStartupExpanded;
        setNativeToolbarStartupExpanded(toolbarStartupExpanded);
        return this;
    }

    /**
     * Set the toolbox (alias toolbar) definition.
     * @param toolbar
     * @return
     */
    public CKEditorConfig setToolbar(CKEditorToolbar toolbar) {
        this.toolbar = toolbar;
        setNativeToolbar(toolbar.getRepresentation());
        return this;
    }

    /**
     * Set a list of plugins that must not be loaded
     * @param removePlugins List of plugins separated by quota
     * @return
     */
    public CKEditorConfig setRemovePlugins(String removePlugins) {
        this.removePlugins = removePlugins;
        setNativeRemovePlugins(removePlugins);
        return this;
    }

    /**
     * Set the space where the toolbar is rendered
     * @param toolbarLocation Possible values top or bottom
     * @return
     */
    public CKEditorConfig setToolbarLocation(String toolbarLocation) {
        this.toolbarLocation = toolbarLocation;
        setNativeToolbarLocation(toolbarLocation);
        return this;
    }

    /**
     * Whether to enable the resizing feature.
     * @param resize_enabled
     * @return
     */
    public CKEditorConfig setResize_enabled(boolean resize_enabled) {
        this.resize_enabled = resize_enabled;
        setNativeResize_enabled(resize_enabled);
        return this;
    }

    /**
     * Whether automatically create wrapping blocks around inline contents inside document body
     * @param autoParagraph
     * @return
     */
    public CKEditorConfig setAutoParagraph(boolean autoParagraph) {
        this.autoParagraph = autoParagraph;
        setNativeAutoParagraph(autoParagraph);
        return this;
    }

    /**
     * Whether a filler text (non-breaking space entity -  ) will be inserted into empty block elements in HTML output
     * @param fillEmptyBlocks
     * @return
     */
    public CKEditorConfig setFillEmptyBlocks(boolean fillEmptyBlocks) {
        this.fillEmptyBlocks = fillEmptyBlocks;
        setNativeFillEmptyBlocks(fillEmptyBlocks);
        return this;
    }

    /**
     * Whether to force all pasting operations to insert on plain text into the editor
     * @param forcePasteAsPlainText
     * @return
     */
    public CKEditorConfig setForcePasteAsPlainText(boolean forcePasteAsPlainText) {
        this.forcePasteAsPlainText = forcePasteAsPlainText;
        setNativeForcePasteAsPlainText(forcePasteAsPlainText);
        return this;
    }

    /**
     * Whether to force editor to keep empty tags like span
     * @param tagName
     */
    public static void keepEmptyTag(String tagName) {
        keepNativeEmptyTag(tagName);
    }

    /**
     * Replace <code>sub</code> core style with a new defined one
     * @param newTag The new tag name for <code>sub</code> style
     * @param newClassName The new class name for <code>sub</code> style
     * @param newType The new type as attribute for <code>sub</code> style
     * @param nameSpace The new namespace as attribute for <code>sub</code> style
     * @return
     */
    public CKEditorConfig replaceSubStyle(String newTag, String newClassName, String newType, String nameSpace) {
        replaceCoreStyle("coreStyles_subscript", "sub", newTag, newClassName, newType, nameSpace);
        return this;
    }

    /**
     * Replace <code>sup</code> core style with a new defined one
     * @param newTag The new tag name for <code>sup</code> style
     * @param newClassName The new class name for <code>sup</code> style
     * @param newType The new type as attribute for <code>sup</code> style
     * @param nameSpace The new namespace as attribute for <code>sup</code> style
     * @return
     */
    public CKEditorConfig replaceSupStyle(String newTag, String newClassName, String newType, String nameSpace) {
        replaceCoreStyle("coreStyles_superscript", "sup", newTag, newClassName, newType, nameSpace);
        return this;
    }

    /**
     * Replace <code>b</code> core style with a new defined one
     * @param newTag The new tag name for <code>b</code> style
     * @param newClassName The new class name for <code>b</code> style
     * @param newType The new type as attribute for <code>b</code> style
     * @param nameSpace The new namespace as attribute for <code>b</code> style
     * @return
     */
    public CKEditorConfig replaceBoldStyle(String newTag, String newClassName, String newType, String nameSpace) {
        replaceCoreStyle("coreStyles_bold", "b", newTag, newClassName, newType, nameSpace);
        return this;
    }

    /**
     * Replace <code>i</code> core style with a new defined one
     * @param newTag The new tag name for <code>i</code> style
     * @param newClassName The new class name for <code>i</code> style
     * @param newType The new type as attribute for <code>i</code> style
     * @param nameSpace The new namespace as attribute for <code>i</code> style
     * @return
     */
    public CKEditorConfig replaceItalicStyle(String newTag, String newClassName, String newType, String nameSpace) {
        replaceCoreStyle("coreStyles_italic", "i", newTag, newClassName, newType, nameSpace);
        return this;
    }

    /**
     * Replace <code>u</code> core style with a new defined one
     * @param newTag The new tag name for <code>u</code> style
     * @param newClassName The new class name for <code>u</code> style
     * @param newType The new type as attribute for <code>u</code> style
     * @param nameSpace The new namespace as attribute for <code>u</code> style
     * @return
     */
    public CKEditorConfig replaceUnderlineStyle(String newTag, String newClassName, String newType, String nameSpace) {
        replaceCoreStyle("coreStyles_underline", "u", newTag, newClassName, newType, nameSpace);
        return this;
    }

    /**
     * Replace <code>strike</code> core style with a new defined one
     * @param newTag The new tag name for <code>strike</code> style
     * @param newClassName The new class name for <code>strike</code> style
     * @param newType The new type as attribute for <code>strike</code> style
     * @param nameSpace The new namespace as attribute for <code>strike</code> style
     * @return
     */
    public CKEditorConfig replaceStrikeThroughStyle(String newTag, String newClassName, String newType, String nameSpace) {
        replaceCoreStyle("coreStyles_strikethrough", "strike", newTag, newClassName, newType, nameSpace);
        return this;
    }

    private void replaceCoreStyle(String ckStyleName, String oldTag, String newTag, String newClassName, String newType, String nameSpace) {
        replaceNativeCoreStyle(ckStyleName, oldTag, newTag, newClassName, newType, nameSpace);
    }

    private native void setNativeReadOnly(boolean readOnly) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.readOnly = readOnly;
    }-*/;

    private native void setNativeZIndex(int zIndex) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.baseFloatZIndex = zIndex;
    }-*/;

    private native void setNativeContentCss(JsArrayString contentCss) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.contentsCss = contentCss;
    }-*/;

    private native void setNativeExtraPlugins(String extraPlugins) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.extraPlugins = extraPlugins;
    }-*/;

    private native void setNativeBodyClass(String bodyClass) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.bodyClass = bodyClass;
    }-*/;

    private native void setNativeStartupFocus(boolean startupFocus) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.startupFocus = startupFocus;
    }-*/;

    private native void setNativeHeight(int height) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.height = height;
    }-*/;

    private native void setNativeToolbarStartupExpanded(boolean toolbarStartupExpanded) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.toolbarStartupExpanded = toolbarStartupExpanded;
    }-*/;

    private native void setNativeToolbar(JavaScriptObject toolbar) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.toolbar = 'temp';
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.toolbar_temp = toolbar;
    }-*/;

    private native void setNativeRemovePlugins(String removePlugins) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.removePlugins = removePlugins;
    }-*/;

    private native void setNativeToolbarLocation(String toolbarLocation) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.toolbarLocation = toolbarLocation;
    }-*/;

    private native void setNativeFillEmptyBlocks(boolean fillEmptyBlocks) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.fillEmptyBlocks = fillEmptyBlocks;
    }-*/;

    private native void setNativeResize_enabled(boolean resize_enabled) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.resize_enabled = resize_enabled;
    }-*/;

    private native void setNativeAutoParagraph(boolean autoParagraph) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.autoParagraph = autoParagraph;
    }-*/;

    private native void setNativeForcePasteAsPlainText(boolean forcePasteAsPlainText) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config.forcePasteAsPlainText = forcePasteAsPlainText;
    }-*/;

    private static native void keepNativeEmptyTag(String tagName) /*-{
        $wnd.CKEDITOR.dtd.$removeEmpty[tagName] = 0;
    }-*/;

    private native void replaceNativeCoreStyle(String ckStyleName, String oldTag, String newTag, String className, String newType, String nameSpace) /*-{
        this.@org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditorConfig::config[ckStyleName] =
        {
            element: newTag,
            attributes: { 'class': className, 'type': newType, 'ns': nameSpace},
            overrides: oldTag
        };
    }-*/;
}
