package org.nsesa.editor.gwt.editor.client.ui.amendments;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentView;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.ui.amendments.header.AmendmentsHeaderView;

import java.util.ArrayList;
import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(EDITOR)
public class AmendmentsPanelViewImpl extends Composite implements AmendmentsPanelView {

    interface MyUiBinder extends UiBinder<Widget, AmendmentsPanelViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField(provided = true)
    AmendmentsHeaderView amendmentsHeaderView;

    @UiField
    VerticalPanel amendments;

    @Inject
    public AmendmentsPanelViewImpl(final AmendmentsHeaderView amendmentsHeaderView) {
        this.amendmentsHeaderView = amendmentsHeaderView;
        final Widget widget = uiBinder.createAndBindUi(this);

        initWidget(widget);
    }

    @Override
    public void refreshAmendments(List<AmendmentView> amendmentsView) {
        if (amendmentsView != null) {
            amendments.clear();
            for(AmendmentView amendmentView : amendmentsView) {
                amendments.add(amendmentView);
            }

        }
    }

}
