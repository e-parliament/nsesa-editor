package org.nsesa.editor.gwt.core.client.ui.amendment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.*;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.AMENDMENT;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(AMENDMENT)
public class AmendmentViewImpl extends Composite implements AmendmentView {
    interface MyUiBinder extends UiBinder<Widget, AmendmentViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    Label title;

    @UiField
    HTMLPanel body;

    @UiField
    Image moreImage;

    @UiField
    Image editImage;

    @UiField
    Image deleteImage;

    public AmendmentViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

    @Override
    public HandlerRegistration addDoubleClickHandler(DoubleClickHandler handler) {
        return addDomHandler(handler, DoubleClickEvent.getType());
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        this.title.setText(title);
    }

    @Override
    public void setBody(String xmlContent) {
        body.getElement().setInnerHTML(xmlContent);
    }

    @Override
    public Element getBody() {
        return body.getElement();
    }

    @Override
    public HasClickHandlers getMoreActionsButton() {
        return moreImage;
    }

    @Override
    public HasClickHandlers getEditButton() {
        return editImage;
    }

    @Override
    public HasClickHandlers getDeleteButton() {
        return deleteImage;
    }
}
