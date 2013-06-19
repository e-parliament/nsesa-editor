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
 * An event raised by the application to include the amendment controllers in the selected list.
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * Date: 24/06/12 21:42
 */
public class OverlayWidgetAwareSelectedEvent extends GwtEvent<OverlayWidgetAwareSelectedEventHandler> {
    public static final Type<OverlayWidgetAwareSelectedEventHandler> TYPE = new Type<OverlayWidgetAwareSelectedEventHandler>();

    private final List<? extends OverlayWidgetAware> selected;

    /**
     * Create <code>AmendmentControllerSelectedEvent</code> with the given input list
     * @param selected The list of <code>AmendmentController</code> that should be included later in selection
     */
    public OverlayWidgetAwareSelectedEvent(List<? extends OverlayWidgetAware> selected) {
        this.selected = selected;
    }

    @Override
    public Type<OverlayWidgetAwareSelectedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(OverlayWidgetAwareSelectedEventHandler handler) {
        handler.onEvent(this);
    }

    /**
     * Return The list of <code>AmendmentController</code> that should be included in selection
     * @return The list of <code>AmendmentController</code> that should be included in selection
     */
    public List<? extends OverlayWidgetAware> getSelected() {
        return selected;
    }
}
