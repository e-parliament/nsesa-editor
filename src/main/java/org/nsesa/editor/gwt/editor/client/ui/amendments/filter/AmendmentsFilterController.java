package org.nsesa.editor.gwt.editor.client.ui.amendments.filter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.util.Filter;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsActionEvent;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsSelectionEvent;
import org.nsesa.editor.gwt.editor.client.event.amendments.MenuClickedEvent;
import org.nsesa.editor.gwt.editor.client.event.amendments.MenuClickedEventHandler;
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

    private static Map<String, Filter> filters = new LinkedHashMap<String, Filter>();

    @Inject
    public AmendmentsFilterController(DocumentEventBus documentEventBus, AmendmentsFilterView view) {
        this.documentEventBus = documentEventBus;
        this.view = view;
        registerFilterActions();
        registerListeners();
        this.view.setFilters(Arrays.asList(filters.keySet().toArray(new String[0])));
    }

    protected void registerFilterActions() {
        registerFilterAction("All amendments", null);
    }

    public void registerFilterAction(String filterName, Filter filter) {
        filters.put(filterName, filter);
    }

    protected void registerListeners() {
        HasClickHandlers filterHandler = view.getFilter();
        filterHandler.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Filter filter = filters.get(view.getSelectedFilter());
                if (filter != null) {
                    documentEventBus.fireEvent(new FilterRequestEvent(filter));
                }
            }
        });
    }

    public AmendmentsFilterView getView() {
        return view;
    }
}
