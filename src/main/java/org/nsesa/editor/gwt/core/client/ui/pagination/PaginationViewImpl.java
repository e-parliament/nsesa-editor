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
package org.nsesa.editor.gwt.core.client.ui.pagination;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Default implementation of {@link PaginationView} interface based on {@link UiBinder} GWT mechanism.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 28/11/12 15:37
 */
@Singleton
@Scope(DOCUMENT)
public class PaginationViewImpl extends Composite implements PaginationView {
    private DocumentEventBus documentEventBus;

    interface MyUiBinder extends UiBinder<Widget, PaginationViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    /**
     * "first" pagination image
     */
    @UiField
    Image first;

    /**
     * "last" pagination image
     */
    @UiField
    Image last;

    /**
     * "next" pagination image
     */
    @UiField
    Image next;

    /**
     * "previous" pagination image
     */
    @UiField
    Image previous;

    /**
     * stores information about current page
     */
    @UiField
    Label current;

    /**
     * Constructs an empty <code>PaginationViewImpl</code> object
     */
    @Inject
    public PaginationViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        if (!GWT.isScript())
            widget.setTitle(this.getClass().getName());
    }

    /**
     * Returns first image
     *
     * @return First image as HasClickHandlers
     */
    @Override
    public HasClickHandlers getFirst() {
        return first;
    }

    /**
     * Returns last image
     *
     * @return Last image as HasClickHandlers
     */

    @Override
    public HasClickHandlers getLast() {
        return last;
    }

    /**
     * Returns next image
     *
     * @return next image as HasClickHandlers
     */

    @Override
    public HasClickHandlers getNext() {
        return next;
    }

    /**
     * Returns previous image
     *
     * @return previous image as HasClickHandlers
     */
    @Override
    public HasClickHandlers getPrevious() {
        return previous;
    }

    /**
     * Set the label content
     *
     * @param currentPage The current page as int
     * @param totalPages  The total number of pages as int
     */
    @Override
    public void displayCurrentPage(int currentPage, int totalPages) {
        current.setText("Page " + currentPage + " of " + totalPages);
    }

    /**
     * Returns the view as <code>Widget</code>
     *
     * @return The view
     */
    @Override
    public Widget asWidget() {
        return this;
    }
}
