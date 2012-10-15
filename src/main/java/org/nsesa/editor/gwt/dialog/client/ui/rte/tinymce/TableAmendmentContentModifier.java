package org.nsesa.editor.gwt.dialog.client.ui.rte.tinymce;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Element;
import org.nsesa.editor.gwt.core.client.util.UUID;
import org.nsesa.editor.gwt.dialog.client.ui.rte.ContentModifier;

import java.util.HashMap;
import java.util.Map;

import static com.google.gwt.query.client.GQuery.$;

/**
 * Groups all changes that are required to adjust the content of a table
 * amendment to be used correctly by tinyMCE. eg nonEditablePlugin.
 * <p/>
 * Date: 15/10/12 09:33
 *
 * @author skoulouris
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public final class TableAmendmentContentModifier {

    private static final Map<String, String> cache = new HashMap<String, String>();

    private static final ContentModifier NON_EDITABLE = new ContentModifier() {
        @Override
        public String modifyContent(String content) {
            return content + "<p class=\"mceNonEditable\"></p>";
        }

        @Override
        public String undoModification(String content) {
            return replace(content, "");
        }

        private native String replace(String content, String replacement) /*-{
            content = content.replace(/<p class="*mceNonEditable"*>.*?<\/p>/i, replacement);
            return content;
        }-*/;
    };

    private static final ContentModifier ASSIGN_IDS = new ContentModifier() {
        // avoid static since we want different UID for TR and TD every time
        @Override
        public String modifyContent(String content) {
            GQuery object = $(content);
            if (object.id() == null || !object.id().startsWith("diff_")) {
                String result = cache.get(content);
                if (result == null) {
                    object.id("diff_table_0");
                    object.find("tr", "td").each(new Function() {
                        @Override
                        public Object f(Element e, int i) {
                            $(this).id("diff_" + UUID.uuid());
                            return null;
                        }
                    });
                    result = object.toString(false);
                    //remove $H GWT reserved attribute
                    result = result.replaceAll(".H=\"\\d+\"", "");
                    cache.put(content, result);
                }
                return result;
            }
            return content;
        }

        @Override
        public String undoModification(String content) {
            // undo no possible atm
            return content;
        }
    };

    private static final RegExp EXP = RegExp.compile("<table.*?", "i");

    private TableAmendmentContentModifier() {
    }

    public static boolean matches(String content) {
        return EXP.test(content);
    }

    public static String modifyContent(String content) {
        content = ASSIGN_IDS.modifyContent(content);
        content = NON_EDITABLE.modifyContent(content);
        return content;
    }

    public static String cleanupModifiedContent(String modifiedContent) {
        modifiedContent = NON_EDITABLE.undoModification(modifiedContent);
        modifiedContent = ASSIGN_IDS.undoModification(modifiedContent);
        return modifiedContent;
    }

}

