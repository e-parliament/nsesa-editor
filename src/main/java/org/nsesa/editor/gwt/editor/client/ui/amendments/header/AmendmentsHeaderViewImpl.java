package org.nsesa.editor.gwt.editor.client.ui.amendments.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import org.nsesa.editor.gwt.editor.client.ui.header.HeaderView;

/**
 * Created with IntelliJ IDEA.
 * User: groza
 * Date: 26/11/12
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */
public class AmendmentsHeaderViewImpl extends Composite implements AmendmentsHeaderView {



    interface MyUiBinder extends UiBinder<Widget, AmendmentsHeaderViewImpl> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    ListBox selections;
    @UiField
    ListBox actions;

    public AmendmentsHeaderViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        this.selections.addItem("All", "All");
        this.selections.addItem("None", "None");
        initWidget(widget);
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void setSelections() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setActions() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HasChangeHandlers getSelections() {
        return selections;
    }

    @Override
    public HasChangeHandlers getActions() {
        return actions;
    }
}
