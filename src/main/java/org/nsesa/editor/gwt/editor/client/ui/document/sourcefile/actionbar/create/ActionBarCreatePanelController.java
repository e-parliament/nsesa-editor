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
package org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.actionbar.create;

import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Occurrence;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.SourceFileController;
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
    private SourceFileController sourceFileController;

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
                        AmendmentAction.CREATION, sourceFileController.getDocumentController()));
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
        LinkedHashMap<AmendableWidget, Occurrence> allowedSiblings = sourceFileController.getDocumentController().getCreator().getAllowedSiblings(sourceFileController.getDocumentController(), amendableWidget);
        for (final Map.Entry<AmendableWidget, Occurrence> entry : allowedSiblings.entrySet()) {
            view.addSiblingAmendableWidget(entry.getKey().getType(), entry.getKey());
        }
        // add all the children
        LinkedHashMap<AmendableWidget, Occurrence> allowedChildren = sourceFileController.getDocumentController().getCreator().getAllowedChildren(sourceFileController.getDocumentController(), amendableWidget);
        for (final Map.Entry<AmendableWidget, Occurrence> entry : allowedChildren.entrySet()) {
            view.addChildAmendableWidget(entry.getKey().getType(), entry.getKey());
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

    public void setSourceFileController(SourceFileController sourceFileController) {
        this.sourceFileController = sourceFileController;
    }
}
