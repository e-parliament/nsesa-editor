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
package org.nsesa.editor.gwt.core.client.event.document;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Widget;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

/**
 * An event indicating a request to scroll to a given widget in a document controller.
 * Date: 24/06/12 20:14
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentScrollToEvent extends GwtEvent<DocumentScrollToEventHandler> {

    public static final Type<DocumentScrollToEventHandler> TYPE = new Type<DocumentScrollToEventHandler>();

    private final DocumentController documentController;
    private final Widget target;
    private final boolean userInitiated;

    public DocumentScrollToEvent(Widget target, DocumentController documentController) {
        this.target = target;
        this.documentController = documentController;
        this.userInitiated = true;
    }

    public DocumentScrollToEvent(Widget target, DocumentController documentController, boolean userInitiated) {
        this.documentController = documentController;
        this.target = target;
        this.userInitiated = userInitiated;
    }

    @Override
    public Type<DocumentScrollToEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DocumentScrollToEventHandler handler) {
        handler.onEvent(this);
    }

    public DocumentController getDocumentController() {
        return documentController;
    }

    public Widget getTarget() {
        return target;
    }

    public boolean isUserInitiated() {
        return userInitiated;
    }
}
