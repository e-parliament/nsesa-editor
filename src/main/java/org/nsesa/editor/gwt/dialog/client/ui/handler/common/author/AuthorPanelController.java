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
package org.nsesa.editor.gwt.dialog.client.ui.handler.common.author;

import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.AmendmentDialogAwareController;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DIALOG;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(DIALOG)
public class AuthorPanelController implements AmendmentDialogAwareController {

    private final AuthorPanelView view;
    private final AuthorPanelViewCss authorPanelViewCss;

    private final ClientFactory clientFactory;

    private DialogContext dialogContext;

    @Inject
    public AuthorPanelController(final ClientFactory clientFactory, final AuthorPanelView view,
                                 final AuthorPanelViewCss authorPanelViewCss) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.authorPanelViewCss = authorPanelViewCss;
        registerListeners();
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void setContext(final DialogContext dialogContext) {
        this.dialogContext = dialogContext;
    }

    private void registerListeners() {
        // nothing yet
    }

    public AuthorPanelView getView() {
        return view;
    }

    @Override
    public String getTitle() {
        return "Author";
    }
}
