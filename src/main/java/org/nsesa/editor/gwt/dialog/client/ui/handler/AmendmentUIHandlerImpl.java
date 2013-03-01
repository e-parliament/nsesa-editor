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
package org.nsesa.editor.gwt.dialog.client.ui.handler;

import com.google.gwt.user.client.ui.Composite;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext;

/**
 * Very simple abstract implementation that only keeps a {@link DialogContext} around for other
 * {@link AmendmentUIHandler}s to work with.
 * Date: 21/12/12 16:09
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public abstract class AmendmentUIHandlerImpl extends Composite implements AmendmentUIHandler {

    /**
     * The dialog context to give access to a wide range of environment and runtime variables.
     */
    protected DialogContext dialogContext;

    /**
     * Set and store the {@link DialogContext}.
     * @param dialogContext the dialog context
     */
    @Override
    public void setContext(final DialogContext dialogContext) {
        this.dialogContext = dialogContext;
    }
}
