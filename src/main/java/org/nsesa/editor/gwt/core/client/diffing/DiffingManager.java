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
package org.nsesa.editor.gwt.core.client.diffing;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.shared.DiffMethod;

/**
 * A diffing manager is responsible for doing a diff on an amendment (after the amendment has been created).
 * Date: 07/01/13 17:45
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultDiffingManager.class)
public interface DiffingManager {

    /**
     * Perform a diff (word or character based) on a set of amendment controllers.
     * @param method                the diff method
     * @param amendmentControllers  the amendment controllers to do the diff-ing on
     */
    void diff(final DiffMethod method, final AmendmentController... amendmentControllers);

    /**
     * Sets the parent document controller.
     * @param documentController the document controller
     */
    void setDocumentController(DocumentController documentController);
}
