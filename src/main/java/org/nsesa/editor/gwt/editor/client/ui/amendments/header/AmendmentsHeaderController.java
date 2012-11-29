package org.nsesa.editor.gwt.editor.client.ui.amendments.header;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.util.Action;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsActionEvent;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsSelectionEvent;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.editor.client.ui.amendments.AmendmentsPanelController;
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
    private static final Action NONE_ACTION = new Action() {
        @Override
        public String getName() {
            return "Select an action";
        }
        public void execute(){
            // do nothing
        }
    };

    private final AmendmentsHeaderView view;
    private DocumentEventBus documentEventBus;
    private final Map<String, Selection<AmendmentController>> selections = new LinkedHashMap<String, Selection<AmendmentController>>();
    private final Map<String, Action> actions = new LinkedHashMap<String, Action>();

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
        this.view.setActions(Arrays.asList(actions.values().toArray(new Action[0])));

    }

    public AmendmentsHeaderView getView() {
        return view;
    }
    public void registerSelection(final Selection<AmendmentController> controllerSelection) {
        selections.put(controllerSelection.getName(), controllerSelection);
    }

    public void registerAction(final Action controllerAction) {
        actions.put(controllerAction.getName(), controllerAction);
    }

    protected void registerActions() {
        registerAction(NONE_ACTION);
        registerAction(new Action() {
            @Override
            public String getName() {
                return "Test Action";
            }

            @Override
            public void execute() {
                Window.alert("This a test action");
            }
        });
    }


    protected void registerSelections() {
        registerSelection(NONE_SELECTION);
        registerSelection(ALL_SELECTION);
    }

    private void registerListeners() {
        //listener for selections
        final HasChangeHandlers selectionBox = view.getSelections();
        selectionBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                final String selectionName = view.getSelectedSelection();
                if (selectionName != null) {
                    Selection<AmendmentController> selection = selections.get(selectionName);
                    documentEventBus.fireEvent(new AmendmentsSelectionEvent(selection));
                }
            }
        });
        //listener for actions
        final HasChangeHandlers actionBox = view.getActions();
        actionBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                final String actionName = view.getSelectedAction();
                if (actionName != null) {
                    final Action action = actions.get(actionName);
                    //execute action
                    documentEventBus.fireEvent(new AmendmentsActionEvent(action));
                }
            }
        });


    }

}
