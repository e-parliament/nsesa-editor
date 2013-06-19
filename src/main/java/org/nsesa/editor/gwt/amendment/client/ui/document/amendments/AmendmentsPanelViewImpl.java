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
package org.nsesa.editor.gwt.amendment.client.ui.document.amendments;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.amendment.client.ui.document.amendments.filter.AmendmentsFilterController;
import org.nsesa.editor.gwt.amendment.client.ui.document.amendments.filter.AmendmentsFilterView;
import org.nsesa.editor.gwt.amendment.client.ui.document.amendments.header.AmendmentsHeaderController;
import org.nsesa.editor.gwt.amendment.client.ui.document.amendments.header.AmendmentsHeaderView;
import org.nsesa.editor.gwt.core.client.event.ResizeEvent;
import org.nsesa.editor.gwt.core.client.event.ResizeEventHandler;
import org.nsesa.editor.gwt.core.client.event.selection.OverlayWidgetAwareSelectionEvent;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.pagination.PaginationController;
import org.nsesa.editor.gwt.core.client.ui.pagination.PaginationView;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.util.Selection;

import java.util.*;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Default implementation of <code>AmendmentsPanelView</code> interface based on {@link UiBinder} GWT mechanism.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 24/06/12 21:44
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentsPanelViewImpl extends Composite implements AmendmentsPanelView, ProvidesResize {
    /**
     * Used to fire event when an amendemnt is selected or un selected
     */
    private DocumentEventBus documentEventBus;
    /**
     * constant used when resize the view
     */
    private static final int SCROLLBAR_OFFSET = 145;
    private HandlerRegistration resizeEventHandlerRegistration;

    interface MyUiBinder extends UiBinder<Widget, AmendmentsPanelViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    /**
     * facilitates scrolling functionality when there are more amendments to be listed
     */
    @UiField
    ScrollPanel scrollPanel;

    /**
     * <code>AmendmentsHeaderView</code> view linked to this widget
     */
    @UiField(provided = true)
    AmendmentsHeaderView amendmentsHeaderView;

    /**
     * <code>AmendmentsFilterView</code> view linked to this widget
     */
    @UiField(provided = true)
    AmendmentsFilterView filterView;

    /**
     * <code>PaginationView</code> view linked to this widget
     */
    @UiField(provided = true)
    PaginationView paginationView;

    /**
     * holder panel for amendments representation
     */
    @UiField
    FlowPanel amendmentsPanel;
    /**
     * holder map for check boxes
     */
    private Map<String, CheckBox> checkBoxes = new LinkedHashMap<String, CheckBox>();

    /* holder list for selected amendments*/
    private Set<AmendmentController> selectedAmendments = new HashSet<AmendmentController>();

    /**
     * Create <code>AmendmentsPanelViewImpl</code> with the given parameters
     *
     * @param amendmentsHeaderController
     * @param paginationController
     * @param amendmentsFilterController
     * @param documentEventBus
     */
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
        //show class name tool tip in hosted mode
        if (!GWT.isScript())
            widget.setTitle(this.getClass().getName());

        registerListeners();
    }

    /**
     * Register specific handlers in particular a handler to resize the scroll panel whenever is necessary.
     */
    private void registerListeners() {
        resizeEventHandlerRegistration = documentEventBus.addHandler(ResizeEvent.TYPE, new ResizeEventHandler() {
            @Override
            public void onEvent(ResizeEvent event) {
                final int height = event.getHeight() - SCROLLBAR_OFFSET;
                scrollPanel.setHeight(height + "px");
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        resizeEventHandlerRegistration.removeHandler();
    }

    /**
     * Display a check box and an amendment representation by using
     * {@link org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController#getExtendedView()}
     * for each amendment controller present in the provided map of amendment controllers
     *
     * @param amendments The amendments that will be displayed
     */
    @Override
    public void setAmendmentControllers(final Map<String, AmendmentController> amendments) {
        for (final Map.Entry<String, AmendmentController> entry : amendments.entrySet()) {
            FlowPanel panel = new FlowPanel();
            //create a check box
            CheckBox checkBox = new CheckBox();
            checkBox.getElement().getStyle().setFloat(Style.Float.LEFT);
            checkBox.getElement().getStyle().setPaddingTop(15, Style.Unit.PX);
            checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> event) {
                    if (event.getValue()) {
                        selectedAmendments.add(entry.getValue());
                    } else {
                        selectedAmendments.remove(entry.getValue());
                    }

                    documentEventBus.fireEvent(new OverlayWidgetAwareSelectionEvent(new Selection<AmendmentController>() {
                        @Override
                        public boolean select(AmendmentController amendmentController) {
                            return selectedAmendments.contains(amendmentController);
                        }
                    }));
                }
            });
            checkBoxes.put(entry.getKey(), checkBox);
            panel.add(checkBox);
            panel.add(entry.getValue().getExtendedView());
            amendmentsPanel.add(panel);
        }
    }

    /**
     * Clean up the view content and  set up again the amendment controllers
     *
     * @param amendments The amendments that will be displayed
     */
    @Override
    public void refreshAmendmentControllers(Map<String, AmendmentController> amendments) {
        amendmentsPanel.clear();
        checkBoxes.clear();
        setAmendmentControllers(amendments);
    }

    /**
     * Returns the selected amendment controllers which are visible in the view
     *
     * @return A list of Id that identify uniquely a selected amendment
     */
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

    /**
     * Mark as selected the check boxes associated to the given id-s
     *
     * @param ids A list of id-s to identify the amendment controllers
     */
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
