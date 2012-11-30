package org.nsesa.editor.gwt.editor.client.ui.amendments.header;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsAction;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsActionEvent;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsSelectionEvent;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * The controller for amendments panel header
 * User: groza
 * Date: 26/11/12
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentsHeaderController {
    private static final Selection<AmendmentController> NONE_SELECTION = new Selection<AmendmentController>() {
        @Override
        public boolean apply(AmendmentController amendmentController) {
            return false;
        }

        @Override
        public String getName() {
            return "None";
        }
    };
    private static final Selection<AmendmentController> ALL_SELECTION = new Selection<AmendmentController>() {
        @Override
        public boolean apply(AmendmentController amendmentController) {
            return true;
        }

        @Override
        public String getName() {
            return "All";
        }
    };

    private final AmendmentsHeaderView view;
    private DocumentEventBus documentEventBus;
    private final Map<String, Selection<AmendmentController>> selections = new LinkedHashMap<String, Selection<AmendmentController>>();
    private final Map<String, AmendmentsAction> actions = new LinkedHashMap<String, AmendmentsAction>();

    @Inject
    public AmendmentsHeaderController(AmendmentsHeaderView view, DocumentEventBus documentEventBus) {
        this.view = view;
        this.documentEventBus = documentEventBus;
        registerListeners();
        // register the selections
        registerSelections();
        // register the actions
        registerActions();
        this.view.setSelections(Arrays.asList(selections.values().toArray(new Selection[0])));
        this.view.setActions(Arrays.asList(actions.values().toArray(new AmendmentsAction[0])));

    }

    public AmendmentsHeaderView getView() {
        return view;
    }
    public void registerSelection(final Selection<AmendmentController> controllerSelection) {
        selections.put(controllerSelection.getName(), controllerSelection);
    }

    public void registerAction(final AmendmentsAction controllerAction) {
        actions.put(controllerAction.getName(), controllerAction);
    }

    protected void registerActions() {
        registerAction(new AmendmentsAction() {
            @Override
            public String getName() {
                return "Test Action";
            }

            @Override
            public void execute(List<String> ids) {
                Window.alert("You have selected " + ids);
            }
        });
    }


    protected void registerSelections() {
        registerSelection(NONE_SELECTION);
        registerSelection(ALL_SELECTION);
    }

    private void registerListeners() {
    }

}
