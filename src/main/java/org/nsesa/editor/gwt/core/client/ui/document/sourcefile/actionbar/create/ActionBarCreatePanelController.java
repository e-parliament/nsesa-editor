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
package org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar.create;

import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEvent;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.SourceFileController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar.ActionBarController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentAction;

import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * A child controller of the {@link ActionBarController} responsible for showing the allowed children and siblings
 * of the currently selected {@link OverlayWidget}.
 * <p/>
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(EDITOR)
public class ActionBarCreatePanelController {

    /**
     * The main view of this component.
     */
    protected final ActionBarCreatePanelView view;

    /**
     * The document scoped event bus.
     */
    protected final DocumentEventBus documentEventBus;

    /**
     * The parent sourcefile controller.
     */
    protected SourceFileController sourceFileController;

    /**
     * The parent sourcefile controller.
     */
    protected ActionBarController actionBarController;

    /**
     * The current overlay widget we're listing the allowed child and sibling types for.
     */
    protected OverlayWidget overlayWidget;

    private int highlightedNumber = -1;

    @Inject
    public ActionBarCreatePanelController(final DocumentEventBus documentEventBus,
                                          final ActionBarCreatePanelView view) {
        this.documentEventBus = documentEventBus;
        this.view = view;
        registerListeners();
    }

    private void registerListeners() {
        // register a handler for the click event on the name of the overlay widget as a child or sibling
        view.setUIListener(new ActionBarCreatePanelView.UIListener() {
            @Override
            public void onClick(final OverlayWidget newChild, final boolean sibling) {
                documentEventBus.fireEvent(new AmendmentContainerCreateEvent(newChild,
                        sibling ? overlayWidget.getParentOverlayWidget() : overlayWidget,
                        sibling ? overlayWidget.getIndex() + 1 : 0,
                        AmendmentAction.CREATION, sourceFileController.getDocumentController()));
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        view.setUIListener(null);
    }

    /**
     * Return the view for this component.
     *
     * @return the view
     */
    public ActionBarCreatePanelView getView() {
        return view;
    }

    /**
     * Sets the current {@link OverlayWidget} to list the allowed child and sibling types for.
     * <p/>
     * The allowed children and siblings are coming from the injected
     * {@link org.nsesa.editor.gwt.core.client.ui.document.DocumentController#getCreator()}.
     *
     * @param overlayWidget
     */
    public void setOverlayWidget(final OverlayWidget overlayWidget) {
        this.overlayWidget = overlayWidget;

        // clean up whatever is there
        view.clearChildOverlayWidgets();

        // add all the possible siblings
        List<OverlayWidget> allowedSiblings = sourceFileController.getDocumentController().getCreator().getAllowedSiblings(sourceFileController.getDocumentController(), overlayWidget);
        for (final OverlayWidget entry : allowedSiblings) {
            view.addSiblingAmendableWidget(entry.getType(), entry);
        }
        // add all the children
        List<OverlayWidget> allowedChildren = sourceFileController.getDocumentController().getCreator().getAllowedChildren(sourceFileController.getDocumentController(), overlayWidget);
        for (final OverlayWidget entry : allowedChildren) {
            view.addChildAmendableWidget(entry.getType(), entry);
        }

        // show separator if both siblings and children are possible
        view.setSeparatorVisible(!allowedSiblings.isEmpty() && !allowedChildren.isEmpty());
    }


    /**
     * Set the parent action bar controller.
     *
     * @param actionBarController the parent action bar controller
     */
    public void setActionBarController(ActionBarController actionBarController) {
        this.actionBarController = actionBarController;
    }

    public void highlightNext() {
        if (overlayWidget != null) {
            view.setHighlight(highlightedNumber + 1);
            // redraw the view
            setOverlayWidget(overlayWidget);
        }
    }

    public void highlightPrevious() {
        if (overlayWidget != null) {
            view.setHighlight(highlightedNumber - 1);
            // redraw the view
            setOverlayWidget(overlayWidget);
        }
    }

    /**
     * Get the current overlay widget
     *
     * @return the overlay widget
     */
    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }

    /**
     * Set the parent sourcefile controller.
     *
     * @param sourceFileController the parent sourcefile controller
     */
    public void setSourceFileController(SourceFileController sourceFileController) {
        this.sourceFileController = sourceFileController;
    }
}
