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

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DIALOG;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(DIALOG)
public class AuthorPanelViewImpl extends Composite implements AuthorPanelView {

    interface MyUiBinder extends UiBinder<Widget, AuthorPanelViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private final ServiceFactory serviceFactory;

    @UiField(provided = true)
    SuggestBox suggestBox;

    @Inject
    public AuthorPanelViewImpl(final ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;

        createSuggestBox();

        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    private void createSuggestBox() {
        MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
        serviceFactory.getGwtDocumentService();
        oracle.add("test1");
        oracle.add("test2");
        oracle.add("test3");
        oracle.add("mep1");
        oracle.add("mep2");
        oracle.add("mep3");

        suggestBox = new SuggestBox(oracle);
    }

}
