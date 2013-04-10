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
package org.nsesa.editor.gwt.core.client.event.amendments;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.util.Selection;

/**
 * An event raised when the user performs a specific <code>Selection</code> in
 * {@link org.nsesa.editor.gwt.core.client.ui.document.amendments.AmendmentsPanelView} view
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 29/11/12 13:15
 */
public class AmendmentControllerSelectionEvent extends GwtEvent<AmendmentControllerSelectionEventHandler> {
    public static final Type<AmendmentControllerSelectionEventHandler> TYPE = new Type<AmendmentControllerSelectionEventHandler>();

    private Selection<AmendmentController> selection;

    /**
     * Create <code>AmendmentControllerSelectionEvent</code> object with the given input
     * @param selection The selection chosen  by the user
     */
    public AmendmentControllerSelectionEvent(Selection<AmendmentController> selection) {
        this.selection = selection;
    }

    @Override
    public Type<AmendmentControllerSelectionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentControllerSelectionEventHandler handler) {
        handler.onEvent(this);
    }

    /**
     * Return the selection chosen  by the user
     * @return The selection chosen  by the user
     */
    public Selection<AmendmentController> getSelection() {
        return selection;
    }
}
