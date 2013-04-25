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

import java.util.Arrays;
import java.util.List;

/**
 * An event raised when a <code>AmendmentController</code> object is selected by the user.
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * Date: 24/06/12 21:42
 */
public class OverlayWidgetAwareAddToSelectionEvent extends GwtEvent<OverlayWidgetAwareAddToSelectionEventHandler> {
    public static final Type<OverlayWidgetAwareAddToSelectionEventHandler> TYPE = new Type<OverlayWidgetAwareAddToSelectionEventHandler>();

    private final List<? extends OverlayWidgetAware> selected;

    /**
     * Create <code>AmendmentControllerAddToSelectionEvent</code> with a given input
     * @param selected The selected <code>AmendmentController</code>
     */
    public OverlayWidgetAwareAddToSelectionEvent(final OverlayWidgetAware selected) {
        this.selected = Arrays.asList(selected);
    }

    /**
     * Create <code>AmendmentControllerAddToSelectionEvent</code> with a given list of selected
     * <code>AmendmentController</code>
     * @param selected The list of selected <code>AmendmentController</code>
     */
    public OverlayWidgetAwareAddToSelectionEvent(final List<? extends OverlayWidgetAware> selected) {
        this.selected = selected;
    }

    @Override
    public Type<OverlayWidgetAwareAddToSelectionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(OverlayWidgetAwareAddToSelectionEventHandler handler) {
        handler.onEvent(this);
    }

    /**
     * Returns the list of the selected amendment controllers
     * @return Amendment controllers as List
     */
    public List<? extends OverlayWidgetAware> getSelected() {
        return selected;
    }
}
