package org.nsesa.editor.gwt.core.client.ui.actionbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
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
    public FocusWidget getModifyHandler() {
        return modifyAnchor;
    }

    @Override
    public FocusWidget getDeleteHandler() {
        return deleteAnchor;
    }

    @Override
    public FocusWidget getBundleHandler() {
        return bundleAnchor;
    }

    @Override
    public FocusWidget getMoveHandler() {
        return moveAnchor;
    }

    @Override
    public FocusWidget getChildHandler() {
        return childAnchor;
    }

    @Override
    public FocusWidget getTranslateHandler() {
        return translateAnchor;
    }

    @Override
    public void setLocation(String location) {
        this.location.setText(location);
    }

    @Override
    public void attach() {
        onAttach();
    }
}
