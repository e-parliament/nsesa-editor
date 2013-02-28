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
package org.nsesa.editor.gwt.core.client.ui.document.sourcefile.header;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.SourceFileController;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;

import java.util.ArrayList;
import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Controller for the source file header component.
 * Date: 24/06/12 18:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class SourceFileHeaderController {

    /**
     * View for this component.
     */
    protected final SourceFileHeaderView view;

    /**
     * Document scoped event bus.
     */
    protected final DocumentEventBus documentEventBus;

    /**
     * Parent source file controller.
     */
    protected SourceFileController sourceFileController;

    /**
     * List of the available translations.
     */
    protected List<DocumentDTO> availableTranslations = new ArrayList<DocumentDTO>();

    /**
     * List of the related documents.
     */
    protected List<DocumentDTO> relatedDocuments = new ArrayList<DocumentDTO>();


    @Inject
    public SourceFileHeaderController(final DocumentEventBus documentEventBus, final SourceFileHeaderView view) {
        assert view != null : "View is not set --BUG";
        this.view = view;
        this.documentEventBus = documentEventBus;
        registerListeners();
    }

    private void registerListeners() {

        // add a change handler to load translations when a new translation is selected
        view.getTranslationsListBox().addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                final String documentID = view.getSelectedTranslationDocumentID();
                if (documentID != null) {

                    // change the document on our parent controller
                    final DocumentDTO document = getTranslationDocument(documentID);
                    if (document == null) {
                        // ? strange, we would not find the document with the given id.
                        // this should only happen if someone changed the list of documents
                        // but not update the translation list box (or if an incorrect value is specified, of course)
                        throw new RuntimeException("Could not find document with documentID " + documentID + " in the list of translations.");
                    }
                    sourceFileController.getDocumentController().setDocument(document);
                    // fire an update to get the new content
                    documentEventBus.fireEvent(new DocumentRefreshRequestEvent(sourceFileController.getDocumentController()));
                }
            }
        });
        // add a change handler to load related documents when a new document is selected
        view.getRelatedDocumentsListBox().addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                final String documentID = view.getSelectedRelatedDocumentID();
                if (documentID != null) {

                    // change the document on our parent controller
                    final DocumentDTO document = getRelatedDocument(documentID);
                    if (document == null) {
                        throw new RuntimeException("Could not find document with documentID " + documentID + " in the list of related documents.");
                    }
                    sourceFileController.getDocumentController().setDocument(document);
                    // fire an update to get the new content
                    documentEventBus.fireEvent(new DocumentRefreshRequestEvent(sourceFileController.getDocumentController()));
                }
            }
        });
    }

    /**
     * Get a {@link DocumentDTO} translation based on the given <tt>documentID</tt>.
     *
     * @param documentID the document identifier
     * @return the document dto, or <tt>null</tt> if it cannot be found in the list of available translations
     */
    private DocumentDTO getTranslationDocument(final String documentID) {
        for (final DocumentDTO document : availableTranslations) {
            if (documentID.equals(document.getDocumentID())) {
                return document;
            }
        }
        return null;
    }

    /**
     * Get a {@link DocumentDTO} related document based on the given <tt>documentID</tt>.
     *
     * @param documentID the document identifier
     * @return the document dto, or <tt>null</tt> if it cannot be found in the list of available related documents
     */
    private DocumentDTO getRelatedDocument(final String documentID) {
        for (final DocumentDTO document : relatedDocuments) {
            if (documentID.equals(document.getDocumentID())) {
                return document;
            }
        }
        return null;
    }

    /**
     * Return the view associated with this controller.
     * @return  the view
     */
    public SourceFileHeaderView getView() {
        return view;
    }

    /**
     * Set the available translations.
     * @param translations the translations
     */
    public void setAvailableTranslations(final ArrayList<DocumentDTO> translations) {
        this.availableTranslations = translations;
        view.setAvailableTranslations(translations);
    }

    /**
     * Set the related documents.
     * @param relatedDocuments the related documents
     */
    public void setRelatedDocuments(final ArrayList<DocumentDTO> relatedDocuments) {
        this.relatedDocuments = relatedDocuments;
        view.setRelatedDocuments(relatedDocuments);
    }

    /**
     * Set the currently active translation.
     * @param selectedTranslation the active selected translation
     */
    public void setSelectedTranslation(final DocumentDTO selectedTranslation) {
        view.setSelectedTranslation(selectedTranslation);
    }

    /**
     * Set the current related document.
     * @param selectedRelatedDocument the active selected related document
     */
    public void setSelectedRelatedDocument(final DocumentDTO selectedRelatedDocument) {
        view.setSelectedRelatedDocument(selectedRelatedDocument);
    }

    /**
     * Set the parent source file controller.
     * @param sourceFileController the source file controller
     */
    public void setSourceFileController(SourceFileController sourceFileController) {
        this.sourceFileController = sourceFileController;
    }
}
