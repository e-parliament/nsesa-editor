package org.nsesa.editor.gwt.editor.client.ui.document.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;

import java.util.ArrayList;

/**
 * Date: 24/06/12 16:39
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class DocumentHeaderViewImpl extends Composite implements DocumentHeaderView {

    interface MyUiBinder extends UiBinder<Widget, DocumentHeaderViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private final EventBus eventBus;
    @UiField
    Label documentName;
    @UiField
    ListBox documentTranslations;

    @Inject
    public DocumentHeaderViewImpl(final EventBus eventBus) {

        this.eventBus = eventBus;

        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public void setDocumentName(String documentName) {
        this.documentName.setText(documentName);
    }

    @Override
    public void setAvailableTranslations(final ArrayList<DocumentDTO> translations) {
        this.documentTranslations.clear();
        for (final DocumentDTO translation : translations) {
            documentTranslations.addItem(translation.getName(), translation.getDocumentID());
        }
    }

    @Override
    public HasChangeHandlers getTranslationsListBox() {
        return documentTranslations;
    }

    @Override
    public String getSelectedDocumentID() {
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
}
