package org.nsesa.editor.gwt.dialog.client.ui.rte.tinymce;

/**
 * Date: 15/10/12 09:27
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface YATinyEditorListener {
    void onInitialized(YATinyEditor editor);

    void onAttached();

    void onDetached();

    boolean executeCommand(YATinyEditor editor, String command, Object value);
}
