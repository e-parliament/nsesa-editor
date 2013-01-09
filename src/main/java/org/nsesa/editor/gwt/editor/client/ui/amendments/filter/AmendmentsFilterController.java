package org.nsesa.editor.gwt.editor.client.ui.amendments.filter;

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

    @Inject
    public AmendmentsFilterController(DocumentEventBus documentEventBus, AmendmentsFilterView view) {
        this.documentEventBus = documentEventBus;
        this.view = view;
        registerFilterActions();
        registerListeners();
        this.view.setFilters(Arrays.asList(filters.keySet().toArray(new String[0])));
    }

    protected void registerFilterActions() {
        registerFilterAction("All amendments",
                new Filter<AmendmentController>(0, 2, AmendmentController.ORDER_COMPARATOR, Selection.ALL));
        registerFilterAction("None",
                new Filter<AmendmentController>(0, 2, AmendmentController.ORDER_COMPARATOR, Selection.NONE));
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
