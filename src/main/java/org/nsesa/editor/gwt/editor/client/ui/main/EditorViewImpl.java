package org.nsesa.editor.gwt.editor.client.ui.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Date: 24/06/12 16:39
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EditorViewImpl extends Composite implements EditorView {

    interface MyUiBinder extends UiBinder<Widget, EditorViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @Inject
    public EditorViewImpl(final EventBus eventBus) {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }
}
