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
package org.nsesa.editor.gwt.core.client.event.widget;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

import java.util.List;

/**
 *  An event indicating a change in the actual structure of the document. That happens when
 *  you move up and down widgets or delete some widgets
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 28/05/13 10:13
 */
public class OverlayWidgetStructureChangeEvent extends GwtEvent<OverlayWidgetStructureChangeEventHandler> {

    public static final Type<OverlayWidgetStructureChangeEventHandler> TYPE = new Type<OverlayWidgetStructureChangeEventHandler>();

    private List<OverlayWidget> affectedWidgets;

    public OverlayWidgetStructureChangeEvent(List<OverlayWidget> affectedWidgets) {
        this.affectedWidgets = affectedWidgets;
    }

    @Override
    public Type<OverlayWidgetStructureChangeEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(OverlayWidgetStructureChangeEventHandler handler) {
        handler.onEvent(this);
    }

    public List<OverlayWidget> getAffectedWidgets() {
        return affectedWidgets;
    }

}
