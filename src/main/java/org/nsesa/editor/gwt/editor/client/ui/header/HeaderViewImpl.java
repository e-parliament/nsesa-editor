package org.nsesa.editor.gwt.editor.client.ui.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;

import java.util.Arrays;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(EDITOR)
public class HeaderViewImpl extends Composite implements HeaderView {
    interface MyUiBinder extends UiBinder<Widget, HeaderViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    Label name;
    @UiField
    Label roles;

    @Inject
    public HeaderViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public void setLoggedInPersonName(String personName) {
        this.name.setText(personName);
    }

    @Override
    public void setLoggedInPersonRoles(String[] roles) {
        if (roles != null) {
            this.roles.setText(Arrays.asList(roles).toString());
        }
    }
}
