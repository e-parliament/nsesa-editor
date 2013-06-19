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
package org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.overlay.TextUtils;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Default implementation of the {@link ActionBarView} using UIBinder.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class ActionBarViewImpl extends Composite implements ActionBarView {

    interface MyUiBinder extends UiBinder<Widget, ActionBarViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    Anchor modifyAnchor;
    @UiField
    Anchor bundleAnchor;
    @UiField
    Anchor childAnchor;
    @UiField
    Anchor deleteAnchor;
    @UiField
    Anchor moveAnchor;
    @UiField
    Anchor translateAnchor;
    @UiField
    InlineHTML location;
    @UiField
    HTMLPanel actionPanel;

    @Inject
    public ActionBarViewImpl() {
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        //show class name tool tip in hosted mode
        if (!GWT.isScript())
            widget.setTitle(this.getClass().getName());

    }

    @Override
    public void addWidget(IsWidget isWidget) {
        actionPanel.add(isWidget);
    }

    @Override
    public ComplexPanel getActionPanel() {
        return actionPanel;
    }

    @Override
    public FocusWidget getModifyHandler() {
        return modifyAnchor;
    }

    @Override
    public FocusWidget getDeleteHandler() {
        return deleteAnchor;
    }

    @Override
    public FocusWidget getBundleHandler() {
        return bundleAnchor;
    }

    @Override
    public FocusWidget getMoveHandler() {
        return moveAnchor;
    }

    @Override
    public FocusWidget getChildHandler() {
        return childAnchor;
    }

    @Override
    public FocusWidget getTranslateHandler() {
        return translateAnchor;
    }

    @Override
    public void setLocation(String location) {
        if (location != null) {
            this.location.setText(TextUtils.capitalize(location));
        } else {
            this.location.setText("");
        }
    }

    @Override
    public void attach() {
        onAttach();
    }
}
