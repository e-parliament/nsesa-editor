package org.nsesa.editor.gwt.editor.client.ui.document.header;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;

import java.util.List;

/**
 * Date: 24/06/12 21:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DocumentHeaderViewImpl.class)
public interface DocumentHeaderView extends IsWidget {

    void setAvailableTranslations(List<DocumentDTO> translations);

    void setRelatedDocuments(List<DocumentDTO> relatedDocuments);

    HasChangeHandlers getTranslationsListBox();

    HasChangeHandlers getRelatedDocumentsListBox();

    String getSelectedRelatedDocumentID();

    String getSelectedTranslationDocumentID();

    void setSelectedTranslation(DocumentDTO selectedTranslation);

    void setSelectedRelatedDocument(DocumentDTO selectedRelatedDocument);

    void setStyleName(String style);
}
