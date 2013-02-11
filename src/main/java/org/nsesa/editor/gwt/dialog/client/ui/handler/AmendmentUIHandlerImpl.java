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
