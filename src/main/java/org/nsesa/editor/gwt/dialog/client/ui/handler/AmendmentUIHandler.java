package org.nsesa.editor.gwt.dialog.client.ui.handler;

import com.google.gwt.user.client.ui.IsWidget;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext;

/**
 * Date: 10/07/12 20:34
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendmentUIHandler {

    void setContext(DialogContext dialogContext);

    void handle();

    IsWidget getView();
}
