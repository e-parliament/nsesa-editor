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
package org.nsesa.editor.gwt.core.client.event.selection;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.document.OverlayWidgetAware;

import java.util.List;

/**
 * An event raised when the user select an action in
 * {@link org.nsesa.editor.gwt.amendment.client.ui.document.amendments.AmendmentsPanelView}.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 29/11/12 13:15
 */
public class OverlayWidgetAwareSelectionActionEvent<T> extends GwtEvent<OverlayWidgetAwareSelectionActionEventHandler> {

    /**
     * An interface to specify what type of actions will be performed against selected amendment controllers
     */
    public static interface Action<T> {
        void execute(List<T> OverlayWidgetAwares);
    }

    public static final Type<OverlayWidgetAwareSelectionActionEventHandler> TYPE = new Type<OverlayWidgetAwareSelectionActionEventHandler>();

    private final Action<T> action;

    /**
     * Create <code>AmendmentControllerSelectionActionEvent</code> object with the given input
     * @param action The selected action
     */
    public OverlayWidgetAwareSelectionActionEvent(Action<T> action) {
        this.action = action;
    }

    @Override
    public Type<OverlayWidgetAwareSelectionActionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(OverlayWidgetAwareSelectionActionEventHandler handler) {
        handler.onEvent(this);
    }

    /**
     * Return the action selected by the user
     * @return action The selected action
     */
    public Action<T> getAction() {
        return action;
    }
}
