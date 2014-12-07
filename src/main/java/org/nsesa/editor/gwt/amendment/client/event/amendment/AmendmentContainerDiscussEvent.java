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
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.shared.AmendmentAction;

/**
 * Event for indicating a request to review an new amendment.
 */
public class AmendmentContainerDiscussEvent extends GwtEvent<AmendmentContainerDiscussEventHandler> {

    public static final Type<AmendmentContainerDiscussEventHandler> TYPE = new Type<AmendmentContainerDiscussEventHandler>();

    private final AmendmentController amendmentController;
    private final AmendmentAction amendmentAction;
    private final DocumentController documentController;

    public AmendmentContainerDiscussEvent(org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController amendmentController, AmendmentAction amendmentAction, DocumentController documentController) {
        this.amendmentController = amendmentController;
        this.amendmentAction = amendmentAction;
        this.documentController = documentController;
    }

    @Override
    public Type<AmendmentContainerDiscussEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerDiscussEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentAction getAmendmentAction() {
        return amendmentAction;
    }

    public static Type<AmendmentContainerDiscussEventHandler> getType() {
        return TYPE;
    }

    public DocumentController getDocumentController() {
        return documentController;
    }

    public AmendmentController getAmendmentController() {
        return amendmentController;
    }
}
