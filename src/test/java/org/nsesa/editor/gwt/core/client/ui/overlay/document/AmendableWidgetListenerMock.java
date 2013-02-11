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
package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

/**
 * Date: 18/01/13 15:13
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendableWidgetListenerMock implements AmendableWidgetListener {
    @Override
    public boolean beforeAmendmentControllerAdded(AmendableWidget amendableWidget, AmendmentController amendmentController) {
        return false;
    }

    @Override
    public void afterAmendmentControllerAdded(AmendableWidget amendableWidget, AmendmentController amendmentController) {
    }

    @Override
    public boolean beforeAmendmentControllerRemoved(AmendableWidget amendableWidget, AmendmentController amendmentController) {
        return false;
    }

    @Override
    public void afterAmendmentControllerRemoved(AmendableWidget amendableWidget, AmendmentController amendmentController) {
    }

    @Override
    public boolean beforeAmendableWidgetAdded(AmendableWidget amendableWidget, AmendableWidget child) {
        return false;
    }

    @Override
    public void afterAmendableWidgetAdded(AmendableWidget amendableWidget, AmendableWidget child) {

    }

    @Override
    public boolean beforeAmendableWidgetRemoved(AmendableWidget amendableWidget, AmendableWidget child) {
        return false;
    }

    @Override
    public void afterAmendableWidgetRemoved(AmendableWidget amendableWidget, AmendableWidget child) {
    }
}
