package org.nsesa.editor.gwt.editor.client.ui.amendments.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Action;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.editor.client.ui.header.HeaderView;

import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Created with IntelliJ IDEA.
 * User: groza
 * Date: 26/11/12
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Scope(DOCUMENT)
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
        initWidget(widget);
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void setSelections(List<Selection> selections) {
        for (Selection selection :selections) {
            this.selections.addItem(selection.getName(), selection.getName());
        }
    }

    @Override
    public void setActions(List<Action> actions) {
        for (Action action :actions) {
            this.actions.addItem(action.getName(), action.getName());
        }
    }

    @Override
    public HasChangeHandlers getSelections() {
        return selections;
    }

    @Override
    public String getSelectedSelection() {
        return selections.getValue(selections.getSelectedIndex());
    }

    @Override
    public HasChangeHandlers getActions() {
        return actions;
    }

    @Override
    public String getSelectedAction() {
        return actions.getValue(actions.getSelectedIndex());
    }
}
