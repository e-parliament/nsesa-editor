package org.nsesa.editor.gwt.editor.client.ui.document.header;

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
public class DocumentHeaderViewImpl extends Composite implements DocumentHeaderView {

    interface MyUiBinder extends UiBinder<Widget, DocumentHeaderViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private final DocumentEventBus documentEventBus;

    @UiField
    HorizontalPanel horizontalPanel;
    @UiField
    Label documentName;
    @UiField
    ListBox documentTranslations;

    @Inject
    public DocumentHeaderViewImpl(final DocumentEventBus documentEventBus) {

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
