package org.nsesa.editor.gwt.editor.client.ui.document.header;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.mode.ActiveState;
import org.nsesa.editor.gwt.core.client.mode.ConsolidationMode;
import org.nsesa.editor.gwt.core.client.mode.DiffMode;
import org.nsesa.editor.gwt.core.client.mode.InlineEditingMode;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentModeChangeEvent;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.ArrayList;
import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class DocumentHeaderController {

    private final DocumentHeaderView view;
    private final DocumentEventBus documentEventBus;
    private DocumentController documentController;

    private List<DocumentDTO> availableTranslations = new ArrayList<DocumentDTO>();
    private List<DocumentDTO> relatedDocuments = new ArrayList<DocumentDTO>();

    // some example modes
    private ToggleButton inlineEditingButton = new ToggleButton("Inline", "Inline", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            final InlineEditingMode inline = (InlineEditingMode) documentController.getMode(InlineEditingMode.KEY);
            documentController.getDocumentEventBus().fireEvent(new DocumentModeChangeEvent<InlineEditingMode>(documentController, inline, new ActiveState(!inline.getState().isActive())));
        }
    });
    private ToggleButton diffingButton = new ToggleButton("Diffing", "Diffing", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            final DiffMode diff = (DiffMode) documentController.getMode(DiffMode.KEY);
            documentController.getDocumentEventBus().fireEvent(new DocumentModeChangeEvent<DiffMode>(documentController, diff, new ActiveState(!diff.getState().isActive())));
        }
    });
    private ToggleButton consolidationButton = new ToggleButton("Consolidation", "Consolidation", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            final ConsolidationMode consolidationMode = (ConsolidationMode) documentController.getMode(ConsolidationMode.KEY);
            documentController.getDocumentEventBus().fireEvent(new DocumentModeChangeEvent<ConsolidationMode>(documentController, consolidationMode, new ActiveState(!consolidationMode.getState().isActive())));
        }
    });

    @Inject
    public DocumentHeaderController(final DocumentEventBus documentEventBus, final DocumentHeaderView view) {
        assert view != null : "View is not set --BUG";

        this.view = view;
        this.documentEventBus = documentEventBus;

        view.addWidget(inlineEditingButton);
        view.addWidget(diffingButton);
        view.addWidget(consolidationButton);

        registerListeners();
    }

    private void registerListeners() {
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
                    documentController.setDocument(document);
                    // fire an update to get the new content
                    documentEventBus.fireEvent(new DocumentRefreshRequestEvent(documentController));
                }
            }
        });
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
                    documentController.setDocument(document);
                    // fire an update to get the new content
                    documentEventBus.fireEvent(new DocumentRefreshRequestEvent(documentController));
                }
            }
        });
    }

    private DocumentDTO getTranslationDocument(String documentID) {
        for (final DocumentDTO document : availableTranslations) {
            if (documentID.equals(document.getDocumentID())) {
                return document;
            }
        }

        return null;
    }

    private DocumentDTO getRelatedDocument(String documentID) {
        for (final DocumentDTO document : relatedDocuments) {
            if (documentID.equals(document.getDocumentID())) {
                return document;
            }
        }

        return null;
    }

    public DocumentHeaderView getView() {
        return view;
    }

    public void setAvailableTranslations(final ArrayList<DocumentDTO> translations) {
        this.availableTranslations = translations;
        view.setAvailableTranslations(translations);
    }

    public void setRelatedDocuments(final ArrayList<DocumentDTO> relatedDocuments) {
        this.relatedDocuments = relatedDocuments;
        view.setRelatedDocuments(relatedDocuments);
    }

    public void setSelectedTranslation(final DocumentDTO selectedTranslation) {
        view.setSelectedTranslation(selectedTranslation);
    }

    public void setSelectedRelatedDocument(final DocumentDTO selectedRelatedDocument) {
        view.setSelectedRelatedDocument(selectedRelatedDocument);
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }
}
