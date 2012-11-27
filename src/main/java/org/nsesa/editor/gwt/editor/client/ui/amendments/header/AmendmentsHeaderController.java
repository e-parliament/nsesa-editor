package org.nsesa.editor.gwt.editor.client.ui.amendments.header;

import com.google.inject.Inject;
import org.nsesa.editor.gwt.editor.client.ui.amendments.AmendmentsPanelView;

/**
 * The controller for amendments panel header
 * User: groza
 * Date: 26/11/12
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class AmendmentsHeaderController {
    private final AmendmentsHeaderView view;

    @Inject
    public AmendmentsHeaderController(AmendmentsHeaderView view) {
        this.view = view;
    }

    public AmendmentsHeaderView getView() {
        return view;
    }
}
