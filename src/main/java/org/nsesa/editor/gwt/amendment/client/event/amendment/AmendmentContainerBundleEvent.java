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
 * An event indicating a request to bundle an amendment with the given children.
 * Date: 24/06/12 20:14
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerBundleEvent extends GwtEvent<AmendmentContainerBundleEventHandler> {

    public static final Type<AmendmentContainerBundleEventHandler> TYPE = new Type<AmendmentContainerBundleEventHandler>();

    private final AmendmentController parent;
    private final AmendmentController[] amendmentControllers;

    public AmendmentContainerBundleEvent(AmendmentController parent, AmendmentController[] amendmentControllers) {
        this.parent = parent;
        this.amendmentControllers = amendmentControllers;
    }

    @Override
    public Type<AmendmentContainerBundleEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerBundleEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentController getParent() {
        return parent;
    }

    public AmendmentController[] getAmendmentControllers() {
        return amendmentControllers;
    }
}
