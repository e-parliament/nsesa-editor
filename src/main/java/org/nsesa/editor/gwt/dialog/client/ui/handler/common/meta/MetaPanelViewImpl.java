package org.nsesa.editor.gwt.dialog.client.ui.handler.common.meta;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DIALOG;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(DIALOG)
public class MetaPanelViewImpl extends Composite implements MetaPanelView {

    interface MyUiBinder extends UiBinder<Widget, MetaPanelViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private final ServiceFactory serviceFactory;

    @UiField
    TextArea justification;
    @UiField
    TextArea notes;

    @Inject
    public MetaPanelViewImpl(final ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }
}
