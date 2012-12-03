package org.nsesa.editor.gwt.editor.client.ui.pagination;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Default implementation of filter view
 * User: groza
 * Date: 28/11/12
 * Time: 13:01
 */
@Singleton
@Scope(DOCUMENT)
public class PaginationViewImpl extends Composite implements PaginationView {
    private DocumentEventBus documentEventBus;

    interface MyUiBinder extends UiBinder<Widget, PaginationViewImpl> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    Image first;
    @UiField
    Image last;
    @UiField
    Image next;
    @UiField
    Image previous;
    @UiField
    Label current;

    @Inject
    public PaginationViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public HasClickHandlers getFirst() {
        return first;
    }

    @Override
    public HasClickHandlers getLast() {
        return last;
    }

    @Override
    public HasClickHandlers getNext() {
        return next;
    }

    @Override
    public HasClickHandlers getPrevious() {
        return previous;
    }
    @Override
    public void displayCurrentPage(int currentPage, int totalPages) {
        current.setText("Page " + currentPage + " of " + totalPages);
    }

    @Override
    public Widget asWidget() {
        return this;
    }
}
