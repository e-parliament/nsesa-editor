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
import org.nsesa.editor.gwt.core.client.ui.document.OverlayWidgetAware;

import java.util.Arrays;
import java.util.List;

/**
 * An event raised when a <code>AmendmentController</code> object is unselected by the user.
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * Date: 24/06/12 21:42
 */
public class OverlayWidgetAwareRemoveFromSelectionEvent extends GwtEvent<OverlayWidgetAwareRemoveFromSelectionEventHandler> {
    public static final Type<OverlayWidgetAwareRemoveFromSelectionEventHandler> TYPE = new Type<OverlayWidgetAwareRemoveFromSelectionEventHandler>();

    private final List<OverlayWidgetAware> selected;

    /**
     * Create <code>AmendmentControllerRemoveFromSelectionEvent</code> object with the given input
     * @param selected The <code>AmendmentController</code> removed from selection
     */
    public OverlayWidgetAwareRemoveFromSelectionEvent(final OverlayWidgetAware selected) {
        this.selected = Arrays.asList(selected);
    }

    /**
     * Create <code>AmendmentControllerRemoveFromSelectionEvent</code> object with the given input list
     * @param selected The <code>AmendmentController</code> list removed from selection
     */
    public OverlayWidgetAwareRemoveFromSelectionEvent(final List<OverlayWidgetAware> selected) {
        this.selected = selected;
    }

    @Override
    public Type<OverlayWidgetAwareRemoveFromSelectionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(OverlayWidgetAwareRemoveFromSelectionEventHandler handler) {
        handler.onEvent(this);
    }

    /**
     * Return the list of <code>AmendmentController</code> removed from selection
     * @return the list of <code>AmendmentController</code> removed from selection
     */
    public List<OverlayWidgetAware> getSelected() {
        return selected;
    }
}
