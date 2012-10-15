package org.nsesa.editor.gwt.dialog.client.ui.rte;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Date: 13/07/12 19:39
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface RichTextEditor extends IsWidget {
    void setHTML(String content);

    String getHTML();
}
