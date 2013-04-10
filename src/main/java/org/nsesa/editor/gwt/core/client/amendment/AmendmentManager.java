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
package org.nsesa.editor.gwt.core.client.amendment;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.util.Filter;
import org.nsesa.editor.gwt.core.client.util.FilterResponse;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

import java.util.List;

/**
 * The {@link AmendmentManager} is responsible for keeping a single copy of the amendments that are available in the
 * application, so that other components can request (a subset of) the amendments. It is responsible for the
 * translation from {@link AmendmentContainerDTO}s into {@link AmendmentController}s.
 * <p/>
 * Date: 09/01/13 17:16
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultAmendmentManager.class)
public interface AmendmentManager extends AmendmentInjectionCapable {

    void registerListeners();

    void removeListeners();

    void deleteAmendmentContainers(final AmendmentController... toDelete);

    void tableAmendmentContainers(final AmendmentController... toTable);

    void withdrawAmendmentContainers(final AmendmentController... toWithdraw);

    /**
     * Sets a list of available amendment container DTOs.
     *
     * @param amendmentContainerDTOs the amendment container DTOs.
     */
    void setAmendmentContainerDTOs(AmendmentContainerDTO[] amendmentContainerDTOs);

    /**
     * Get the full list of amendment controllers container within this {@link AmendmentManager}.
     *
     * @return the full list of amendment controllers.
     */
    List<AmendmentController> getAmendmentControllers();

    /**
     * Get (a potential subset of) the amendment controllers that adhere to the specifications in the
     * given {@link Filter} <tt>filter</tt>.
     *
     * @param filter the filter with the specifications
     * @return the response of the filtering request
     */
    FilterResponse<AmendmentController> getAmendmentControllers(Filter<AmendmentController> filter);

    /**
     * Sets the parent {@link DocumentController}.
     * @param documentController the parent document controller
     */
    void setDocumentController(DocumentController documentController);
}
