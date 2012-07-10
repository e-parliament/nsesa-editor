package org.nsesa.editor.gwt.editor.client.ui.actionbar;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentScrollEvent;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentScrollEventHandler;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ActionBarController extends Composite {

    private final ActionBarView view;
    private final ActionBarViewCss actionBarViewCss;

    private final ClientFactory clientFactory;

    private AmendableWidget amendableWidget;

    @Inject
    public ActionBarController(final ClientFactory clientFactory, final ActionBarView view,
                               final ActionBarViewCss actionBarViewCss) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.actionBarViewCss = actionBarViewCss;
        registerListeners();
    }

    private void registerListeners() {
        view.getModifyHandler().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (amendableWidget != null) {
                    clientFactory.getEventBus().fireEvent(new AmendmentContainerCreateEvent(amendableWidget, AmendmentAction.MODIFICATION));
                }
            }
        });

        clientFactory.getEventBus().addHandler(DocumentScrollEvent.TYPE, new DocumentScrollEventHandler() {
            @Override
            public void onEvent(DocumentScrollEvent event) {
                view.asWidget().setVisible(false);
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
        if (amendableWidget != null) {
            final Style style = view.asWidget().getElement().getStyle();
            style.setPosition(Style.Position.ABSOLUTE);
            final int coordinateY = amendableWidget.asWidget().getAbsoluteTop() - view.asWidget().getOffsetHeight();
            style.setTop(coordinateY, Style.Unit.PX);
            style.setLeft(amendableWidget.asWidget().getAbsoluteLeft(), Style.Unit.PX);
            style.setWidth(amendableWidget.asWidget().getOffsetWidth(), Style.Unit.PX);
        }
    }

    public AmendableWidget getAmendableWidget() {
        return amendableWidget;
    }
}
