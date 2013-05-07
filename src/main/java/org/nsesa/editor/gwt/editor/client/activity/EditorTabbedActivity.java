/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
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
        } catch (NumberFormatException nfe) {
            //do nothing
        }
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        //editorController.getView().switchToTab(tabIndex);
        panel.setWidget(editorController.getView());
    }

}
