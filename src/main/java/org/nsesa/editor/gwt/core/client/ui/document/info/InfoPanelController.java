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
package org.nsesa.editor.gwt.core.client.ui.document.info;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Controller for the (meta) information tab under a {@link DocumentController}.
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(EDITOR)
public class InfoPanelController {

    /**
     * View part of the component.
     */
    protected final InfoPanelView view;

    /**
     * The parent document controller.
     */
    protected DocumentController documentController;

    @Inject
    public InfoPanelController(InfoPanelView view) {
        this.view = view;
    }

    public void registerListeners() {

    }

    public void removeListeners() {

    }

    /**
     * Return the main view.
     *
     * @return the view
     */
    public InfoPanelView getView() {
        return view;
    }

    /**
     * Set the parent document controller.
     *
     * @param documentController the document controller
     */
    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }
}
