package org.nsesa.editor.gwt.editor.client.ui.document.header;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.ArrayList;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentHeaderController extends Composite {

    private final DocumentHeaderView view;
    private final ClientFactory clientFactory;
    private EventBus documentEventBus;
    private DocumentController documentController;

    private ArrayList<DocumentDTO> availableTranslations = new ArrayList<DocumentDTO>();

    @Inject
    public DocumentHeaderController(final ClientFactory clientFactory, final DocumentHeaderView view) {
        assert view != null : "View is not set --BUG";

        this.view = view;
        this.clientFactory = clientFactory;
        registerGlobalListeners();
    }

    private void registerGlobalListeners() {

    }

    private void registerPrivateListeners() {
        final HasChangeHandlers listBox = view.getTranslationsListBox();
        listBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                final String documentID = view.getSelectedDocumentID();
                if (documentID != null) {

                    // change the document on our parent controller
                    final DocumentDTO document = getDocument(documentID);
                    if (document == null) {
                        // ? strange, we would not find the document with the given id.
                        // this should only happen if someone changed the list of documents
                        // but not update the translation list box (or if an incorrect value is specified, of course)
                        throw new RuntimeException("Could not find document with documentID " + documentID + " in the list of translations.");
                    }
                    documentController.setDocument(document);
                    // fire an update to get the new content
                    clientFactory.getEventBus().fireEvent(new DocumentRefreshRequestEvent(documentController));
                }
            }
        });
    }

    private DocumentDTO getDocument(String documentID) {
        for (final DocumentDTO document : availableTranslations) {
            if (documentID.equals(document.getDocumentID())) {
                return document;
            }
        }
        return null;
    }

    public DocumentHeaderView getView() {
        return view;
    }

    public void setDocumentName(final String documentName) {
        view.setDocumentName(documentName);
    }

    public void setAvailableTranslations(final ArrayList<DocumentDTO> translations) {
        this.availableTranslations = translations;
        view.setAvailableTranslations(translations);
    }

    public void setSelectedTranslation(final DocumentDTO selectedTranslation) {
        view.setSelectedTranslation(selectedTranslation);
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }

    public void setDocumentEventBus(EventBus documentEventBus) {
        this.documentEventBus = documentEventBus;
        registerPrivateListeners();
    }
}
