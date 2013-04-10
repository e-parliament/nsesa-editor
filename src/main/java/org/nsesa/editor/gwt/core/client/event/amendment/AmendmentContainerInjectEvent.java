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
package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

/**
 * An event indicating a request for injecting an amendment.
 * Date: 24/06/12 20:14
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerInjectEvent extends GwtEvent<AmendmentContainerInjectEventHandler> {

    public static final Type<AmendmentContainerInjectEventHandler> TYPE = new Type<AmendmentContainerInjectEventHandler>();

    private final AmendmentContainerDTO[] amendments;

    public AmendmentContainerInjectEvent(AmendmentContainerDTO amendment) {
        this.amendments = new AmendmentContainerDTO[]{amendment};
    }

    public AmendmentContainerInjectEvent(AmendmentContainerDTO[] amendments) {
        this.amendments = amendments;
    }

    @Override
    public Type<AmendmentContainerInjectEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerInjectEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentContainerDTO[] getAmendments() {
        return amendments;
    }
}
