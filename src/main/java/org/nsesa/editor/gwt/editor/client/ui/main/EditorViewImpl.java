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
package org.nsesa.editor.gwt.editor.client.ui.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.editor.client.ui.footer.FooterController;
import org.nsesa.editor.gwt.editor.client.ui.footer.FooterView;
import org.nsesa.editor.gwt.editor.client.ui.header.HeaderController;
import org.nsesa.editor.gwt.editor.client.ui.header.HeaderView;

/**
 * Default implementation of the {@link EditorView} using UIBinder.
 * Date: 24/06/12 16:39
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EditorViewImpl extends Composite implements EditorView, ProvidesResize {

    interface MyUiBinder extends UiBinder<Widget, EditorViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    /**
     * The client factory, giving access to various user context dependencies.
     */
    protected final ClientFactory clientFactory;

    @UiField
    HorizontalPanel documentsPanel;
    @UiField(provided = true)
    HeaderView headerView;
    @UiField(provided = true)
    FooterView footerView;

    @Inject
    public EditorViewImpl(final ClientFactory clientFactory, final HeaderController headerController,
                          final FooterController footerController
    ) {
        this.clientFactory = clientFactory;
        this.headerView = headerController.getView();
        this.footerView = footerController.getView();
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        if (!GWT.isScript())
            widget.setTitle(this.getClass().getName());

    }

    /**
     * Returns the main panel to add {@link org.nsesa.editor.gwt.core.client.ui.document.DocumentController}s to.
     *
     * @return the document panel
     */
    @Override
    public CellPanel getDocumentsPanel() {
        return documentsPanel;
    }
}
