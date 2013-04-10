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
package org.nsesa.editor.gwt.core.client.ui.document.sourcefile.header;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;

import java.util.List;

/**
 * View for the {@link SourceFileHeaderController}.
 * Date: 24/06/12 21:43
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(SourceFileHeaderViewImpl.class)
public interface SourceFileHeaderView extends IsWidget {

    /**
     * Set the list of available translations.
     * @param translations the translations
     */
    void setAvailableTranslations(List<DocumentDTO> translations);

    /**
     * Set the list of related documents.
     * @param relatedDocuments the related documents
     */
    void setRelatedDocuments(List<DocumentDTO> relatedDocuments);

    /**
     * Get a reference to the translations list box.
     * @return the listbox
     */
    HasChangeHandlers getTranslationsListBox();

    /**
     * Get a reference to the related documents list box.
     * @return the listbox
     */
    HasChangeHandlers getRelatedDocumentsListBox();

    /**
     * Get the document ID from the current selected related document.
     * @return the documentID
     */
    String getSelectedRelatedDocumentID();

    /**
     * Get the document ID from the current selected translations.
     * @return the documentID
     */
    String getSelectedTranslationDocumentID();

    /**
     * Set the current selected translation.
     * @param selectedTranslation the selected translation
     */
    void setSelectedTranslation(DocumentDTO selectedTranslation);

    /**
     * Set the current selected related document.
     * @param selectedRelatedDocument the selected related document
     */
    void setSelectedRelatedDocument(DocumentDTO selectedRelatedDocument);

    /**
     * General style setting
     * @param style the CSS style name to set
     */
    void setStyleName(String style);

    /**
     * Add another widget on the main header toolbar.
     * @param widget the widget to add
     */
    void addWidget(Widget widget);

    /**
     * Add another widget on the main header toolbar.
     * @param widget the widget to add
     */
    void addWidget(IsWidget widget);
}
