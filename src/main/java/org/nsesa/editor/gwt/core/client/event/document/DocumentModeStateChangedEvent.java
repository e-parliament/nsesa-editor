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
import org.nsesa.editor.gwt.core.client.mode.DocumentMode;
import org.nsesa.editor.gwt.core.client.mode.DocumentState;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

/**
 * An event indicating that a {@link DocumentMode} has changed its state.
 * Date: 24/06/12 20:14
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentModeStateChangedEvent<D extends DocumentMode> extends GwtEvent<DocumentModeStateChangedEventHandler> {

    public static final Type<DocumentModeStateChangedEventHandler> TYPE = new Type<DocumentModeStateChangedEventHandler>();

    private final DocumentController documentController;
    private final D documentMode;

    public DocumentModeStateChangedEvent(final DocumentController documentController,
                                         final D documentMode) {
        this.documentController = documentController;
        this.documentMode = documentMode;
    }

    @Override
    public Type<DocumentModeStateChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DocumentModeStateChangedEventHandler handler) {
        handler.onEvent(this);
    }

    public DocumentController getDocumentController() {
        return documentController;
    }

    public D getDocumentMode() {
        return documentMode;
    }
}
