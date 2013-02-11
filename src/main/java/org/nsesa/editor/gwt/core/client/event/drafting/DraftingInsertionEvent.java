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
package org.nsesa.editor.gwt.core.client.event.drafting;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * An event occured when the user wants to draft an amendment
 * User: groza
 * Date: 17/01/13
 * Time: 15:24
 */
public class DraftingInsertionEvent extends GwtEvent<DraftingInsertionEventHandler> {

    public static final Type<DraftingInsertionEventHandler> TYPE = new Type<DraftingInsertionEventHandler>();

    private AmendableWidget amendableWidget;

    public DraftingInsertionEvent(AmendableWidget amendableWidget) {
        this.amendableWidget = amendableWidget;
    }

    @Override
    public Type<DraftingInsertionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DraftingInsertionEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendableWidget getAmendableWidget() {
        return amendableWidget;
    }
}
