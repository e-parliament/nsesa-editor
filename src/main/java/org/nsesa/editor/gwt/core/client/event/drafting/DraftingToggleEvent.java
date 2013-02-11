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

/**
 * An event used to show/hide the drafting tool
 * User: groza
 * Date: 22/01/13
 * Time: 13:14
 */
public class DraftingToggleEvent extends GwtEvent<DraftingToggleEventHandler> {

    public static final Type<DraftingToggleEventHandler> TYPE = new Type<DraftingToggleEventHandler>();
    private boolean shown;

    public DraftingToggleEvent(boolean shown) {
        this.shown = shown;
    }

    @Override
    public Type<DraftingToggleEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DraftingToggleEventHandler handler) {
        handler.onEvent(this);
    }

    public boolean isShown() {
        return shown;
    }
}
