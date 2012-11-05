package org.nsesa.editor.gwt.core.client.ui.deadline;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class DeadlineViewImpl extends Composite implements DeadlineView {

    interface MyUiBinder extends UiBinder<Widget, DeadlineViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);


    @Inject
    public DeadlineViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        if (!GWT.isScript())
            this.setTitle(this.getClass().getName());
    }

    @UiField
    DeadlineViewCss style;
    @UiField
    HTML deadline;


    public void setDeadline(String deadlineString) {
        this.deadline.setHTML(deadlineString);
    }

    public void setPastStyle() {
        addStyleName(style.past());
    }

    public void set24HourStyle() {
        addStyleName(style.past24hours());
    }

    public void set1HourStyle() {
        addStyleName(style.past1hours());
    }
}
