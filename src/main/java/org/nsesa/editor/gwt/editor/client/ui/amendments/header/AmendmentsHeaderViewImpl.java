package org.nsesa.editor.gwt.editor.client.ui.amendments.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsAction;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsActionEvent;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsSelectionEvent;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Implementation for AmendmentsHeaderView interface
 * User: groza
 * Date: 26/11/12
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentsHeaderViewImpl extends Composite implements AmendmentsHeaderView {

    private DocumentEventBus documentEventBus;

    interface MyUiBinder extends UiBinder<Widget, AmendmentsHeaderViewImpl> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    MenuBar menu;
    @UiField
    MenuBar menuSelection;
    @UiField
    MenuBar menuAction;

    @Inject
    public AmendmentsHeaderViewImpl(DocumentEventBus documentEventBus) {
        this.documentEventBus = documentEventBus;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void setSelections(List<Selection> selections) {
        for (final Selection selection :selections) {
            this.menuSelection.addItem(selection.getName(), new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    documentEventBus.fireEvent(new AmendmentsSelectionEvent(selection));
                }
            });
        }
    }

    @Override
    public void setActions(List<AmendmentsAction> actions) {
        for (final AmendmentsAction action :actions) {
            this.menuAction.addItem(action.getName(), new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    documentEventBus.fireEvent(new AmendmentsActionEvent(action));
                }
            });
        }
    }
}
