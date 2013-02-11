/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.editor.client.event.amendments;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

import java.util.List;

/**
 * An event raised when the user select a selection
 * User: groza
 * Date: 29/11/12
 * Time: 10:42
 */
public class AmendmentControllerSelectionActionEvent extends GwtEvent<AmendmentControllerSelectionActionEventHandler> {

    public static interface Action {
        void execute(List<AmendmentController> amendmentControllers);
    }

    public static final Type<AmendmentControllerSelectionActionEventHandler> TYPE = new Type<AmendmentControllerSelectionActionEventHandler>();

    private final Action action;

    public AmendmentControllerSelectionActionEvent(Action action) {
        this.action = action;
    }

    @Override
    public Type<AmendmentControllerSelectionActionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentControllerSelectionActionEventHandler handler) {
        handler.onEvent(this);
    }

    public Action getAction() {
        return action;
    }
}
