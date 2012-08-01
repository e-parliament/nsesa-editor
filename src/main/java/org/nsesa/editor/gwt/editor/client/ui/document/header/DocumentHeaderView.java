package org.nsesa.editor.gwt.editor.client.ui.document.header;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;

import java.util.ArrayList;

/**
 * Date: 24/06/12 21:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DocumentHeaderViewImpl.class)
public interface DocumentHeaderView extends IsWidget {

    void setDocumentName(String documentName);

    void setAvailableTranslations(ArrayList<DocumentDTO> translations);

    HasChangeHandlers getTranslationsListBox();

    String getSelectedDocumentID();

    void setSelectedTranslation(DocumentDTO selectedTranslation);
}
