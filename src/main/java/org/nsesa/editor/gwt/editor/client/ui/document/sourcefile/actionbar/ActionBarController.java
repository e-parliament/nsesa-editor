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
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentScrollEvent;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentScrollEventHandler;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;
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

    private AmendableWidget amendableWidget;

    private DocumentController documentController;

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

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
        this.actionBarCreatePanelController.setDocumentController(documentController);
    }

    private void registerListeners() {
        view.getModifyHandler().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (amendableWidget != null) {
                    documentEventBus.fireEvent(new AmendmentContainerCreateEvent(amendableWidget, null, 0, AmendmentAction.MODIFICATION, documentController));
                }
            }
        });
        view.getDeleteHandler().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (amendableWidget != null) {
                    documentEventBus.fireEvent(new AmendmentContainerCreateEvent(amendableWidget, null, 0, AmendmentAction.DELETION, documentController));
                }
            }
        });
        view.getChildHandler().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (amendableWidget != null) {
                    actionBarCreatePanelController.setAmendableWidget(amendableWidget);
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
                if (event.getDocumentController() == documentController || documentController == null) {
                    view.asWidget().setVisible(false);
                }
            }
        });
    }

    public ActionBarView getView() {
        return view;
    }

    public void setAmendableWidget(final AmendableWidget amendableWidget) {
        this.amendableWidget = amendableWidget;
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

    public void attach(AmendableWidget target) {
        if (amendableWidget != target) {

            // if our action bar view has not yet been added to the rootpanel, then do so now.
            if (!view.asWidget().isAttached()) {
                RootPanel.get().add(view);
            }

            popupPanel.hide();

            //make sure it is visible
            view.asWidget().setVisible(true);

            // if we had a previous widget that was selected, make sure to remove its special action css
            // done this way because onmouseout is not reliable enough
            if (amendableWidget != null) {
                amendableWidget.asWidget().removeStyleName(actionBarViewCss.hover());
            }

            this.amendableWidget = target;
            amendableWidget.asWidget().addStyleName(actionBarViewCss.hover());

            // position our action bar exactly above the amendable widget
            adaptPosition();
        }
    }

    public void adaptPosition() {
        // hide the panel with our creation elements
        actionBarCreatePanelController.getView().asWidget().setVisible(false);
        if (amendableWidget != null && amendableWidget.getRoot() != null) {
            final Style style = view.asWidget().getElement().getStyle();
            final int coordinateY = amendableWidget.asWidget().getAbsoluteTop() - (view.asWidget().getOffsetHeight() - 1) - 70;
            style.setTop(coordinateY, Style.Unit.PX);
            final int x = amendableWidget.asWidget().getAbsoluteLeft();
            style.setLeft(x, Style.Unit.PX);
            final int width = amendableWidget.getRoot().asWidget().getOffsetWidth();
            final int offsetRoot = amendableWidget.getRoot().asWidget().getAbsoluteLeft();
            style.setWidth((width + offsetRoot) - x, Style.Unit.PX);
        }
    }

    public AmendableWidget getAmendableWidget() {
        return amendableWidget;
    }
}
