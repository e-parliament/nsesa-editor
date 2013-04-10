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
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.shared.DiffMethod;

/**
 * An event indicating a request to perform a diffing on a set of amendment controllers.
 * Date: 24/06/12 20:14
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerDiffEvent extends GwtEvent<AmendmentContainerDiffEventHandler> {

    public static final Type<AmendmentContainerDiffEventHandler> TYPE = new Type<AmendmentContainerDiffEventHandler>();

    private final String type;
    private final DiffMethod diffMethod;
    private final AmendmentController[] amendmentControllers;

    public AmendmentContainerDiffEvent(String type, DiffMethod diffMethod, AmendmentController amendmentController) {
        this.type = type;
        this.diffMethod = diffMethod;
        this.amendmentControllers = new AmendmentController[]{amendmentController};
    }

    public AmendmentContainerDiffEvent(String type, DiffMethod diffMethod, AmendmentController[] amendmentControllers) {
        this.type = type;
        this.diffMethod = diffMethod;
        this.amendmentControllers = amendmentControllers;
    }

    @Override
    public Type<AmendmentContainerDiffEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerDiffEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentController[] getAmendmentControllers() {
        return amendmentControllers;
    }

    public String getType() {
        return type;
    }

    public DiffMethod getDiffMethod() {
        return diffMethod;
    }
}
