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
package org.nsesa.editor.gwt.amendment.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.shared.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

/**
 * Event for indicating a request to create a new amendment in the UI.
 * Date: 24/06/12 20:14
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerCreateEvent extends GwtEvent<AmendmentContainerCreateEventHandler> {

    public static final Type<AmendmentContainerCreateEventHandler> TYPE = new Type<AmendmentContainerCreateEventHandler>();

    private final OverlayWidget parentOverlayWidget;
    private final OverlayWidget reference;
    private final OverlayWidget overlayWidget;
    private final int index;
    private final AmendmentAction amendmentAction;
    private final DocumentController documentController;

    public AmendmentContainerCreateEvent(OverlayWidget parentOverlayWidget, OverlayWidget reference, OverlayWidget overlayWidget, int index, AmendmentAction amendmentAction, DocumentController documentController) {
        this.parentOverlayWidget = parentOverlayWidget;
        this.reference = reference;
        this.overlayWidget = overlayWidget;
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

    public OverlayWidget getParentOverlayWidget() {
        return parentOverlayWidget;
    }

    public OverlayWidget getReference() {
        return reference;
    }

    public OverlayWidget getOverlayWidget() {
        return overlayWidget;
    }

    public int getIndex() {
        return index;
    }

    public DocumentController getDocumentController() {
        return documentController;
    }
}
