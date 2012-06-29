package org.nsesa.editor.gwt.core.client.ui.actionbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ActionBarViewImpl extends Composite implements ActionBarView {

    interface MyUiBinder extends UiBinder<Widget, ActionBarViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    Anchor modifyAnchor;
    @UiField
    Anchor bundleAnchor;
    @UiField
    Anchor childAnchor;
    @UiField
    Anchor deleteAnchor;
    @UiField
    Anchor moveAnchor;
    @UiField
    Anchor translateAnchor;
    @UiField
    Label location;

    @Inject
    public ActionBarViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public Widget getModifyHandler() {
        return modifyAnchor;
    }

    @Override
    public Widget getDeleteHandler() {
        return deleteAnchor;
    }

    @Override
    public Widget getBundleHandler() {
        return bundleAnchor;
    }

    @Override
    public Widget getMoveHandler() {
        return moveAnchor;
    }

    @Override
    public Widget getChildHandler() {
        return childAnchor;
    }

    @Override
    public Widget getTranslateHandler() {
        return translateAnchor;
    }

    @Override
    public void setLocation(String location) {
        this.location.setText(location);
    }
}
