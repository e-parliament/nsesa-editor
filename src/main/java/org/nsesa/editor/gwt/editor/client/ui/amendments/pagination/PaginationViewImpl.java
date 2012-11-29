package org.nsesa.editor.gwt.editor.client.ui.amendments.pagination;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Created with IntelliJ IDEA.
 * User: groza
 * Date: 28/11/12
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Scope(DOCUMENT)
public class PaginationViewImpl extends Composite implements PaginationView {
    interface MyUiBinder extends UiBinder<Widget, PaginationViewImpl> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private PaginationCallback callback;

    @UiField
    Button first;
    @UiField
    Button last;
    @UiField
    Button next;
    @UiField
    Button previous;
    @UiField
    Label current;
    // start from one
    private int currentPage = 1;

    private int totalPages = 1;

    public PaginationViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        displayCurrentPage();
        initWidget(widget);
    }

    @Override
    public void setCallback(PaginationCallback callback) {
        this.callback = callback;
    }

    @Override
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        displayCurrentPage();
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @UiHandler("first")
    public void onClickFirst(ClickEvent event) {
        currentPage = 1;
        moveTo();
    }

    @UiHandler("last")
    public void onClickLast(ClickEvent event) {
        currentPage = totalPages;
        moveTo();
    }
    @UiHandler("next")
    public void onClickNext(ClickEvent event) {
        currentPage++;
        moveTo();
    }
    @UiHandler("previous")
    public void onClickPrevious(ClickEvent event) {
        currentPage--;
        moveTo();
    }

    private void moveTo() {
        if (currentPage <= 0) {
            currentPage = 1;
        } else if (currentPage >= totalPages) {
            currentPage = totalPages;
        }
        if (callback != null) {
            callback.moveToPage(currentPage);
        }
        displayCurrentPage();

    }

    private void displayCurrentPage() {
        current.setText("Page " + currentPage + " of " + totalPages);
    }

    @Override
    public Widget asWidget() {
        return this;
    }
}
