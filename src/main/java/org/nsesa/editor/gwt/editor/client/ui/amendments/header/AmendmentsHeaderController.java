package org.nsesa.editor.gwt.editor.client.ui.amendments.header;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.editor.client.event.amendments.*;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.*;

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
        public boolean select(AmendmentController amendmentController) {
            return false;
        }

        @Override
        public String getName() {
            return "None";
        }
    };
    private static final Selection<AmendmentController> ALL_SELECTION = new Selection<AmendmentController>() {
        @Override
        public boolean select(AmendmentController amendmentController) {
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
        setSelections();
        setActions();
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

    private void setSelections() {
        List<String> list = new ArrayList<String>();
        for(final Selection<AmendmentController> selection : selections.values()) {
            list.add(selection.getName());
        }
        this.view.setSelections(list);
    }

    private void setActions() {
        List<String> list = new ArrayList<String>();
        for(final AmendmentsAction action : actions.values()) {
            list.add(action.getName());
        }
        this.view.setActions(list);
    }

    private void registerListeners() {
        documentEventBus.addHandler(MenuClickedEvent.TYPE, new MenuClickedEventHandler() {
            @Override
            public void onEvent(MenuClickedEvent event) {
                final MenuClickedEvent.MenuType menuType = event.getMenuType();
                switch(menuType) {
                    case SELECTION:
                        documentEventBus.fireEvent(new AmendmentsSelectionEvent(selections.get(event.getOption())));
                        break;
                    case ACTION:
                        documentEventBus.fireEvent(new AmendmentsActionEvent(actions.get(event.getOption())));
                        break;
                }

            }
        });
    }

}
