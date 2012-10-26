package org.nsesa.editor.gwt.editor.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.nsesa.editor.gwt.editor.client.ui.main.EditorController;

/**
 * An activity to switch the tabs in the editor
 * User: sgroza
 * Date: 25/10/12
 * Time: 10:01
 */
public class EditorTabbedActivity extends AbstractActivity {

    private Provider<EditorTabbedPlace> provider;
    private EditorController editorController;
    private int tabIndex;

    @Inject
    public EditorTabbedActivity(Provider<EditorTabbedPlace> provider, EditorController editorController) {
        this.provider = provider;
        this.editorController = editorController;
    }

    public void init(EditorTabbedPlace editorTabbedPlace) {
        try {
            tabIndex = Integer.valueOf(editorTabbedPlace.getPlaceName());
        } catch(NumberFormatException nfe) {
            //do nothing
        }
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        editorController.getView().switchToTab(tabIndex);
        panel.setWidget(editorController.getView());
    }

}
