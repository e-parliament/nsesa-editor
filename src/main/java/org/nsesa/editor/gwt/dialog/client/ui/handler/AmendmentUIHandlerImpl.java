package org.nsesa.editor.gwt.dialog.client.ui.handler;

import com.google.gwt.user.client.ui.Composite;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext;

/**
 * Date: 21/12/12 16:09
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public abstract class AmendmentUIHandlerImpl extends Composite implements AmendmentUIHandler {

    protected DialogContext dialogContext;

    @Override
    public void setContext(DialogContext dialogContext) {
        this.dialogContext = dialogContext;
    }
}
