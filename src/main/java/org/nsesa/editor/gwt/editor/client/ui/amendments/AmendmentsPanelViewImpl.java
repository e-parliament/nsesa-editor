package org.nsesa.editor.gwt.editor.client.ui.amendments;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.event.ResizeEvent;
import org.nsesa.editor.gwt.core.client.event.ResizeEventHandler;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentControllerAddToSelectionEvent;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentControllerRemoveFromSelectionEvent;
import org.nsesa.editor.gwt.editor.client.ui.amendments.filter.AmendmentsFilterController;
import org.nsesa.editor.gwt.editor.client.ui.amendments.filter.AmendmentsFilterView;
import org.nsesa.editor.gwt.editor.client.ui.amendments.header.AmendmentsHeaderController;
import org.nsesa.editor.gwt.editor.client.ui.amendments.header.AmendmentsHeaderView;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.editor.client.ui.pagination.PaginationController;
import org.nsesa.editor.gwt.editor.client.ui.pagination.PaginationView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentsPanelViewImpl extends Composite implements AmendmentsPanelView, ProvidesResize {

    private DocumentEventBus documentEventBus;
    private static final int SCROLLBAR_OFFSET = 125;

    interface MyUiBinder extends UiBinder<Widget, AmendmentsPanelViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    ScrollPanel scrollPanel;

    @UiField(provided = true)
    AmendmentsHeaderView amendmentsHeaderView;
    @UiField(provided = true)
    AmendmentsFilterView filterView;
    @UiField(provided = true)
    PaginationView paginationView;

    @UiField
    VerticalPanel amendmentsPanel;

    private Map<String, CheckBox> checkBoxes = new LinkedHashMap<String, CheckBox>();

    @Inject
    public AmendmentsPanelViewImpl(AmendmentsHeaderController amendmentsHeaderController,
                                   PaginationController paginationController,
                                   AmendmentsFilterController amendmentsFilterController,
                                   DocumentEventBus documentEventBus) {
        this.documentEventBus = documentEventBus;
        this.amendmentsHeaderView = amendmentsHeaderController.getView();
        this.filterView = amendmentsFilterController.getView();
        this.paginationView = paginationController.getPaginationView();
        final Widget widget = uiBinder.createAndBindUi(this);

        initWidget(widget);
        registerListeners();
    }

    private void registerListeners() {
        documentEventBus.addHandler(ResizeEvent.TYPE, new ResizeEventHandler() {
            @Override
            public void onEvent(ResizeEvent event) {
                final int height = event.getHeight() - SCROLLBAR_OFFSET;
                scrollPanel.setHeight(height + "px");
            }
        });

    }


    @Override
    public void setAmendmentControllers(final Map<String, AmendmentController> amendments) {
        for (final Map.Entry<String, AmendmentController> entry : amendments.entrySet()) {
            HorizontalPanel panel = new HorizontalPanel();
            //create a check box
            CheckBox checkBox = new CheckBox();
            checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> event) {
                    if (event.getValue()) {
                        documentEventBus.fireEvent(new AmendmentControllerAddToSelectionEvent(entry.getValue()));
                    } else {
                        documentEventBus.fireEvent(new AmendmentControllerRemoveFromSelectionEvent(entry.getValue()));
                    }
                }
            });
            checkBoxes.put(entry.getKey(), checkBox);
            panel.add(checkBox);
            panel.add(entry.getValue().getExtendedView());
            amendmentsPanel.add(panel);
        }
    }

    @Override
    public void refreshAmendmentControllers(Map<String, AmendmentController> amendments) {
        amendmentsPanel.clear();
        checkBoxes.clear();
        setAmendmentControllers(amendments);
    }

    @Override
    public List<String> getSelectedVisibleAmendmentContainerIds() {
        List<String> result = new ArrayList<String>();
        for (Map.Entry<String, CheckBox> entry : checkBoxes.entrySet()) {
            if (entry.getValue().getValue()) {
                result.add(entry.getKey());
            }

        }
        return result;
    }

    @Override
    public void selectAmendmentControllers(List<String> ids) {
        // deselect all
        for (Map.Entry<String, CheckBox> entry : checkBoxes.entrySet()) {
            entry.getValue().setValue(false);
        }
        for (String id : ids) {
            CheckBox checkBox = checkBoxes.get(id);
            if (checkBox != null) {
                checkBox.setValue(true);
            }
        }
    }

}
