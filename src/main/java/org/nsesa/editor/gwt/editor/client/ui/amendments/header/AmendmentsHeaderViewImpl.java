package org.nsesa.editor.gwt.editor.client.ui.amendments.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.event.amendments.MenuClickedEvent;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Implementation for AmendmentsFilterView interface
 * User: groza
 * Date: 26/11/12
 * Time: 11:51
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
    public void setSelections(List<String> selections) {
        for (final String selection :selections) {
            this.menuSelection.addItem(selection, new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    documentEventBus.fireEvent(new MenuClickedEvent(selection, MenuClickedEvent.MenuType.SELECTION));
                }
            });
        }
    }

    @Override
    public void setActions(List<String> actions) {
        for (final String action :actions) {
            this.menuAction.addItem(action, new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    documentEventBus.fireEvent(new MenuClickedEvent(action, MenuClickedEvent.MenuType.ACTION));
                }
            });
        }
    }
}
