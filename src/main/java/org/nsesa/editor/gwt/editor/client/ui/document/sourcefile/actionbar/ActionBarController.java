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
package org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.actionbar;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEventHandler;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentScrollEvent;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentScrollEventHandler;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.SourceFileController;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.actionbar.create.ActionBarCreatePanelController;
import org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.actionbar.create.ActionBarCreatePanelView;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class ActionBarController {

    private final ActionBarView view;
    private final ActionBarViewCss actionBarViewCss;

    private final DocumentEventBus documentEventBus;

    private final ActionBarCreatePanelController actionBarCreatePanelController;

    private final PopupPanel popupPanel;

    private OverlayWidget overlayWidget;

    private SourceFileController sourceFileController;

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

    public void setSourceFileController(SourceFileController sourceFileController) {
        this.sourceFileController = sourceFileController;
        this.actionBarCreatePanelController.setSourceFileController(sourceFileController);
    }

    private void registerListeners() {
        view.getModifyHandler().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (overlayWidget != null) {
                    documentEventBus.fireEvent(new AmendmentContainerCreateEvent(overlayWidget, null, 0, AmendmentAction.MODIFICATION, sourceFileController.getDocumentController()));
                }
            }
        });
        view.getDeleteHandler().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (overlayWidget != null) {
                    documentEventBus.fireEvent(new AmendmentContainerCreateEvent(overlayWidget, null, 0, AmendmentAction.DELETION, sourceFileController.getDocumentController()));
                }
            }
        });
        view.getChildHandler().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (overlayWidget != null) {
                    actionBarCreatePanelController.setOverlayWidget(overlayWidget);
                    final ActionBarCreatePanelView panelView = actionBarCreatePanelController.getView();
                    panelView.asWidget().setVisible(true);
                    popupPanel.setWidget(panelView);
                    popupPanel.showRelativeTo(view.getChildHandler());
                    popupPanel.show();
                }
            }
        });
        documentEventBus.addHandler(DocumentScrollEvent.TYPE, new DocumentScrollEventHandler() {
            @Override
            public void onEvent(DocumentScrollEvent event) {
                if (event.getDocumentController() == sourceFileController.getDocumentController() || sourceFileController.getDocumentController() == null) {
                    view.asWidget().setVisible(false);
                }
            }
        });

        documentEventBus.addHandler(AmendmentContainerCreateEvent.TYPE, new AmendmentContainerCreateEventHandler() {
            @Override
            public void onEvent(AmendmentContainerCreateEvent event) {
                popupPanel.hide();
            }
        });
    }

    public ActionBarView getView() {
        return view;
    }

    public void setOverlayWidget(final OverlayWidget overlayWidget) {
        this.overlayWidget = overlayWidget;
    }

    public void setAllowDelete(final boolean allowDelete) {
        view.getDeleteHandler().setVisible(!allowDelete);
    }

    public void setAllowModify(final boolean allowModify) {
        view.getModifyHandler().setVisible(!allowModify);
    }

    public void setAllowMove(final boolean allowMove) {
        view.getMoveHandler().setVisible(!allowMove);
    }

    public void setAllowBundle(final boolean allowBundle) {
        view.getBundleHandler().setVisible(!allowBundle);
    }

    public void setAllowChild(final boolean allowChild) {
        view.getChildHandler().setVisible(!allowChild);
    }

    public void setAllowTranslate(final boolean allowTranslate) {
        view.getTranslateHandler().setVisible(!allowTranslate);
    }

    public void setLocation(String location) {
        view.setLocation(location);
    }

    public void attach(OverlayWidget target) {
        if (overlayWidget != target) {

            // if our action bar view has not yet been added to the rootpanel, then do so now.
            if (!view.asWidget().isAttached()) {
                RootPanel.get().add(view);
            }

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
            adaptPosition();
        }
    }

    public void adaptPosition() {
        // hide the panel with our creation elements
        actionBarCreatePanelController.getView().asWidget().setVisible(false);
        if (overlayWidget != null && overlayWidget.getRoot() != null) {
            final Style style = view.asWidget().getElement().getStyle();
            final int coordinateY = overlayWidget.asWidget().getAbsoluteTop() - (view.asWidget().getOffsetHeight() - 1) - 70;
            style.setTop(coordinateY, Style.Unit.PX);
            final int x = overlayWidget.asWidget().getAbsoluteLeft();
            style.setLeft(x, Style.Unit.PX);
            final int width = overlayWidget.getRoot().asWidget().getOffsetWidth();
            final int offsetRoot = overlayWidget.getRoot().asWidget().getAbsoluteLeft();
            style.setWidth((width + offsetRoot) - x, Style.Unit.PX);
        }
    }

    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }
}
