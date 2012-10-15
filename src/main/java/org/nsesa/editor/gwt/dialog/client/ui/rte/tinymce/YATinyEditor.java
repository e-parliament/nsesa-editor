package org.nsesa.editor.gwt.dialog.client.ui.rte.tinymce;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * First attempt at using TinyMCE inside GWT via JSNI.
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id: YATinyEditor.java 5372 2012-03-16 11:03:04Z skoulouris $
 * @see <a href="http://tinymce.moxiecode.com/">http://tinymce.moxiecode.com/</a>
 */
public class YATinyEditor extends Composite implements RichTextEditor {

    private static final Map<String, String> LANGUAGE_PARAM_BY_LANG_ISOCODE = new HashMap<String, String>() {
        {
            put("bg", "+Bulgarian=bg");
            put("cz", "+Chech=cz");
            put("da", "+Danish=da");
            put("de", "+German=de");
            put("el", "+Greek=el");
            put("en", "+English=en");
            put("es", "+Spanish=es");
            put("et", "+Estonian=et");
            put("fi", "+Finnish=fi");
            put("fr", "+French=fr");
            put("hu", "+Hungarian=hu");
            put("it", "+Italian=it");
            put("lt", "+Lithuanian=lt");
            put("lv", "+Latvian=lv");
            put("mt", "+Maltese=mt");
            put("nl", "+Dutch=nl");
            put("pl", "+Polish=pl");
            put("pt", "+Potuguese=pt");
            put("ro", "+Romanian=ro");
            put("sk", "+Slovakian=sk");
            put("sl", "+Slovenian=sl");
            put("sv", "+Swedish=sv");
        }
    };

    //"English=en", "German=de", "French=fr", "Italian=it", "Spanish=es", "Greek=gr", "Portuguese=pt"

    private static final String DEFAULT_TINY_MCE_BUTTONS =
            "spellchecker,separator,justifyleft,justifycenter,separator,undo,redo,separator,sub,sup,separator,charmap";

    private static final String NO_JUSTIFY_TINY_MCE_BUTTONS =
            "spellchecker,separator,undo,redo,separator,sub,sup,separator,charmap";

    protected boolean readOnly;
    protected final String itemID;

    private YATinyEditorListener listener;

    private int work_width;
    private int work_height;
    private boolean attached;

    protected TextArea textArea;
    private FlowPanel mainPanel;

    public YATinyEditor(boolean readOnly, YATinyEditorListener listener) {
        this.readOnly = readOnly;
        this.listener = listener;

        mainPanel = new FlowPanel();
        mainPanel.setStyleName("tinyEditor-mainPanel");
        mainPanel.setHeight("100%");

        textArea = new TextArea();
        textArea.setStyleName("tinyEditor-textArea-original");
        textArea.setWidth("100%");
        textArea.setHeight("100%");
        textArea.getElement().setAttribute("wrap", "virtual");
        textArea.getElement().setAttribute("rows", "15");
        textArea.getElement().setAttribute("cols", "20");

        mainPanel.add(textArea);

        mainPanel.setStyleName("tinyEditor-mainPanel-cell");

        itemID = Random.nextInt(2147483647) + "mce";

        Element el = textArea.getElement();
        el.setId(itemID);
        el.setClassName(el.getClassName() + " " + itemID);

        initWidget(mainPanel);
    }

    public void logEvents(String toLog) {
        if (Log.isDebugEnabled())
            Log.debug("-----------> " + toLog);
    }

    public void initialized() {
        resize();
        listener.onInitialized(this);
    }

    public native void refreshEditor(String itemID) /*-{
        $wnd.tinyMCE.execCommand('mceRepaint', false, itemID);
    }-*/;

    public String getItemID() {
        return itemID;
    }

    public boolean commandCallback(String command, Object value) {
        return listener.executeCommand(this, command, value);
    }

    public void attachEditor(String isoLanguage) {
        attachEditor(isoLanguage, true);
    }

    public void attachEditor(String isoLanguage, boolean noAlignment) {
        String mceButtons = noAlignment ? NO_JUSTIFY_TINY_MCE_BUTTONS : DEFAULT_TINY_MCE_BUTTONS;
        attachEditor(isoLanguage, mceButtons);
    }

    public void attachEditor(String isoLanguage, String mceButtons) {
        if (!attached) {
            String path = GWT.getModuleBaseURL() + "css/editor.css";
            attachEditor(this, itemID, path, readOnly, generateLanguageSettings(isoLanguage, new HashSet<String>()), mceButtons);
        }
        attached = true;
        listener.onAttached();
        resize();
    }

    private native void attachEditor(YATinyEditor tinyEditor, String itemID, String cssPath,
                                     boolean readOnly, String defaultLanguages, String mceButtons) /*-{
        // required by EPNet to be able to rewrite the URLs.
        var tiny_content_css = cssPath;
        var tiny_spellchecker_rpc_url = "spellchecker/rpc/";
        var tiny_theme = "advanced";
        var customCleanUpFunc = function (type, value) {
            var value = value + ""; //Ensure value is a string
            value = value.replace(/<(p|em|strong|s)(>|[^>]*>)(\s)*<\/\1>/ig, "");
            var html = value + ""; //Ensure source is a string
            var regX = /<(?:!(?:--[\s\S]*?--\s*)?(>)\s*|(?:script|style|SCRIPT|STYLE)[\s\S]*?<\/(?:script|style|SCRIPT|STYLE)>)/g;
            return html.replace(regX, function (m, $1) {
                return $1 ? '' : m;
            });
        };

        var doc_type = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' "
                + "'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>";
        var valid_elems = ""
                + "br[id],"
            // + "-del[cite|datetime|id|lang],"
            // + "-ins[cite|datetime|id|lang],"
                + "-p[lang],"
                + "-span[id|lang],"
                + "-sub[id|lang],"
                + "-sup[id|lang],"
                + "table[id|lang|width],"
            // + "tbody[id|lang],"
                + "td[id|colspan|height|lang|rowspan|width],"
            // + "tfoot[id|lang],"
                + "th[id|colspan|height|lang|rowspan|width],"
            // + "thead[id|lang],"
                + "tr[id|rowspan|lang],";

        var plug = "paste, noneditable, contextmenu, table, tablecontextmenu";
        if (defaultLanguages) {
            plug = "paste, spellchecker, noneditable, contextmenu, table, tablecontextmenu";
        }

        var settings = {
            mode:"none",
            //mode : "specific_textareas",
            theme:tiny_theme,
            doctype:doc_type,
            plugins:plug,
            cleanup_on_startup:false,
            cleanup:false,
            inline_styles:false,
            content_css:tiny_content_css,
            verify_html:false,
            readonly:readOnly,
            convert_urls:false,
            //editor_selector : itemID,
            fix_content_duplication:false,
            valid_elements:valid_elems,
            apply_source_formatting:false,
            theme_advanced_buttons1:mceButtons,
            theme_advanced_buttons2:"",
            theme_advanced_buttons3:"",
            theme_advanced_toolbar_location:"bottom",
            //theme_advanced_buttons2_add : "tablecontrols",
            table_styles:"Header 1=header1;Header 2=header2;Header 3=header3",
            table_cell_styles:"Header 1=header1;Header 2=header2;Header3=header3;Table Cell=tableCel1",
            table_row_styles:"Header 1=header1;Header 2=header2;Header3=header3;Table Row=tableRow1",
            table_cell_limit:500,
            table_row_limit:20,
            table_col_limit:20,
            spellchecker_rpc_url:tiny_spellchecker_rpc_url,
            spellchecker_languages:defaultLanguages,
            paste_text_sticky:true,
            paste_auto_cleanup_on_paste:true,
            cleanup_callback:customCleanUpFunc,
            entity_encoding:"raw",
            setup:function (ed) {
                ed.onInit.add(function (ed) {
                    ed.focus();
                    ed.pasteAsPlainText = true;
                });
                ed.onLoadContent.add(function (ed) {
                    tinyEditor.@org.nsesa.editor.gwt.dialog.client.ui.rte.tinymce.YATinyEditor::initialized()();
                    // Modify the context menu
                    ed.plugins.contextmenu.onContextMenu.add(function (th, menu, event) {
                        var se = ed.selection, col = se.isCollapsed();

                        menu.removeAll();
                        menu.add({title:'advanced.cut_desc', icon:'cut', cmd:'Cut'}).setDisabled(col);
                        menu.add({title:'advanced.copy_desc', icon:'copy', cmd:'Copy'}).setDisabled(col);
                        menu.add({title:'advanced.paste_desc', icon:'paste', cmd:'Paste'});
                    });
                });
            }
        };
        $wnd.tinyMCE.settings = settings;
        $wnd.tinyMCE.execCommand('mceAddControl', false, itemID);
    }-*/;

    public void detachEditor() {
        if (attached) {
            detachEditor(itemID);
        }
        listener.onDetached();
        attached = false;
    }

    private native void detachEditor(String itemId) /*-{
        $wnd.tinyMCE.execCommand('mceRemoveControl', false, itemId);
    }-*/;


    public String getHTML() {
        String html = getHTML(itemID);
        if (html != null && TableAmendmentContentModifier.matches(html)) {
            TableAmendmentContentModifier.cleanupModifiedContent(html);
        }

        return html;
    }

    public native String getHTML(String itemID) /*-{
        var html = null;
        if ($wnd.tinyMCE.get(itemID)) {
            html = $wnd.tinyMCE.get(itemID).getContent();
        }
        return html;
    }-*/;

    public void setHTML(String content) {
        if (TableAmendmentContentModifier.matches(content)) {
            content = TableAmendmentContentModifier.modifyContent(content);
        }
        setHTML(this, content, itemID);
        setFocus();
    }

    public native void setHTML(final YATinyEditor editor, final String html, final String itemID) /*-{
        if ($wnd.tinyMCE.get(itemID)) {
            $wnd.tinyMCE.get(itemID).execCommand('mceSetContent', false, html);
        }
        editor.@org.nsesa.editor.gwt.dialog.client.ui.rte.tinymce.YATinyEditor::setTemporaryHTML(Ljava/lang/String;)(html);
    }-*/;


    public void setTemporaryHTML(String html) {
        //Log.info("Setting temporary html in tiny editor to " + itemID + "  '" + html + "'");
        textArea.setText(html);
    }

    public void setFocus() {
        setFocus(itemID);
    }

    private native void setFocus(String itemID)/*-{
        var editor = $wnd.tinyMCE.get(itemID);
        if (editor != null) {
            editor.focus();
            $wnd.tinyMCE.execCommand('mceFocus', false, itemID);
        }
    }-*/;


    public void setWorkSize(int work_width, int work_height) {
        this.work_width = work_width;
        this.work_height = work_height;
        resize();
    }

    private void resize() {
        try {
            int cnt = mainPanel.getElement().getChildCount();
            if (cnt > 1) {
                Node node = mainPanel.getElement().getChild(1);
                if (node != null && node.hasChildNodes()) {
                    // the table node is on the second position now
                    Node table = node.getChild(1);
                    Element child = (Element) table.cast();
                    child.getStyle().setWidth(work_width, Style.Unit.PX);
                    child.getStyle().setHeight(work_height, Style.Unit.PX);
                    if (!readOnly) {
                        //adjust height of <td> with IFrame
                        Node tableBody = table.getChild(0);
                        Node tableTR = tableBody.getChild(0);
                        Node tableTD = tableTR.getChild(0);
                        int h = work_height - 30; //26 + 2 + 2
                        Element cell = (Element) tableTD.cast();
                        cell.getStyle().setHeight(h, Style.Unit.PX);
                    }

                }
            }
        } catch (Exception e) {
            Log.error("Exception in resizing tiny mce editor in the pane dialog", e);
        }
    }

    @Inject
    private String generateLanguageSettings(String isoCode, Set<String> supportedISO) {
        for (String language : supportedISO) {
            if (language.equalsIgnoreCase(isoCode)) {
                return LANGUAGE_PARAM_BY_LANG_ISOCODE.get(language.toLowerCase());
            }
        }

        return null;
    }
}