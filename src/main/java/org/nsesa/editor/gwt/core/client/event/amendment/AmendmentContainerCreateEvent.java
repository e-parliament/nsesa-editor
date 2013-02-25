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
package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerCreateEvent extends GwtEvent<AmendmentContainerCreateEventHandler> {

    public static final Type<AmendmentContainerCreateEventHandler> TYPE = new Type<AmendmentContainerCreateEventHandler>();

    private final OverlayWidget overlayWidget;
    private final OverlayWidget parentOverlayWidget;
    private final int index;
    private final AmendmentAction amendmentAction;
    private final DocumentController documentController;

    public AmendmentContainerCreateEvent(OverlayWidget overlayWidget, OverlayWidget parentOverlayWidget, int index, AmendmentAction amendmentAction, DocumentController documentController) {
        this.overlayWidget = overlayWidget;
        this.parentOverlayWidget = parentOverlayWidget;
        this.index = index;
        this.amendmentAction = amendmentAction;
        this.documentController = documentController;
    }

    @Override
    public Type<AmendmentContainerCreateEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerCreateEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentAction getAmendmentAction() {
        return amendmentAction;
    }

    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }

    public DocumentController getDocumentController() {
        return documentController;
    }

    public OverlayWidget getParentOverlayWidget() {
        return parentOverlayWidget;
    }

    public int getIndex() {
        return index;
    }
}
