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
package org.nsesa.editor.gwt.amendment.client.ui.document.amendments.filter;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.event.filter.FilterRequestEvent;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.util.Filter;
import org.nsesa.editor.gwt.core.client.util.Selection;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <code>AmendmentsFilterController</code> class is responsible to set up the filters that will be available in
 * {@link AmendmentsFilterView} view and to raise {@link FilterRequestEvent} events as soon as the user select a
 * filter form the view.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 26/11/12 13:44
 */
@Singleton
public class AmendmentsFilterController {

    /**
     * Used to fire GWT events
     */
    private DocumentEventBus documentEventBus;
    /**
     * The view associated to this controller
     */
    private AmendmentsFilterView view;

    /**
     * A map with registered
     */
    private Map<String, Filter<AmendmentController>> filters = new LinkedHashMap<String, Filter<AmendmentController>>();

    /**
     * <code>Selection</code> of all <code>AmendmentController</code>
     */
    private Selection<AmendmentController> ALL = new Selection.AllSelection<AmendmentController>();

    /**
     * <code>Selection</code> of none of <code>AmendmentController</code>
     */
    private Selection<AmendmentController> NONE = new Selection.NoneSelection<AmendmentController>();
    private HandlerRegistration changeHandlerRegistration;

    /**
     * Create <code>AmendmentsFilterController</code> with the given parameters
     *
     * @param documentEventBus The event bus linked to the controller
     * @param view             The associated view
     */
    @Inject
    public AmendmentsFilterController(DocumentEventBus documentEventBus, AmendmentsFilterView view) {
        this.documentEventBus = documentEventBus;
        this.view = view;
        registerFilterActions();
        registerListeners();
        this.view.setFilters(Arrays.asList(filters.keySet().toArray(new String[filters.size()])));
    }

    /**
     * Register the actions that will be displayed in the view
     */
    protected void registerFilterActions() {
        registerFilterAction("All amendments",
                new Filter<AmendmentController>(0, 2, AmendmentController.ORDER_COMPARATOR, ALL));
        registerFilterAction("None",
                new Filter<AmendmentController>(0, 2, AmendmentController.ORDER_COMPARATOR, NONE));
    }

    /**
     * Add a filter in the list of filter actions available in the view
     *
     * @param filterName The filter name as String
     * @param filter     The filter representation
     */
    public void registerFilterAction(String filterName, Filter<AmendmentController> filter) {
        filters.put(filterName, filter);
    }

    /**
     * Add a change handler for {@link org.nsesa.editor.gwt.amendment.client.ui.document.amendments.filter.AmendmentsFilterView#getFilter()}
     * and raise a new {@link FilterRequestEvent} GWT event
     */
    private void registerListeners() {
        changeHandlerRegistration = view.getFilter().addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                Filter<AmendmentController> filter = filters.get(view.getSelectedFilter());
                if (filter != null) {
                    //set the start page to 0
                    filter.setStart(0);
                    documentEventBus.fireEvent(new FilterRequestEvent(filter));
                }
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        changeHandlerRegistration.removeHandler();
    }

    /**
     * Returns the view associated to the controller
     *
     * @return the view as AmendmentsFilterView
     */
    public AmendmentsFilterView getView() {
        return view;
    }
}
