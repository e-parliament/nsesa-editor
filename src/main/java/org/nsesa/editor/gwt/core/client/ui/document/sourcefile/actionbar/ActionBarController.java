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
package org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEventHandler;
import org.nsesa.editor.gwt.core.client.event.document.DocumentScrollEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentScrollEventHandler;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.SourceFileController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar.create.ActionBarCreatePanelController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar.create.ActionBarCreatePanelView;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentAction;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * The controller for the action bar component. This action bar is displayed when hoovering an amendable
 * {@link OverlayWidget} in the source text view displayed in the {@link SourceFileController}.
 * <p/>
 * This toolbar gives access to commonly used actions such as modifying, deleting, creating new elements, as well as
 * displaying information about the location of the {@link OverlayWidget} in the document.
 * <p/>
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class ActionBarController {

    /**
     * The main view for this component.
     */
    protected final ActionBarView view;

    /**
     * The CSS resource.
     */
    protected final ActionBarViewCss actionBarViewCss;

    /**
     * The document scoped event bus for communication between components in a
     * {@link org.nsesa.editor.gwt.core.client.ui.document.DocumentController}.
     */
    protected final DocumentEventBus documentEventBus;

    /**
     * The child controller to pop up a panel with the possibilities for new element to be nested as children or
     * as siblings.
     */
    protected final ActionBarCreatePanelController actionBarCreatePanelController;

    /**
     * The popup panel that will hold the new elements or siblings for this {@link OverlayWidget}.
     */
    protected final PopupPanel popupPanel;

    /**
     * The overlay widget that is currently the target of this action bar component.
     */
    protected OverlayWidget overlayWidget;

    /**
     * The parent sourcefile controller. Gives access to the
     * {@link org.nsesa.editor.gwt.core.client.ui.document.DocumentController}.
     */
    protected SourceFileController sourceFileController;
    private HandlerRegistration modifyHandlerRegistration;
    private HandlerRegistration deleteHandlerRegistration;
    private HandlerRegistration childHandlerRegistration;
    private com.google.web.bindery.event.shared.HandlerRegistration documentScrollEventHandlerRegistration;
    private com.google.web.bindery.event.shared.HandlerRegistration amendmentContainerCreateEventHandlerRegistration;

    @Inject
    public ActionBarController(final DocumentEventBus documentEventBus, final ActionBarView view,
                               final ActionBarViewCss actionBarViewCss,
                               final ActionBarCreatePanelController actionBarCreatePanelController) {
        this.documentEventBus = documentEventBus;
        this.view = view;
        this.actionBarViewCss = actionBarViewCss;

        this.actionBarCreatePanelController = actionBarCreatePanelController;
        this.popupPanel = new DecoratedPopupPanel(true);
        actionBarCreatePanelController.setActionBarController(this);

        registerListeners();
    }

    /**
     * Sets the parent source file controller both on this component, and the child
     * {@link ActionBarCreatePanelController}.
     *
     * @param sourceFileController the parent source file controller
     */
    public void setSourceFileController(SourceFileController sourceFileController) {
        this.sourceFileController = sourceFileController;
        this.actionBarCreatePanelController.setSourceFileController(sourceFileController);
    }

    private void registerListeners() {
        // translate the click on the modify handler into a request to create a new modification amendment
        modifyHandlerRegistration = view.getModifyHandler().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (overlayWidget != null) {
                    documentEventBus.fireEvent(new AmendmentContainerCreateEvent(overlayWidget, null, 0, AmendmentAction.MODIFICATION, sourceFileController.getDocumentController()));
                }
            }
        });
        // translate the click on the delete handler into a request to create a new deletion amendment
        deleteHandlerRegistration = view.getDeleteHandler().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (overlayWidget != null) {
                    documentEventBus.fireEvent(new AmendmentContainerCreateEvent(overlayWidget, null, 0, AmendmentAction.DELETION, sourceFileController.getDocumentController()));
                }
            }
        });
        // translate the click on the child handler into a request to create a new element amendment
        childHandlerRegistration = view.getChildHandler().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (overlayWidget != null) {
                    actionBarCreatePanelController.setOverlayWidget(overlayWidget);
                    final ActionBarCreatePanelView createPanelView = actionBarCreatePanelController.getView();
                    createPanelView.asWidget().setVisible(true);
                    popupPanel.setWidget(createPanelView);
                    popupPanel.showRelativeTo(view.getChildHandler());
                    popupPanel.show();
                }
            }
        });
        // we hide this toolbar as soon as the user scrolls the content
        documentScrollEventHandlerRegistration = documentEventBus.addHandler(DocumentScrollEvent.TYPE, new DocumentScrollEventHandler() {
            @Override
            public void onEvent(DocumentScrollEvent event) {
                if (event.getDocumentController() == sourceFileController.getDocumentController() || sourceFileController.getDocumentController() == null) {
                    view.asWidget().setVisible(false);
                }
            }
        });

        // hide this panel as soon as the an amendment create event was published
        amendmentContainerCreateEventHandlerRegistration = documentEventBus.addHandler(AmendmentContainerCreateEvent.TYPE, new AmendmentContainerCreateEventHandler() {
            @Override
            public void onEvent(AmendmentContainerCreateEvent event) {
                popupPanel.hide();
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        modifyHandlerRegistration.removeHandler();
        deleteHandlerRegistration.removeHandler();
        childHandlerRegistration.removeHandler();
        documentScrollEventHandlerRegistration.removeHandler();
        amendmentContainerCreateEventHandlerRegistration.removeHandler();
    }

    /**
     * Get the view for this component.
     *
     * @return the view
     */
    public ActionBarView getView() {
        return view;
    }

    /**
     * Set the current overlay widget - normally done via the
     * {@link #attach(org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget, org.nsesa.editor.gwt.core.client.ui.document.DocumentController)} method.
     *
     * @param overlayWidget the overlay widget
     */
    public void setOverlayWidget(final OverlayWidget overlayWidget) {
        this.overlayWidget = overlayWidget;
    }

    /**
     * Enable or disable the delete link to be visible.
     *
     * @param allowDelete <tt>true</tt> if you want the delete link to be visible.
     */
    public void setAllowDelete(final boolean allowDelete) {
        view.getDeleteHandler().setVisible(!allowDelete);
    }

    /**
     * Enable or disable the modify link to be visible.
     *
     * @param allowModify <tt>true</tt> if you want the modify link to be visible.
     */
    public void setAllowModify(final boolean allowModify) {
        view.getModifyHandler().setVisible(!allowModify);
    }

    /**
     * Enable or disable the move link to be visible.
     *
     * @param allowMove <tt>true</tt> if you want the move link to be visible.
     */
    public void setAllowMove(final boolean allowMove) {
        view.getMoveHandler().setVisible(!allowMove);
    }

    /**
     * Enable or disable the bundle link to be visible.
     *
     * @param allowBundle <tt>true</tt> if you want the bundle link to be visible.
     */
    public void setAllowBundle(final boolean allowBundle) {
        view.getBundleHandler().setVisible(!allowBundle);
    }

    /**
     * Enable or disable the new element link to be visible.
     *
     * @param allowChild <tt>true</tt> if you want the new element link to be visible.
     */
    public void setAllowChild(final boolean allowChild) {
        view.getChildHandler().setVisible(!allowChild);
    }

    /**
     * Enable or disable the translate link to be visible.
     *
     * @param allowTranslate <tt>true</tt> if you want the translate link to be visible.
     */
    public void setAllowTranslate(final boolean allowTranslate) {
        view.getTranslateHandler().setVisible(!allowTranslate);
    }

    /**
     * Set the location to be shown for the current {@link OverlayWidget}.
     *
     * @param location the location to be shown
     */
    public void setLocation(final String location) {
        view.setLocation(location);
    }

    /**
     * 'Attaches' itself to the given <tt>target</tt> amendable overlay widget, changing the CSS style
     * of the overlay widget, and position the toolbar right above it via a call to {@link #adaptPosition(com.google.gwt.user.client.ui.Widget)}.
     *
     * @param target the target overlay widget
     * @param target the containing document controller
     */
    public void attach(final OverlayWidget target, final DocumentController documentController) {
        // only perform the world if the new target overlay widget is actually different
        // this made a huge difference in re-flow/repainting of the browser
        if (overlayWidget != target) {
            // if our action bar view has not yet been added to the rootpanel, then do so now.
            if (!view.asWidget().isAttached()) {
                RootPanel.get().add(view);
            }
            // hide the popup panel with
            popupPanel.hide();

            //make sure it is visible
            view.asWidget().setVisible(true);

            // if we had a previous widget that was selected, make sure to remove its special action css
            // done this way because onmouseout is not reliable enough
            if (overlayWidget != null) {
                overlayWidget.asWidget().removeStyleName(actionBarViewCss.hover());
            }

            this.overlayWidget = target;
            overlayWidget.asWidget().addStyleName(actionBarViewCss.hover());

            // position our action bar exactly above the amendable widget
            adaptPosition(documentController.getSourceFileController().getContentController().getView().getContentPanel());
        }
    }

    /**
     * Adapts the position of this 'hoovering' toolbar to be placed just above the {@link OverlayWidget}.
     */
    public void adaptPosition(final Widget container) {
        // hide the panel with our creation elements
        actionBarCreatePanelController.getView().asWidget().setVisible(false);
        if (overlayWidget != null) {
            final Style style = view.asWidget().getElement().getStyle();
            final int coordinateY = overlayWidget.asWidget().getAbsoluteTop() - (view.asWidget().getOffsetHeight() - 1);
            style.setTop(coordinateY, Style.Unit.PX);
            style.setLeft(overlayWidget.asWidget().getAbsoluteLeft(), Style.Unit.PX);

            // we need to limit the width to make sure it does not
            final int width = (container.getOffsetWidth() + container.getAbsoluteLeft()) - overlayWidget.asWidget().getAbsoluteLeft();
            style.setWidth((width), Style.Unit.PX);
        }
    }

    /**
     * The overlay widget we are considered to be 'attached' to.
     *
     * @return the overlay widget
     */
    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }
}
