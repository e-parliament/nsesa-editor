package org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.actionbar.create;

import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.actionbar.ActionBarController;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(EDITOR)
public class ActionBarCreatePanelController {

    private final ActionBarCreatePanelView view;
    private final DocumentEventBus documentEventBus;
    private DocumentController documentController;

    private ActionBarController actionBarController;

    private AmendableWidget amendableWidget;

    @Inject
    public ActionBarCreatePanelController(final DocumentEventBus documentEventBus,
                                          final ActionBarCreatePanelView view) {
        this.documentEventBus = documentEventBus;
        this.view = view;
        registerListeners();
    }

    private void registerListeners() {
        view.setUIListener(new ActionBarCreatePanelView.UIListener() {
            @Override
            public void onClick(final AmendableWidget newChild, final boolean sibling) {
                documentEventBus.fireEvent(new AmendmentContainerCreateEvent(newChild,
                        sibling ? amendableWidget.getParentAmendableWidget() : amendableWidget,
                        sibling ? amendableWidget.getIndex() + 1 : 0,
                        AmendmentAction.CREATION, documentController));
            }
        });
    }

    public ActionBarCreatePanelView getView() {
        return view;
    }

    public void setAmendableWidget(final AmendableWidget amendableWidget) {
        this.amendableWidget = amendableWidget;

        // clean up whatever is there
        view.clearAmendableWidgets();

        // add all the possible siblings
        LinkedHashMap<String, AmendableWidget> allowedSiblings = documentController.getCreator().getAllowedSiblings(documentController, amendableWidget);
        for (final Map.Entry<String, AmendableWidget> entry : allowedSiblings.entrySet()) {
            view.addSiblingAmendableWidget(entry.getKey(), entry.getValue());
        }
        // add all the children
        LinkedHashMap<String, AmendableWidget> allowedChildren = documentController.getCreator().getAllowedChildren(documentController, amendableWidget);
        for (final Map.Entry<String, AmendableWidget> entry : allowedChildren.entrySet()) {
            view.addChildAmendableWidget(entry.getKey(), entry.getValue());
        }

        // show spacer if both siblings and children are possible
        view.setSeparatorVisible(!allowedSiblings.isEmpty() && !allowedChildren.isEmpty());
    }


    public void setActionBarController(ActionBarController actionBarController) {
        this.actionBarController = actionBarController;
    }

    public AmendableWidget getAmendableWidget() {
        return amendableWidget;
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }
}
