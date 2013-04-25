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

/**
 * An event indicating that an amendment was not injected into a document controller (because its element could not
 * be found or provided, or because its language was not matching).
 * Date: 24/06/12 20:14
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerSkippedEvent extends GwtEvent<AmendmentContainerSkippedEventHandler> {

    public static final Type<AmendmentContainerSkippedEventHandler> TYPE = new Type<AmendmentContainerSkippedEventHandler>();

    private final AmendmentController amendmentController;

    public AmendmentContainerSkippedEvent(AmendmentController amendmentController) {
        this.amendmentController = amendmentController;
    }

    @Override
    public Type<AmendmentContainerSkippedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerSkippedEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentController getAmendmentController() {
        return amendmentController;
    }
}
