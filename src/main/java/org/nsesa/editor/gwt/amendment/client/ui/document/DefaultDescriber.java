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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.amendment.client.ui.document.resources.Messages;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.shared.AmendmentAction;

/**
 * Date: 11/06/13 10:06
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class DefaultDescriber implements Describer {

    private final Messages messages;
    private DocumentController documentController;

    @Inject
    public DefaultDescriber(Messages messages) {
        this.messages = messages;
    }

    @Override
    public String introduction(AmendmentController amendmentController, String isoLanguage) {
        // we currently ignore the language
        final StringBuilder sb = new StringBuilder();
        String action = null;
        switch (amendmentController.getModel().getAmendmentAction()) {
            case DELETION:
                action = messages.amendmentActionDelete();
                break;
            case MODIFICATION:
                action = messages.amendmentActionModification();
                break;
            case CREATION:
                action = messages.amendmentActionCreation();
                break;
            case MOVE:
                action = messages.amendmentActionMove();
                break;
            case BUNDLE:
                action = messages.amendmentActionBundle();
                break;
            default:
                throw new UnsupportedOperationException("Unrecognized option.");
        }
        String num = amendmentController.getOverlayWidget().getUnformattedIndex();
        if (num == null || "".equalsIgnoreCase(num)) {
            num = Integer.toString(amendmentController.getOverlayWidget().getTypeIndex());
        }
        final String location = amendmentController.getOverlayWidget().getType().toLowerCase() + (num != null ? " " + num : "");
        sb.append(messages.amendmentAction(action, location));
        return sb.toString();
    }

    @Override
    public String describe(AmendmentController amendmentController, String isoLanguage) {
        // we currently ignore the language
        final StringBuilder sb = new StringBuilder("(description)");
        if (amendmentController.getModel().getAmendmentAction() == AmendmentAction.MODIFICATION) {
            // note: this only works AFTER the amendment has been diffed.
            // TODO
        }
        return sb.toString();
    }

    @Override
    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }
}
