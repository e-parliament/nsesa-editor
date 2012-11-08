package org.nsesa.editor.gwt.editor.client.ui.actionbar.create;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(EDITOR)
public class ActionBarCreatePanelViewImpl extends Composite implements ActionBarCreatePanelView {

    interface MyUiBinder extends UiBinder<Widget, ActionBarCreatePanelViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private UIListener uiListener;

    @UiField
    Label siblingTitle;
    @UiField
    VerticalPanel siblingPanel;

    @UiField
    HTMLPanel separator;

    @UiField
    VerticalPanel childPanel;

    @UiField
    Label childTitle;

    @Inject
    public ActionBarCreatePanelViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public void setUIListener(final UIListener uiListener) {
        this.uiListener = uiListener;
    }

    public void addChildAmendableWidget(final String title, final AmendableWidget amendableWidget) {
        childTitle.setVisible(true);
        Anchor w = new Anchor(title);
        w.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                uiListener.onClick(amendableWidget);
            }
        });
        childPanel.add(w);
    }

    public void addSiblingAmendableWidget(final String title, final AmendableWidget amendableWidget) {
        siblingTitle.setVisible(true);
        Anchor w = new Anchor(title);
        w.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        uiListener.onClick(amendableWidget);
                    }
                });
        siblingPanel.add(w);
    }

    public void setSeparatorVisible(final boolean visible) {
        separator.setVisible(visible);
    }

    public void clearAmendableWidgets() {
        siblingPanel.clear();
        childPanel.clear();
        siblingTitle.setVisible(false);
        childTitle.setVisible(false);
    }

    @Override
    public void attach() {
        onAttach();
    }


}
