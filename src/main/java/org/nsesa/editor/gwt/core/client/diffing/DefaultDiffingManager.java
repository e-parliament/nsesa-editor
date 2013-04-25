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
package org.nsesa.editor.gwt.core.client.diffing;

import com.google.inject.Inject;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.DiffMethod;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Default implementation of the {@link DiffingManager}. This manager does <strong>NOT</strong> perform any actual diffing.
 * Date: 08/07/12 13:56
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(DOCUMENT)
public class DefaultDiffingManager implements DiffingManager {

    /**
     * Reference to the parent document controller.
     */
    protected DocumentController documentController;

    /**
     * Reference to the parent document event bus.
     */
    protected final DocumentEventBus documentEventBus;

    @Inject
    public DefaultDiffingManager(final DocumentEventBus documentEventBus) {
        this.documentEventBus = documentEventBus;
    }

    /**
     * Default no-diffing.
     * @param method                the diff method
     * @param amendmentControllers  the amendment controllers to do the diff-ing on
     */
    public void diff(final DiffMethod method, final AmendmentController... amendmentControllers) {
        // default is to not do any diffing at all ..
    }

    /**
     * Sets the parent document controller.
     * @param documentController the document controller
     */
    @Override
    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }
}
