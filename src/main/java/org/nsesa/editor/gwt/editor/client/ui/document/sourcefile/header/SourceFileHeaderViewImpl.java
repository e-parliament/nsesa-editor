package org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 24/06/12 16:39
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class SourceFileHeaderViewImpl extends Composite implements SourceFileHeaderView {

    interface MyUiBinder extends UiBinder<Widget, SourceFileHeaderViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private final DocumentEventBus documentEventBus;

    @UiField
    HorizontalPanel horizontalPanel;
    @UiField
    HorizontalPanel extensionPanel;
    @UiField
    Label documentName;
    @UiField
    ListBox documentTranslations;
    @UiField
    ListBox relatedDocuments;

    @Inject
    public SourceFileHeaderViewImpl(final DocumentEventBus documentEventBus) {
        this.documentEventBus = documentEventBus;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        horizontalPanel.getElement().getStyle().setTableLayout(Style.TableLayout.FIXED);
    }

    @Override
    public void setAvailableTranslations(final List<DocumentDTO> translations) {
        this.documentTranslations.clear();
        for (final DocumentDTO translation : translations) {
            documentTranslations.addItem(translation.getName(), translation.getDocumentID());
        }
    }

    @Override
    public void setRelatedDocuments(List<DocumentDTO> relatedDocuments) {
        this.relatedDocuments.clear();
        for (final DocumentDTO related : relatedDocuments) {
            this.relatedDocuments.addItem(related.getName(), related.getDocumentID());
        }
    }

    @Override
    public void setSelectedRelatedDocument(DocumentDTO selectedRelatedDocument) {
        for (int i = 0; i < relatedDocuments.getItemCount(); i++) {
            final String value = relatedDocuments.getValue(i);
            final String text = relatedDocuments.getItemText(i);
            if (value.equals(selectedRelatedDocument.getDocumentID())) {
                relatedDocuments.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    public HasChangeHandlers getTranslationsListBox() {
        return documentTranslations;
    }

    @Override
    public HasChangeHandlers getRelatedDocumentsListBox() {
        return relatedDocuments;
    }

    @Override
    public String getSelectedRelatedDocumentID() {
        if (relatedDocuments.getSelectedIndex() != -1) {
            return relatedDocuments.getValue(relatedDocuments.getSelectedIndex());
        }
        return null;
    }

    @Override
    public String getSelectedTranslationDocumentID() {
        if (documentTranslations.getSelectedIndex() != -1) {
            return documentTranslations.getValue(documentTranslations.getSelectedIndex());
        }
        return null;
    }

    @Override
    public void setSelectedTranslation(DocumentDTO selectedTranslation) {
        for (int i = 0; i < documentTranslations.getItemCount(); i++) {
            final String value = documentTranslations.getValue(i);
            final String text = documentTranslations.getItemText(i);
            if (value.equals(selectedTranslation.getDocumentID())) {
                documentTranslations.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    public void addWidget(Widget widget) {
        extensionPanel.add(widget);
    }

    @Override
    public void addWidget(IsWidget widget) {
        extensionPanel.add(widget);
    }
}
