/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.editor.client.ui.document.amendments.filter;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.util.Filter;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.editor.client.event.filter.FilterRequestEvent;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Filter controller responsible to raise filter amendment events if the case
 * User: groza
 * Date: 3/12/12
 * Time: 13:52
 */
@Singleton
public class AmendmentsFilterController {
    private DocumentEventBus documentEventBus;
    private AmendmentsFilterView view;

    private static Map<String, Filter<AmendmentController>> filters = new LinkedHashMap<String, Filter<AmendmentController>>();

    private Selection<AmendmentController> ALL = new Selection.AllSelection<AmendmentController>();
    private Selection<AmendmentController> NONE = new Selection.NoneSelection<AmendmentController>();

    @Inject
    public AmendmentsFilterController(DocumentEventBus documentEventBus, AmendmentsFilterView view) {
        this.documentEventBus = documentEventBus;
        this.view = view;
        registerFilterActions();
        registerListeners();
        this.view.setFilters(Arrays.asList(filters.keySet().toArray(new String[filters.size()])));
    }

    protected void registerFilterActions() {
        registerFilterAction("All amendments",
                new Filter<AmendmentController>(0, 2, AmendmentController.ORDER_COMPARATOR, ALL));
        registerFilterAction("None",
                new Filter<AmendmentController>(0, 2, AmendmentController.ORDER_COMPARATOR, NONE));
    }

    public void registerFilterAction(String filterName, Filter<AmendmentController> filter) {
        filters.put(filterName, filter);
    }

    protected void registerListeners() {
        HasChangeHandlers filterHandler = view.getFilter();
        filterHandler.addChangeHandler(new ChangeHandler() {
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

    public AmendmentsFilterView getView() {
        return view;
    }
}
