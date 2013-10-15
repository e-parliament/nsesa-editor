/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.dialog.client.ui.handler.common.authors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DIALOG;

/**
 * Default implementation for the {@link AuthorsPanelView} using UIBinder.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(DIALOG)
public class AuthorsPanelViewImpl extends Composite implements AuthorsPanelView {

    interface MyUiBinder extends UiBinder<Widget, AuthorsPanelViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private final ServiceFactory serviceFactory;

    private final ClientFactory clientFactory;

    /**
     * Suggest oracle for the person DTOs coming from the backend.
     */
    private final PersonMultiWordSuggestionOracle oracle;

    @UiField
    SuggestBox suggestBox;

    @UiField
    VerticalPanel authorsPanel;

    @UiField
    HorizontalPanel form;
    @UiField
    AbsolutePanel boundaryPanel;

    @Inject
    public AuthorsPanelViewImpl(final ServiceFactory serviceFactory, final ClientFactory clientFactory) {
        this.serviceFactory = serviceFactory;
        this.clientFactory = clientFactory;
        oracle = new PersonMultiWordSuggestionOracle(serviceFactory, clientFactory);
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        if (!GWT.isScript())
            widget.setTitle(this.getClass().getName());
    }

    @UiFactory
    SuggestBox createSuggestBox() {
        return new SuggestBox(oracle);
    }

    public SuggestBox getSuggestBox() {
        return suggestBox;
    }

    @Override
    public VerticalPanel getAuthorsPanel() {
        return authorsPanel;
    }

    @Override
    public AbsolutePanel getBoundaryPanel() {
        return boundaryPanel;
    }
}
