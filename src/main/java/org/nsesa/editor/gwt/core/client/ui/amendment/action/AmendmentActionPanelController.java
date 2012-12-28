package org.nsesa.editor.gwt.core.client.ui.amendment.action;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.AMENDMENT;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(AMENDMENT)
public class AmendmentActionPanelController {

    private final AmendmentActionPanelView view;

    private final ClientFactory clientFactory;

    private final PopupPanel popupPanel = new PopupPanel(true, false);

    @Inject
    public AmendmentActionPanelController(final ClientFactory clientFactory,
                                          final AmendmentActionPanelView amendmentActionPanelView) {
        this.clientFactory = clientFactory;

        this.view = amendmentActionPanelView;
        this.popupPanel.setWidget(amendmentActionPanelView);
        registerListeners();
    }

    private void registerListeners() {

    }

    public void show(int x, int y) {
        popupPanel.setPopupPosition(x, y);
        popupPanel.show();
    }

    public void hide() {
        popupPanel.hide();
    }
}
