package org.nsesa.editor.gwt.core.client.ui.actionbar;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendableWidget;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ActionBarController extends Composite {

    private final ActionBarView view;
    private final ActionBarViewCss actionBarViewCss;

    private AmendableWidget amendableWidget;

    @Inject
    public ActionBarController(final ActionBarView view, final ActionBarViewCss actionBarViewCss) {
        this.view = view;
        this.actionBarViewCss = actionBarViewCss;
        registerListeners();
    }

    private void registerListeners() {

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
            if (amendableWidget != null) {
                amendableWidget.asWidget().removeStyleName(actionBarViewCss.hover());
            }
            if (view.asWidget().getParent() != null) {
                view.asWidget().removeFromParent();
            }

            this.amendableWidget = target;
            final Element element = this.amendableWidget.asWidget().getElement();
            DOM.insertChild(element, view.asWidget().getElement(), 0);
            setLocation(this.amendableWidget.asWidget().getElement().getNodeName());
            this.amendableWidget.asWidget().addStyleName(actionBarViewCss.hover());
        }
    }
}
