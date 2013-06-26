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
package org.nsesa.editor.gwt.amendment.client.ui.document;

import org.nsesa.editor.gwt.amendment.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.action.AmendmentActionPanelController;
import org.nsesa.editor.gwt.amendment.client.ui.document.amendments.AmendmentsPanelController;
import org.nsesa.editor.gwt.amendment.client.ui.document.amendments.header.AmendmentsHeaderController;
import org.nsesa.editor.gwt.core.client.diffing.DiffingManager;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentInjector;
import org.nsesa.editor.gwt.core.client.ui.overlay.Selector;

/**
 * Date: 24/04/13 12:00
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendmentDocumentInjector extends DocumentInjector {
    /**
     * Get the amendments panel controller.
     *
     * @return the amendments panel controller.
     */
    AmendmentsPanelController getAmendmentsPanelController();

    /**
     * Get the amendment action panel controller.
     *
     * @return the amendment action panel controller
     */
    AmendmentActionPanelController getAmendmentActionPanelController();

    /**
     * Get the amendment manager.
     *
     * @return the amendment manager
     */
    AmendmentManager getAmendmentManager();

    /**
     * Get an amendment controller.
     *
     * @return an amendment controller
     */
    AmendmentController getAmendmentController();

    /**
     * Get the header controller for the amendments panel.
     *
     * @return the amendments header controller
     */
    AmendmentsHeaderController getAmendmentsHeaderController();

    /**
     * Get a selector that holds a selection of amendment controllers.
     *
     * @return the selector.
     */
    Selector<AmendmentController> getSelector();

    /**
     * Get the diffing manager for diffing amendments.
     *
     * @return the diffing manager
     */
    DiffingManager<AmendmentController> getAmendmentDiffingManager();

    /**
     * Get a describer for describing the amendment controller.
     *
     * @return the describer.
     */
    Describer getDescriber();
}
