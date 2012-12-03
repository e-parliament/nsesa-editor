package org.nsesa.editor.gwt.editor.client.ui.amendments.filter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Filter controller responsible to filter amendments if the case
 * User: groza
 * Date: 3/12/12
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
@Singleton
public class AmendmentsFilterController {
    private DocumentEventBus documentEventBus;
    private AmendmentsFilterView view;

    private static Map<String, String> filters = new LinkedHashMap<String, String>();

    @Inject
    public AmendmentsFilterController(DocumentEventBus documentEventBus, AmendmentsFilterView view) {
        this.documentEventBus = documentEventBus;
        this.view = view;
        registerFilterActions();
        registerListeners();
        this.view.setFilters(Arrays.asList(filters.values().toArray(new String[0])));
    }

    protected void registerFilterActions() {
        registerFilterAction("All amendments");
    }

    public void registerFilterAction(String filter) {
        filters.put(filter, filter);
    }

    protected void registerListeners() {
    }

    public AmendmentsFilterView getView() {
        return view;
    }
}
