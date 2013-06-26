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

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

/**
 * A describer is a helper class to translate an {@link org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController}
 * into a human readable format.
 * Date: 11/06/13 09:54
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultDescriber.class)
public interface Describer {

    /**
     * Creates a (simple) introduction for an amendment controller (eg. 'This amendment deletes article 2').
     *
     * @param amendmentController the amendment controller
     * @param isoLanguage         the language
     * @return the introduction
     */
    String introduction(AmendmentController amendmentController, String isoLanguage);

    /**
     * Creates a (complex) description of the (textual) changes in an amendment (eg. 'Deletes 4th word untilt he end of
     * the sentence'). Should only be called AFTER a diff has been completed.
     *
     * @param amendmentController the amendment controller
     * @param isoLanguage         the language
     * @return the description.
     */
    String describe(AmendmentController amendmentController, String isoLanguage);

    /**
     * Set the active document controller.
     *
     * @param documentController the document controller.
     */
    void setDocumentController(DocumentController documentController);
}
